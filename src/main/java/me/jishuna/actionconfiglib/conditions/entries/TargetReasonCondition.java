package me.jishuna.actionconfiglib.conditions.entries;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;

@RegisterCondition(name = "TARGET_REASON")
public class TargetReasonCondition extends Condition {
	private static final Set<String> ALL_CAUSES = Arrays.stream(TargetReason.values()).map(Enum::toString)
			.collect(Collectors.toSet());
	
	private final Set<TargetReason> causes = EnumSet.noneOf(TargetReason.class);

	public TargetReasonCondition(ConfigurationEntry entry) {
		String[] data = entry.getString("reasons").toUpperCase().split(",");

		for (String causeString : data) {
			if (ALL_CAUSES.contains(causeString)) {
				this.causes.add(TargetReason.valueOf(causeString));
			} else {
				System.err.println(causeString);
			}
		}
	}

	@Override
	public boolean evaluate(ActionContext context) {
		if (context.getEvent()instanceof EntityTargetLivingEntityEvent event) {
			return this.causes.contains(event.getReason());
		}
		return false;
	}
}
