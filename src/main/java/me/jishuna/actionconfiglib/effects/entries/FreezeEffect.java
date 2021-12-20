package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "ticks", "stack" })
@RegisterEffect(name = "FREEZE")
public class FreezeEffect extends Effect {
	private final int ticks;
	private final EntityTarget target;
	private final boolean stack;

	public FreezeEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.ticks = entry.getIntOrThrow("ticks");
		this.stack = entry.getBoolean("stack", false);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target)
				.ifPresent(entity -> entity.setFreezeTicks(Math.max((stack ? entity.getFreezeTicks() : 0) + ticks, 0)));
	}

}
