package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.event.player.PlayerItemDamageEvent;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

@ArgumentFormat(format = { "expression" })
@RegisterEffect(name = "SET_DURABILITY_LOSS")
public class SetDurabilityLossEffect extends Effect {
	private static final EvaluationEnvironment ENV = new EvaluationEnvironment();

	static {
		ENV.setVariableNames("%durability_loss%");
	}

	private final CompiledExpression expression;

	public SetDurabilityLossEffect(ConfigurationEntry entry) throws ParsingException {
		this.expression = entry.getEquationOrThrow("expression", ENV);
	}

	@Override
	public void evaluate(ActionContext context) {
		if (context.getEvent()instanceof PlayerItemDamageEvent event) {
			int damage = event.getDamage();

			event.setDamage((int) expression.evaluate(damage));
		}
	}

}
