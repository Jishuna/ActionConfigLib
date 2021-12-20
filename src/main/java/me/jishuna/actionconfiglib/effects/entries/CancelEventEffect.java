package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.event.Cancellable;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;

@ArgumentFormat(format = {})
@RegisterEffect(name = "CANCEL_EVENT")
public class CancelEventEffect extends Effect {

	public CancelEventEffect(ConfigurationEntry entry) {
		// NO OP
	}

	public void evaluate(ActionContext context) {
		if (context.getEvent()instanceof Cancellable event) {
			event.setCancelled(true);
		}
	}
}
