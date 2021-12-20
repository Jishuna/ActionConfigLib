package me.jishuna.actionconfiglib.conditions.entries;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "causes" })
@RegisterCondition(name = "DAMAGE_CAUSE")
public class DamageCauseCondition extends Condition {
	private final Set<DamageCause> causes = EnumSet.noneOf(DamageCause.class);

	public DamageCauseCondition(ConfigurationEntry entry) throws ParsingException {
		String[] data = entry.getString("causes").split(",");

		for (String causeString : data) {
			causes.add(Enums.getIfPresent(DamageCause.class, causeString.toUpperCase()).toJavaUtil().orElseThrow(
					() -> new ParsingException("The damage cause \"" + causeString + "\" could not be found.")));
		}
	}

	@Override
	public boolean evaluate(ActionContext context) {
		if (context.getEvent()instanceof EntityDamageEvent event) {
			return this.causes.contains(event.getCause());
		}
		return false;
	}
}
