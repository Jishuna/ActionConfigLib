package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
@RegisterCondition(name = "EXP_LEVEL")
public class ExpLevelCondition extends Condition {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%level%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public ExpLevelCondition(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		LivingEntity entity = context.getLivingTarget(this.target);
		if (!(entity instanceof Player player))
			return false;

		return this.expression.evaluate(player.getLevel()) > 0d;
	}
}
