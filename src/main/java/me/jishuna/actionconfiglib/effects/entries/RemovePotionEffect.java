package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.potion.PotionEffectType;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "REMOVE_EFFECT")
public class RemovePotionEffect extends Effect {
	private final PotionEffectType type;
	private final EntityTarget target;

	public RemovePotionEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		this.type = PotionEffectType.getByName(entry.getString("effect-type").toUpperCase());
		if (type == null)
			throw new ParsingException("The effect type \"" + entry.getString("effect-type") + "\" could not be found.");
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> entity.removePotionEffect(this.type));
	}

}
