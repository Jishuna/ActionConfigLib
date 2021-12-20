package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "projectile-type" })
@RegisterEffect(name = "LAUNCH_PROJECTILE")
public class LaunchProjectileEffect extends Effect {
	private final EntityType type;
	private final EntityTarget target;

	public LaunchProjectileEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getStringOrThrow("target"));

		this.type = EntityType.valueOf(entry.getStringOrThrow("projectile-type").toUpperCase());
		if (!Projectile.class.isAssignableFrom(this.type.getEntityClass()))
			throw new ParsingException("The projectile-type value " + entry.getString("projectile-type") + " is not a projectile.");

	}

	@SuppressWarnings("unchecked")
	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target)
				.ifPresent(entity -> entity.launchProjectile((Class<? extends Projectile>) this.type.getEntityClass()));
	}

}
