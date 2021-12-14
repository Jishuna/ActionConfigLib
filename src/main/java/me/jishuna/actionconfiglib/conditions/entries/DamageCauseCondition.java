package me.jishuna.actionconfiglib.conditions.entries;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "DAMAGE_CAUSE")
public class DamageCauseCondition extends Condition {
	private static final Set<String> ALL_CAUSES = Arrays.stream(DamageCause.values()).map(Enum::toString)
			.collect(Collectors.toSet());
	
	private final Set<DamageCause> causes = EnumSet.noneOf(DamageCause.class);

	public DamageCauseCondition(ConfigurationEntry entry) {
		String[] data = entry.getString("causes").toUpperCase().split(",");

		for (String causeString : data) {
			if (ALL_CAUSES.contains(causeString)) {
				this.causes.add(DamageCause.valueOf(causeString));
			} else {
				System.err.println(causeString);
			}
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
