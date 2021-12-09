package me.jishuna.actionconfiglib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

public class Action {

	private final String name;
	private final List<Component> components;

	public Action(ActionConfigLib instance, ConfigurationSection section) {
		this.name = section.getString("name");
		this.components = new ArrayList<>(Arrays.asList(instance.parseComponents(section.getMapList("components"))));
	}

	public void handleAction(ActionContext context) {
		this.components.forEach(component -> component.handleAction(context));
	}

	public String getName() {
		return name;
	}
}
