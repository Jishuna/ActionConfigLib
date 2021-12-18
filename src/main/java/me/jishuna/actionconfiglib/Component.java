package me.jishuna.actionconfiglib;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.entries.CooldownCondition;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.entries.DelayEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import me.jishuna.actionconfiglib.triggers.Trigger;

public class Component {
	private final Set<Trigger> triggers;
	private final List<Condition> conditions;
	private final List<Effect> effects;

	private final boolean hasDelay;
	private final JavaPlugin plugin;

	protected Component(ActionConfigLib instance, ConfigurationEntry entry) throws ParsingException {
		this.triggers = instance.getTriggerRegistry().parseTriggers(entry.getString("triggers").toUpperCase());

		this.conditions = instance.getConditionRegistry().parseConditions(entry.getMapList("conditions"));
		this.effects = instance.getEffectRegistry().parseEffects(entry.getMapList("effects"));

		this.hasDelay = this.effects.stream().anyMatch(effect -> effect instanceof DelayEffect);
		this.plugin = instance.getOwningPlugin();
	}

	public void handleAction(ActionContext context) {
		Trigger trigger = context.getTrigger();
		if (!this.triggers.contains(trigger))
			return;

		if (!handleConditions(context))
			return;

		if (!this.hasDelay) {
			handleEffects(context);
		} else {
			handleEffectsDelay(context);
		}
	}

	private void handleEffectsDelay(ActionContext context) {
		Iterator<Effect> iterator = this.effects.iterator();

		new BukkitRunnable() {
			int delay;

			@Override
			public void run() {
				if (delay > 0) {
					delay--;
					return;
				}

				while (iterator.hasNext()) {
					Effect effect = iterator.next();

					if (effect instanceof DelayEffect delayEffect) {
						delay = delayEffect.getDelay();
						return;
					} else {
						effect.evaluate(context);
					}
				}
				cancel();
			}
		}.runTaskTimer(plugin, 0, 1);
	}

	private void handleEffects(ActionContext context) {
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
