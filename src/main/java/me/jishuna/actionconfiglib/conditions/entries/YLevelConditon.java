package me.jishuna.actionconfiglib.conditions.entries;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@RegisterCondition(name = "Y_LEVEL")
public class YLevelConditon extends Condition {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%y%", "%min%", "%max%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public YLevelConditon(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public boolean evaluate(ActionContext context) {
		Entity entity = context.getTarget(this.target);
		if (entity == null)
			return false;

		World world = entity.getWorld();

		double y = entity.getLocation().getY();
		int min = world.getMinHeight();
		int max = world.getMaxHeight();

		return this.expression.evaluate(y, min, max) > 0d;
	}

}
