package me.jishuna.actionconfiglib.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.ConfigurationListEntry;
import me.jishuna.actionconfiglib.ConfigurationMapEntry;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import me.jishuna.actionconfiglib.utils.Utils;

public class EffectRegistry {
	private static final Class<?> TYPE_CLASS = Effect.class;

	private final Map<String, Class<? extends Effect>> effectMap = new HashMap<>();

	public EffectRegistry() {
		reloadEffects();
	}

	public void reloadEffects() {
		this.effectMap.clear();
		registerEffects("me.jishuna.actionconfiglib.effects.entries");
	}

	@SuppressWarnings("unchecked")
	public void registerEffects(String packageName) {
		for (Class<?> clazz : Utils.getAllClassesInSubpackages(packageName, this.getClass().getClassLoader())) {
			if (!TYPE_CLASS.isAssignableFrom(clazz))
				continue;

			for (RegisterEffect annotation : clazz.getAnnotationsByType(RegisterEffect.class)) {
				registerEffect(annotation.name(), (Class<? extends Effect>) clazz);
			}
		}
	}

	public void registerEffect(String name, Class<? extends Effect> clazz) {
		this.effectMap.put(name, clazz);
	}

	public List<Effect> parseEffects(ConfigurationMapEntry entry) throws ParsingException {
		List<Effect> effects = new ArrayList<>();
		if (!entry.has("effects"))
			return effects;

		List<String> stringList = entry.getStringList("effects");

		if (!stringList.isEmpty()) {

			for (String string : stringList) {
				Effect effect = parseCompactEffect(string);
				if (effect != null)
					effects.add(effect);
			}
			return effects;
		}

		List<Map<?, ?>> mapList = entry.getMapList("effects");
		if (!mapList.isEmpty()) {
			int size = mapList.size();

			for (int i = 0; i < size; i++) {
				Map<?, ?> map = mapList.get(i);
				if (map != null) {
					Effect effect = parseEffect(new ConfigurationMapEntry(map));
					if (effect != null)
						effects.add(effect);
				}
			}
			return effects;
		}
		throw new ParsingException("Unknown effect format");
	}

	private Class<? extends Effect> getType(String key) throws ParsingException {
		Class<? extends Effect> clazz = this.effectMap.get(key.toUpperCase());

		if (clazz == null) {
			throw new ParsingException("Unknown effect type \"" + key + "\"");
		}
		return clazz;
	}

	private Effect parseCompactEffect(String string) throws ParsingException {
		int open = string.indexOf('(');
		int close = string.lastIndexOf(')');

		if (open < 0 || close < 0)
			throw new ParsingException("No matching pair of () found in effect string \"" + string + "\"");

		String type = string.substring(0, open).toLowerCase();
		String data = string.substring(open + 1, close);

		Class<? extends Effect> clazz = getType(type);
		ArgumentFormat format = clazz.getAnnotation(ArgumentFormat.class);

		if (format == null)
			throw new ParsingException("The effect \"" + type + "\" has no defined argument format");

		String[] effectData = data.split(",");
		Map<String, String> dataMap = new HashMap<>();
		final int length = format.format().length;

		for (int index = 0; index < length; index++) {
			if (index >= effectData.length)
				break;

			if (index == length - 1) {
				dataMap.put(format.format()[index],
						String.join(",", Arrays.copyOfRange(effectData, index, effectData.length)));
			} else {
				dataMap.put(format.format()[index], effectData[index]);
			}
		}

		ConfigurationListEntry entry = new ConfigurationListEntry(dataMap);

		Effect effect;
		try {
			effect = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause() instanceof ParsingException ex) {
				throw new ParsingException("Error parsing effect \"" + type + "\":", ex);
			}
			e.printStackTrace();
			throw new ParsingException("Unknown error parsing effect \"" + type + "\": " + e.getMessage());
		}
		return effect;
	}

	private Effect parseEffect(ConfigurationMapEntry entry) throws ParsingException {
		String type = entry.getString("type").toUpperCase();

		Class<? extends Effect> clazz = getType(type);

		Effect effect;
		try {
			effect = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause() instanceof ParsingException ex) {
				throw new ParsingException("Error parsing effect \"" + type + "\":", ex);
			}
			e.printStackTrace();
			throw new ParsingException("Unknown error parsing effect \"" + type + "\": " + e.getMessage());
		}
		return effect;
	}
}
