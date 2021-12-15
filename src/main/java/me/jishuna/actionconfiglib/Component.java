package me.jishuna.actionconfiglib;

import java.util.List;
import java.util.Set;

import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.entries.CooldownCondition;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import me.jishuna.actionconfiglib.triggers.Trigger;

public class Component {
	private final Set<Trigger> triggers;
	private final List<Condition> conditions;
	private final List<Effect> effects;

	protected Component(ActionConfigLib instance, ConfigurationEntry entry) throws ParsingException {
		this.triggers = instance.getTriggerRegistry().parseTriggers(entry.getString("triggers").toUpperCase());

		this.conditions = instance.getConditionRegistry().parseConditions(entry.getMapList("conditions"));
		this.effects = instance.getEffectRegistry().parseEffects(entry.getMapList("effects"));
	}

	public void handleAction(ActionContext context) {
		Trigger trigger = context.getTrigger();
		if (!this.triggers.contains(trigger))
			return;

		if (!handleConditions(context))
			return;

		this.effects.forEach(effect -> effect.evaluate(context));
	}

	private boolean handleConditions(ActionContext context) {
		CooldownCondition cooldown = null;

		for (Condition condition : this.conditions) {
			if (!condition.evaluate(context))
				return false;

			if (condition instanceof CooldownCondition cool)
				cooldown = cool;
		}

		if (cooldown != null)
			cooldown.setCooldown(context);
		return true;
	}
}
