package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "ADD_EFFECT")
public class PotionEffectEffect extends Effect {
	private final PotionEffect effect;
	private final EntityTarget target;

	public PotionEffectEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		PotionEffectType type = PotionEffectType.getByName(entry.getString("effect-type").toUpperCase());
		if (type == null)
			throw new ParsingException("The effect type \"" + type + "\" could not be found.");

		int duration = entry.getIntOrThrow("duration");
		if (duration <= 0)
			throw new ParsingException("The duration value " + duration + " is invalid, it must be greater than 0.");

		int level = entry.getIntOrThrow("level");
		if (level <= 0)
			throw new ParsingException("The level value " + level + " is invalid, it must be greater than 0.");

		boolean ambient = entry.getBoolean("ambient", false);
		boolean hidden = entry.getBoolean("hidden", false);

		this.effect = new PotionEffect(type, duration, level - 1, ambient, !hidden);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> entity.addPotionEffect(this.effect));
	}

}
