package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.LivingEntity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.TagManager;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "tag", "value" })
@RegisterCondition(name = "HAS_TAG")
public class TagCondition extends Condition {
	private final EntityTarget target;
	private final String tag;
	private final boolean value;

	public TagCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.tag = entry.getStringOrThrow("tag");
		this.value = entry.getBoolean("value", true);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		LivingEntity entity = context.getLivingTarget(this.target);
		if (entity == null)
			return false;

		return TagManager.getInstance().hasTag(entity.getUniqueId(), this.tag) == this.value;
	}

}
