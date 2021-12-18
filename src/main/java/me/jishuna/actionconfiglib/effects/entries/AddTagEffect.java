package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.TagManager;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "ADD_TAG")
public class AddTagEffect extends Effect {
	private final EntityTarget target;
	private final String tag;

	public AddTagEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.tag = entry.getStringOrThrow("tag");
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetOptional(this.target)
				.ifPresent(entity -> TagManager.getInstance().addTag(entity.getUniqueId(), this.tag));
	}

}
