package me.jishuna.actionconfiglib.triggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TriggerRegistry {
	private static final List<Trigger> DEFAULTS = new ArrayList<>();

	public static final Trigger TICK = createDefault("TICK");
	public static final Trigger BREAK_BLOCK = createDefault("BREAK_BLOCK");
	public static final Trigger PLACE_BLOCK = createDefault("PLACE_BLOCK");
	public static final Trigger DAMAGED_BY_OTHER = createDefault("DAMAGED_BY_OTHER");
	public static final Trigger DAMAGED_BY_ENTITY = createDefault("DAMAGED_BY_ENTITY");
	public static final Trigger DAMAGED_BY_PROJECTILE = createDefault("DAMAGED_BY_PROJECTILE");

	private final Map<String, Trigger> triggerMap = new HashMap<>();

	public TriggerRegistry() {
		DEFAULTS.forEach(this::registerTrigger);
	}

	private static Trigger createDefault(String string) {
		Trigger trigger = new Trigger(string);
		DEFAULTS.add(trigger);

		return trigger;
	}

	public void registerTrigger(Trigger trigger) {
		this.triggerMap.put(trigger.getKey(), trigger);
	}

	public Set<Trigger> parseTriggers(String string) {
		String[] data = string.split(",");
		final int size = data.length;

		Set<Trigger> triggers = new HashSet<>();

		for (int i = 0; i < size; i++) {
			Trigger trigger = getTrigger(data[i]);
			if (trigger != null) {
				triggers.add(trigger);
			} else {
				System.err.println("Invalid trigger: " + data[i]);
			}
		}
		return triggers;
	}

	public Trigger getTrigger(String key) {
		return this.triggerMap.get(key.toUpperCase());
	}
}
