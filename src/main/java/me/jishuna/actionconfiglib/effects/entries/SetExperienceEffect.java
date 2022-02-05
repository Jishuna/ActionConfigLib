package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@ArgumentFormat(format = { "expression" })
@RegisterEffect(name = "SET_EXPERIENCE")
public class SetExperienceEffect extends Effect {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%experience%");
	}

	private final CompiledExpression expression;

	public SetExperienceEffect(ConfigurationEntry entry) throws ParsingException {
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public void evaluate(ActionContext context) {
		if (context.getEvent() instanceof EntityDeathEvent event) {
			int xp = event.getDroppedExp();

			event.setDroppedExp((int) expression.evaluate(xp));
		} else if (context.getEvent() instanceof BlockBreakEvent event) {
			int xp = event.getExpToDrop();

			event.setExpToDrop((int) expression.evaluate(xp));
		}
	}

}
