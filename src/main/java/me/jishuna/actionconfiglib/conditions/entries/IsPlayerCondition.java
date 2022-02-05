package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "value" })
@RegisterCondition(name = "IS_PLAYER")
public class IsPlayerCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public IsPlayerCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		return (entity instanceof Player) == this.value;
	}

}
