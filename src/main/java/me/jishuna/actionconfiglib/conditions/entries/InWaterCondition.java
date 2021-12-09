package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.EntityTarget;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "IN_WATER")
public class InWaterCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public InWaterCondition(ConfigurationEntry entry) {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null)
			return false;

		return entity.isInWater() == this.value;
	}

}
