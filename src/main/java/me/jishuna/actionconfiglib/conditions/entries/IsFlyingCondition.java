package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.EntityTarget;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "IS_FLYING")
public class IsFlyingCondition extends Condition {

	private final EntityTarget target;
	private final boolean value;

	public IsFlyingCondition(ConfigurationEntry entry) {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null || !(entity instanceof Player player))
			return false;

		return player.isFlying() == value;
	}

}
