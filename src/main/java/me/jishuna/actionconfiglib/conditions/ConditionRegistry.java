package me.jishuna.actionconfiglib.conditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.ParsingException;
import me.jishuna.actionconfiglib.Utils;

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

	public List<Condition> parseConditions(List<Map<?, ?>> mapList) throws ParsingException {
		int size = mapList.size();
		List<Condition> conditions = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Map<?, ?> map = mapList.get(i);
			if (map != null) {
				Condition condition = parseCondition(new ConfigurationEntry(map));
				if (condition != null)
					conditions.add(condition);
			}
		}
		return conditions;
	}

	private Condition parseCondition(ConfigurationEntry entry) throws ParsingException {
		String type = entry.getString("type").toUpperCase();

		Class<? extends Condition> clazz = this.conditionMap.get(type);

		if (clazz == null) {
			System.err.println(type + " not found.");
			return null;
		}

		Condition condition;
		try {
			condition = clazz.getDeclaredConstructor(ConfigurationEntry.class).newInstance((Object) entry);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			if (e.getCause() instanceof ParsingException ex) {
				throw new ParsingException("Error parsing condition \"" + type + "\":", ex);
			}
			return null;
		}
		return condition;
	}
}
