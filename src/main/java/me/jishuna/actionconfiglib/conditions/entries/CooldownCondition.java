package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.Entity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.CooldownManager;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "ticks" })
@RegisterCondition(name = "COOLDOWN")
public class CooldownCondition extends Condition {
	private final CooldownManager manager;

	public CooldownCondition(ConfigurationEntry entry) throws ParsingException {
		this.manager = new CooldownManager(entry.getLongOrThrow("ticks"));
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getUser();
		if (entity == null)
			return false;

		return !this.manager.isOnCooldown(entity.getUniqueId());
	}

	public void setCooldown(ActionContext context) {
		Entity entity = context.getUser();
		if (entity == null)
			return;

		this.manager.setCooldown(entity.getUniqueId());
	}
}
