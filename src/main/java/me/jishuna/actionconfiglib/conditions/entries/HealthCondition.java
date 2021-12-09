package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.EntityTarget;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import redempt.crunch.CompiledExpression;
import redempt.crunch.Crunch;
import redempt.crunch.functional.EvaluationEnvironment;

@RegisterCondition(name = "HEALTH")
public class HealthCondition extends Condition {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%health%", "%max%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public HealthCondition(ConfigurationEntry entry) {
		this.target = EntityTarget.fromString(entry.getString("target"));
		expression = Crunch.compileExpression(entry.getString("expression"), ENV);
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
