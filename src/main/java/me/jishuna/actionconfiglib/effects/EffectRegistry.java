package me.jishuna.actionconfiglib.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import me.jishuna.actionconfiglib.utils.Utils;

public class EffectRegistry {
	private static final Class<?> TYPE_CLASS = Effect.class;

	private final Map<String, Class<? extends Effect>> effectMap = new HashMap<>();

	public EffectRegistry() {
		reloadEffects();
	}

	// Pretty sure this is not an unchecked cast
	@SuppressWarnings("unchecked")
	public void reloadEffects() {
		this.effectMap.clear();

		for (Class<?> clazz : Utils.getAllClassesInSubpackages("me.jishuna.actionconfiglib.effects.entries",
				this.getClass().getClassLoader())) {
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

	public List<Effect> parseEffects(List<Map<?, ?>> mapList) throws ParsingException {
		int size = mapList.size();
		List<Effect> effects = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Map<?, ?> map = mapList.get(i);
			if (map != null) {
				Effect effect = parseEffect(new ConfigurationEntry(map));
				if (effect != null)
					effects.add(effect);
			}
		}
		return effects;
	}

	private Effect parseEffect(ConfigurationEntry entry) throws ParsingException {
		String type = entry.getString("type").toUpperCase();

		Class<? extends Effect> clazz = this.effectMap.get(type);

		if (clazz == null) {
			System.err.println(type + " not found.");
			return null;
		}

		Effect effect;
		try {
			effect = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause() instanceof ParsingException ex) {
				throw new ParsingException("Error parsing effect \"" + type + "\":", ex);
			}
			throw new ParsingException("Unknown error parsing effect \"" + type + "\": " + e.getMessage());
		}
		return effect;
	}
}
