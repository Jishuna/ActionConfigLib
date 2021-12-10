package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.EntityTarget;
import me.jishuna.actionconfiglib.ParsingException;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "ON_GROUND")
public class OnGroundCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public OnGroundCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBooleanOrThrow("value");
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null)
			return false;

		return entity.getLocation().subtract(0, 0.01, 0).getBlock().getType().isAir() != value;
	}

}
