package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterCondition(name = "IS_SNEAKING")
public class IsSneakingCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public IsSneakingCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBooleanOrThrow("value");
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null || !(entity instanceof Player player))
			return false;

		return player.isSneaking() == value;
	}

}
