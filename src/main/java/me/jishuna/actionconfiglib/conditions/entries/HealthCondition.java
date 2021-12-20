package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@ArgumentFormat(format = { "target", "expression" })
@RegisterCondition(name = "HEALTH")
public class HealthCondition extends Condition {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%health%", "%max%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public HealthCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		LivingEntity entity = context.getLivingTarget(this.target);
		if (entity == null)
			return false;

		double health = entity.getHealth();
		double max = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

		return this.expression.evaluate(health, max) > 0d;
	}

}
