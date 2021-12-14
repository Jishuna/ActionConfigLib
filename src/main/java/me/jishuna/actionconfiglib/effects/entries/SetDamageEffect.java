package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.event.entity.EntityDamageEvent;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@RegisterEffect(name = "SET_DAMAGE")
public class SetDamageEffect extends Effect {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%damage%", "%final_damage%");
	}

	private final CompiledExpression expression;

	public SetDamageEffect(ConfigurationEntry entry) throws ParsingException {
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public void evaluate(ActionContext context) {
		if (context.getEvent()instanceof EntityDamageEvent event) {
			double damage = event.getDamage();
			double finalDamage = event.getFinalDamage();

			event.setDamage(expression.evaluate(damage, finalDamage));
		}
	}

}
