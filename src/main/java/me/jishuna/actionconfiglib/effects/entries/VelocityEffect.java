package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.util.Vector;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "x-velocity", "y-velocity", "z-velocity" })
@RegisterEffect(name = "VELOCITY")
public class VelocityEffect extends Effect {
	private final EntityTarget target;
	private final double xVelocity;
	private final double yVelocity;
	private final double zVelocity;

	public VelocityEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		this.xVelocity = entry.getDouble("x-velocity", 0);
		this.yVelocity = entry.getDouble("y-velocity", 0);
		this.zVelocity = entry.getDouble("z-velocity", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetOptional(this.target)
				.ifPresent(entity -> entity.setVelocity(new Vector(this.xVelocity, this.yVelocity, this.zVelocity)));
	}
}
