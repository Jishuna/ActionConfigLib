package me.jishuna.actionconfiglib.conditions.entries;

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
@RegisterCondition(name = "AIR")
public class AirCondition extends Condition {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%air%", "%max%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public AirCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		LivingEntity entity = context.getLivingTarget(this.target);
		
		int air = entity.getRemainingAir();
		int maxAir = entity.getMaximumAir();

		return this.expression.evaluate(air, maxAir) > 0d;
	}
}
