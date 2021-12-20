package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@ArgumentFormat(format = { "target", "expression" })
@RegisterEffect(name = "SET_AIR")
public class SetAirEffect extends Effect {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%air%", "%max%");
	}

	private final EntityTarget target;
	private final CompiledExpression expression;

	public SetAirEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getLivingTargetOptional(this.target).ifPresent(entity -> {
			int air = entity.getRemainingAir();
			int maxAir = entity.getMaximumAir();

			entity.setRemainingAir((int) Math.round(this.expression.evaluate(air, maxAir)));
		});
	}

}
