package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "LIGHTNING")
public class LightningEffect extends Effect {
	private final LocationTarget target;
	private final int xOff;
	private final int yOff;
	private final int zOff;

	public LightningEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = LocationTarget.fromString(entry.getString("target-location"));

		this.xOff = entry.getInt("x-offset", 0);
		this.yOff = entry.getInt("y-offset", 0);
		this.zOff = entry.getInt("z-offset", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetLocationOptional(this.target).ifPresent(
				location -> location.getWorld().strikeLightning(location.add(this.xOff, this.yOff, this.zOff)));
	}

}
