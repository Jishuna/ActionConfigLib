package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.LivingEntity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.EntityTarget;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "IS_GLIDING")
public class IsGlidingCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public IsGlidingCondition(ConfigurationEntry entry) {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		LivingEntity entity = context.getLivingTarget(this.target);
		if (entity == null)
			return false;

		return entity.isGliding() == value;
	}

}
