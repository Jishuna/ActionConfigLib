package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.EntityEffect;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "effect" })
@RegisterEffect(name = "ENTITY_EFFECT")
public class EntityEffectEffect extends Effect {
	private final EntityTarget target;
	private final EntityEffect effect;

	public EntityEffectEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		String effect = entry.getStringOrThrow("effect");

		this.effect = Enums.getIfPresent(EntityEffect.class, effect.toUpperCase()).toJavaUtil()
				.orElseThrow(() -> new ParsingException("The entity effect \"" + effect + "\" could not be found."));
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> entity.playEffect(this.effect));
	}
}
