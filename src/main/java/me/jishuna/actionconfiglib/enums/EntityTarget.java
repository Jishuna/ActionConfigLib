package me.jishuna.actionconfiglib.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import me.jishuna.actionconfiglib.exceptions.ParsingException;

public enum EntityTarget {

	USER, OPPONENT;

	public static EntityTarget fromString(String name) throws ParsingException {
		name = name.toUpperCase();

		if (!TYPES.contains(name))
			throw new ParsingException("Invalid target \"" + name + "\", expected user or opponent.");
		return EntityTarget.valueOf(name);
	}

	public static EntityTarget getOpposite(EntityTarget target) {
		return switch (target) {
		case USER -> OPPONENT;
		case OPPONENT -> USER;
		};
	}

	private static final Set<String> TYPES = Arrays.stream(EntityTarget.values()).map(Enum::toString)
			.collect(Collectors.toSet());

}
