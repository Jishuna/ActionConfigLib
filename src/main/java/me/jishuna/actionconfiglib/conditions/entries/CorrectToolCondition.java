package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target-location", "value" })
@RegisterCondition(name = "CORRECT_TOOL")
public class CorrectToolCondition extends Condition {

	private final LocationTarget target;
	private final boolean value;

	public CorrectToolCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = LocationTarget.fromString(entry.getString("target-location"));
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Location location = context.getTargetLocation(this.target);
		ItemStack item = context.getItemDirect();

		if (item == null || location == null)
			return false;

		return location.getBlock().isPreferredTool(item) == this.value;
	}

}
