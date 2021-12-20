package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "name", "attribute", "opperation", "value", "scale-health" })
@RegisterEffect(name = "ADD_ATTRIBUTE")
public class AddAttributeEffect extends Effect {
	private final Attribute attribute;
	private final AttributeModifier modifier;
	private final EntityTarget target;
	private final String name;
	private final boolean scaleHealth;

	public AddAttributeEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		this.name = entry.getString("name");
		this.attribute = Attribute.valueOf(entry.getString("attribute").toUpperCase());
		this.scaleHealth = entry.getBoolean("scale-health", false);

		Operation operation = Operation.valueOf(entry.getString("opperation").toUpperCase());
		double value = entry.getDoubleOrThrow("value");

		this.modifier = new AttributeModifier(name, value, operation);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> {
			AttributeInstance instance = entity.getAttribute(this.attribute);
			for (AttributeModifier modifier : instance.getModifiers()) {
				if (modifier.getName().equals(this.name))
					instance.removeModifier(modifier);
			}
			instance.addModifier(this.modifier);
			
			if (this.attribute == Attribute.GENERIC_MAX_HEALTH && this.scaleHealth && entity instanceof Player player) {
				player.setHealthScale(instance.getValue());
				player.setHealthScaled(true);
			}
		});
	}

}
