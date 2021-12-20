package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "item-type", "ticks" })
@RegisterEffect(name = "ITEM_COOLDOWN")
public class ItemCooldownEffect extends Effect {
	private final EntityTarget target;
	private final Material type;
	private final int ticks;

	public ItemCooldownEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.type = Material.matchMaterial(entry.getString("item-type"));
		if (this.type == null)
			throw new ParsingException("The item-type value " + entry.getString("item-type") + " could not be found.");

		this.ticks = entry.getIntOrThrow("ticks");
		if (ticks < 0)
			throw new ParsingException("The ticks value " + ticks + " is invalid, it must be greater than or equal to 0.");

	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> {
			if (entity instanceof Player player) {
				player.setCooldown(this.type, this.ticks);
			}
		});
	}

}
