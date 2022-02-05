package me.jishuna.actionconfiglib.conditions.entries;

import java.util.concurrent.ThreadLocalRandom;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.RegisterCondition;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "chance" })
@RegisterCondition(name = "CHANCE")
public class ChanceCondition extends Condition {
	private final double chance;

	public ChanceCondition(ConfigurationEntry entry) throws ParsingException {
		this.chance = entry.getDoubleOrThrow("chance");

		if (this.chance <= 0)
			throw new ParsingException("Chance must be greater than 0");
		if (this.chance > 100)
			throw new ParsingException("Chance must not be greater than 100");
	}

	@Override
	public boolean evaluate(ActionContext context) {
		return ThreadLocalRandom.current().nextDouble() * 100 < this.chance;
	}
}
