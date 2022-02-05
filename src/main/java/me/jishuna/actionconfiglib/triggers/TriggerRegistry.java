package me.jishuna.actionconfiglib.triggers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.jishuna.actionconfiglib.exceptions.ParsingException;

public class TriggerRegistry {
	private static final List<Trigger> DEFAULTS = new ArrayList<>();

	public static final Trigger TICK = createDefault("TICK");
	public static final Trigger BREAK_BLOCK = createDefault("BREAK_BLOCK");
	public static final Trigger BLOCK_DROP_ITEM = createDefault("BLOCK_DROP_ITEM");
	public static final Trigger PLACE_BLOCK = createDefault("PLACE_BLOCK");
	public static final Trigger ENTITY_INTERACT = createDefault("ENTITY_INTERACT");
	public static final Trigger DAMAGED_BY_OTHER = createDefault("DAMAGED_BY_OTHER");
	public static final Trigger DAMAGED_BY_ENTITY = createDefault("DAMAGED_BY_ENTITY");
	public static final Trigger DAMAGED_BY_PROJECTILE = createDefault("DAMAGED_BY_PROJECTILE");
	public static final Trigger ATTACK_ENTITY = createDefault("ATTACK_ENTITY");
	public static final Trigger ATTACK_PROJECTILE = createDefault("ATTACK_PROJECTILE");
	public static final Trigger KILL_ENTITY = createDefault("KILL_ENTITY");
	public static final Trigger DEATH = createDefault("DEATH");
	public static final Trigger EFFECT_GAINED = createDefault("EFFECT_GAINED");
	public static final Trigger DURABILITY_LOST = createDefault("DURABILITY_LOST");
	public static final Trigger ENTITY_TARGET = createDefault("ENTITY_TARGET");
	public static final Trigger HAND_ITEM_SWAPPED = createDefault("HAND_ITEM_SWAPPED");
	public static final Trigger SHOOT_PROJECTILE = createDefault("SHOOT_PROJECTILE");
	public static final Trigger CATCH_FISH = createDefault("CATCH_FISH");
	public static final Trigger RIGHT_CLICK = createDefault("RIGHT_CLICK");

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

	public Set<Trigger> parseTriggers(String string) throws ParsingException {
		String[] data = string.split(",");
		final int size = data.length;

		Set<Trigger> triggers = new HashSet<>();

		for (int i = 0; i < size; i++) {
			Trigger trigger = getTrigger(data[i]);
			if (trigger != null) {
				triggers.add(trigger);
			} else {
				throw new ParsingException("Invalid trigger: " + data[i]);
			}
		}
		return triggers;
	}

	public Trigger getTrigger(String key) {
		return this.triggerMap.get(key.toUpperCase());
	}
}
