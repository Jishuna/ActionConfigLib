package me.jishuna.actionconfiglib.conditions;

import me.jishuna.actionconfiglib.ActionContext;

public abstract class Condition {
	public abstract boolean evaluate(ActionContext context);
}
