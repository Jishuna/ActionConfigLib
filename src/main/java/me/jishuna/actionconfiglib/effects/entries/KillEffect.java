package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;

@RegisterEffect(name = "KILL")
public class KillEffect extends Effect {
	private final EntityTarget target;

	public KillEffect(ConfigurationEntry entry) {
		this.target = EntityTarget.fromString(entry.getString("target"));
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> entity.setHealth(0));
	}

}
