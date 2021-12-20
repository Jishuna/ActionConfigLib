package me.jishuna.actionconfiglib.conditions.entries;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "reasons" })
@RegisterCondition(name = "TARGET_REASON")
public class TargetReasonCondition extends Condition {

	private final Set<TargetReason> causes = EnumSet.noneOf(TargetReason.class);

	public TargetReasonCondition(ConfigurationEntry entry) throws ParsingException {
		String[] data = entry.getString("reasons").split(",");

		for (String targetReason : data) {
			causes.add(Enums.getIfPresent(TargetReason.class, targetReason.toUpperCase()).toJavaUtil().orElseThrow(
					() -> new ParsingException("The target reason \"" + targetReason + "\" could not be found.")));
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
