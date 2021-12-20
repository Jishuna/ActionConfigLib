package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "value" })
@RegisterCondition(name = "ON_GROUND")
public class OnGroundCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public OnGroundCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null)
			return false;

		return entity.getLocation().subtract(0, 0.01, 0).getBlock().getType().isAir() != value;
	}

}
