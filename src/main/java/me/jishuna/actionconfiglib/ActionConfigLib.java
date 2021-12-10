package me.jishuna.actionconfiglib;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.actionconfiglib.conditions.Condition;
import me.jishuna.actionconfiglib.conditions.ConditionRegistry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.EffectRegistry;
import me.jishuna.actionconfiglib.exceptions.ParsingException;
import me.jishuna.actionconfiglib.triggers.TriggerRegistry;

public class ActionConfigLib {

	private final JavaPlugin plugin;
	private final ConditionRegistry conditionRegistry;
	private final EffectRegistry effectRegistry;
	private final TriggerRegistry triggerRegistry;

	private ActionConfigLib(JavaPlugin plugin) {
		this.plugin = plugin;

		this.conditionRegistry = new ConditionRegistry();
		this.effectRegistry = new EffectRegistry();
		this.triggerRegistry = new TriggerRegistry();
	}

	public Action parseAction(ConfigurationSection section) {
		try {
			return new Action(this, section);
		} catch (ParsingException ex) {
			ex.log(this.plugin.getLogger(), 0);
			return null;
		}
	}

	public Component[] parseComponents(List<Map<?, ?>> mapList) {
		int size = mapList.size();
		Component[] components = new Component[size];

		for (int i = 0; i < size; i++) {
			Map<?, ?> map = mapList.get(i);
			if (map != null)
				try {
					components[i] = new Component(this, new ConfigurationEntry(map));
				} catch (ParsingException ex) {
					new ParsingException("Error parsing components:", ex).log(this.plugin.getLogger(), 0);
				}
		}
		return components;
	}

	protected Component[] parseComponentsOrThrow(List<Map<?, ?>> mapList) throws ParsingException {
		int size = mapList.size();
		Component[] components = new Component[size];

		for (int i = 0; i < size; i++) {
			Map<?, ?> map = mapList.get(i);
			if (map != null)
				try {
					components[i] = new Component(this, new ConfigurationEntry(map));
				} catch (ParsingException ex) {
					throw new ParsingException("Error parsing components:", ex);
				}
		}
		return components;
	}

	public void registerEffect(String name, Class<? extends Effect> clazz) {
		this.effectRegistry.registerEffect(name, clazz);
	}

	public void registerCondition(String name, Class<? extends Condition> clazz) {
		this.conditionRegistry.registerCondition(name, clazz);
	}

	public JavaPlugin getOwningPlugin() {
		return plugin;
	}

	public ConditionRegistry getConditionRegistry() {
		return conditionRegistry;
	}

	public EffectRegistry getEffectRegistry() {
		return effectRegistry;
	}

	public TriggerRegistry getTriggerRegistry() {
		return triggerRegistry;
	}

	public static ActionConfigLib createInstance(JavaPlugin plugin) {
		return new ActionConfigLib(plugin);
	}

}
