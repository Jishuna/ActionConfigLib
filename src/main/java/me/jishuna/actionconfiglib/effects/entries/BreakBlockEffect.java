package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "BREAK_BLOCK")
public class BreakBlockEffect extends Effect {
	private final EntityTarget target;
	private final LocationTarget locationTarget;
	private final int xOff;
	private final int yOff;
	private final int zOff;

	public BreakBlockEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.locationTarget = LocationTarget.fromString("target-location");

		this.xOff = entry.getInt("x-offset", 0);
		this.yOff = entry.getInt("y-offset", 0);
		this.zOff = entry.getInt("z-offset", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		Location target = context.getTargetLocation(this.locationTarget);
		LivingEntity entity = context.getLivingTarget(this.target);

		if (target == null || !(entity instanceof Player player))
			return;

		player.breakBlock(target.add(this.xOff, this.yOff, this.zOff).getBlock());
	}

}
