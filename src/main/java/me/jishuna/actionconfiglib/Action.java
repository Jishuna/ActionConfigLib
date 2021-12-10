package me.jishuna.actionconfiglib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import me.jishuna.actionconfiglib.exceptions.ParsingException;

public class Action {

	private final String name;
	private final List<Component> components;

	public Action(ActionConfigLib instance, ConfigurationSection section) throws ParsingException {
		this.name = section.getString("name");
		try {
			this.components = new ArrayList<>(Arrays.asList(instance.parseComponentsOrThrow(section.getMapList("components"))));
		} catch (ParsingException ex) {
			throw new ParsingException("Error parsing action \"" + this.name + "\":", ex);
		}
	}

	public void handleAction(ActionContext context) {
		this.components.forEach(component -> component.handleAction(context));
	}

	public String getName() {
		return name;
	}
}
