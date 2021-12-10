package me.jishuna.actionconfiglib.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum EntityTarget {

	USER, OPPONENT;

	public static EntityTarget fromString(String name) {
		name = name.toUpperCase();

		if (!TYPES.contains(name))
			return null;
		return EntityTarget.valueOf(name);
	}

	private static final Set<String> TYPES = Arrays.stream(EntityTarget.values()).map(Enum::toString)
			.collect(Collectors.toSet());

}
