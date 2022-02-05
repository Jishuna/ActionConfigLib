package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target-location", "power", "fire", "break-blocks", "x-offset", "y-offset", "z-offset" })
@RegisterEffect(name = "EXPLOSION")
public class ExplosionEffect extends Effect {
	private final LocationTarget target;
	private final float power;
	private final boolean fire;
	private final boolean breakBlocks;
	private final double xOffset;
	private final double yOffset;
	private final double zOffset;

	public ExplosionEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = LocationTarget.fromString(entry.getString("target-location"));
		this.power = (float) entry.getDoubleOrThrow("power");

		this.fire = entry.getBoolean("fire", false);
		this.breakBlocks = entry.getBoolean("break-blocks", true);

		this.xOffset = entry.getDouble("x-offset", 0);
		this.yOffset = entry.getDouble("y-offset", 0);
		this.zOffset = entry.getDouble("z-offset", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetLocationOptional(this.target).ifPresent(location -> location.getWorld().createExplosion(
				location.add(this.xOffset, this.yOffset, this.zOffset), this.power, this.fire, this.breakBlocks));
	}

}
