package me.jishuna.actionconfiglib.effects.entries;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@RegisterEffect(name = "DELAY")
public class DelayEffect extends Effect {
	private final int delay;

	public DelayEffect(ConfigurationEntry entry) throws ParsingException {
		this.delay = entry.getIntOrThrow("ticks");

		if (delay <= 0)
			throw new ParsingException("The ticks value " + delay + " is invalid, it must be greater than 0.");
	}

	@Override
	public void evaluate(ActionContext context) {
		// The delay effect is handled differently due to the nature of its function
	}

	public int getDelay() {
		return delay;
	}

}
