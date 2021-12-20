package me.jishuna.actionconfiglib.conditions.entries;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "types" })
@RegisterCondition(name = "EFFECT_TYPE")
public class EffectTypeCondition extends Condition {
	private final Set<PotionEffectType> types = new HashSet<>();

	public EffectTypeCondition(ConfigurationEntry entry) throws ParsingException {
		String[] data = entry.getString("types").split(",");

		for (String causeString : data) {
			PotionEffectType type = PotionEffectType.getByName(causeString.toUpperCase());
			if (type == null)
				throw new ParsingException("The effect type \"" + causeString + "\" could not be found.");
			
			this.types.add(type);
		}
	}

	@Override
	public boolean evaluate(ActionContext context) {
		if (context.getEvent() instanceof EntityPotionEffectEvent event) {
			return this.types.contains(event.getModifiedType());
		}
		return false;
	}
}
