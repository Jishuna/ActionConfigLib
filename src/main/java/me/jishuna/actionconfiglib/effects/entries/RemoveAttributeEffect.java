package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "name", "attribute", "scale-health"})
@RegisterEffect(name = "REMOVE_ATTRIBUTE")
public class RemoveAttributeEffect extends Effect {
	private final Attribute attribute;
	private final EntityTarget target;
	private final boolean scaleHealth;

	private final String name;

	public RemoveAttributeEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		this.name = entry.getString("name");
		this.attribute = Attribute.valueOf(entry.getString("attribute").toUpperCase());
		this.scaleHealth = entry.getBoolean("scale-health", false);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> {
			AttributeInstance instance = entity.getAttribute(this.attribute);
			for (AttributeModifier modifier : instance.getModifiers()) {
				if (modifier.getName().equals(this.name))
					instance.removeModifier(modifier);
			}

			if (this.attribute == Attribute.GENERIC_MAX_HEALTH && this.scaleHealth && entity instanceof Player player) {
				player.setHealthScale(instance.getValue());
				player.setHealthScaled(false);
			}
		});
	}

}
