package me.jishuna.actionconfiglib.conditions;

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

public class ConditionRegistry {
	private static final Class<?> TYPE_CLASS = Condition.class;

	private final Map<String, Class<? extends Condition>> conditionMap = new HashMap<>();

	public ConditionRegistry() {
		reloadConditions();
	}

	// Pretty sure this is not an unchecked cast
	@SuppressWarnings("unchecked")
	public void reloadConditions() {
		this.conditionMap.clear();

		for (Class<?> clazz : Utils.getAllClassesInSubpackages("me.jishuna.actionconfiglib.conditions.entries",
				this.getClass().getClassLoader())) {
			if (!TYPE_CLASS.isAssignableFrom(clazz))
				continue;

			for (RegisterCondition annotation : clazz.getAnnotationsByType(RegisterCondition.class)) {
				registerCondition(annotation.name(), (Class<? extends Condition>) clazz);
			}
		}
	}

	public void registerCondition(String name, Class<? extends Condition> clazz) {
		this.conditionMap.put(name, clazz);
	}

	public List<Condition> parseConditions(ConfigurationMapEntry entry) throws ParsingException {
		List<Condition> conditions = new ArrayList<>();
		if (!entry.has("conditions"))
			return conditions;

		List<String> stringList = entry.getStringList("conditions");
		if (!stringList.isEmpty()) {

			for (String string : stringList) {
				Condition condition = parseCompactCondition(string);
				if (condition != null)
					conditions.add(condition);
			}
			return conditions;
		}

		List<Map<?, ?>> mapList = entry.getMapList("conditions");
		if (!mapList.isEmpty()) {
			int size = mapList.size();

			for (int i = 0; i < size; i++) {
				Map<?, ?> map = mapList.get(i);
				if (map != null) {
					Condition condition = parseCondition(new ConfigurationMapEntry(map));
					if (condition != null)
						conditions.add(condition);
				}
			}
			return conditions;
		}
		throw new ParsingException("Unknown condition format");
	}

	private Class<? extends Condition> getType(String key) throws ParsingException {
		Class<? extends Condition> clazz = this.conditionMap.get(key.toUpperCase());

		if (clazz == null) {
			throw new ParsingException("Unknown condition type \"" + key + "\"");
		}
		return clazz;
	}

	private Condition parseCompactCondition(String string) throws ParsingException {
		int open = string.indexOf('(');
		int close = string.lastIndexOf(')');

		if (open < 0 || close < 0)
			throw new ParsingException("No matching pair of () found in condition string \"" + string + "\"");

		String type = string.substring(0, open).toLowerCase();
		String data = string.substring(open + 1, close);

		Class<? extends Condition> clazz = getType(type);
		ArgumentFormat format = clazz.getAnnotation(ArgumentFormat.class);

		if (format == null)
			throw new ParsingException("The condition \"" + type + "\" has no defined argument format");

		String[] conditionData = data.split(",");
		Map<String, String> dataMap = new HashMap<>();
		final int length = format.format().length;

		for (int index = 0; index < length; index++) {
			if (index >= conditionData.length)
				break;
			
			if (index == length - 1) {
				dataMap.put(format.format()[index],
						String.join(",", Arrays.copyOfRange(conditionData, index, conditionData.length)));
			} else {
				dataMap.put(format.format()[index], conditionData[index]);
			}
		}

		ConfigurationListEntry entry = new ConfigurationListEntry(dataMap);

		Condition condition;
		try {
			condition = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause()instanceof ParsingException ex) {
				throw new ParsingException("Error parsing condition \"" + type + "\":", ex);
			}
			e.printStackTrace();
			throw new ParsingException("Unknown error parsing condition \"" + type + "\": " + e.getMessage());
		}
		return condition;
	}

	private Condition parseCondition(ConfigurationMapEntry entry) throws ParsingException {
		String type = entry.getString("type").toUpperCase();

		Class<? extends Condition> clazz = getType(type);

		Condition condition;
		try {
			condition = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause()instanceof ParsingException ex) {
				throw new ParsingException("Error parsing condition \"" + type + "\":", ex);
			}
			e.printStackTrace();
			throw new ParsingException("Unknown error parsing condition \"" + type + "\": " + e.getMessage());
		}
		return condition;
	}
}
