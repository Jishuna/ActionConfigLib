package me.jishuna.actionconfiglib.conditions.entries;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "types" })
@RegisterCondition(name = "ENTITY_TYPE")
public class EntityTypeCondition extends Condition {
	private final Set<EntityType> types = EnumSet.noneOf(EntityType.class);
	private final EntityTarget target;

	public EntityTypeCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		String[] data = entry.getString("types").split(",");

		for (String typeString : data) {
			types.add(Enums.getIfPresent(EntityType.class, typeString.toUpperCase()).toJavaUtil().orElseThrow(
					() -> new ParsingException("The entity type \"" + typeString + "\" could not be found.")));
		}
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);

		if (entity == null)
			return false;
		
		return this.types.contains(entity.getType());
	}
}
