package me.jishuna.actionconfiglib.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import me.jishuna.actionconfiglib.exceptions.ParsingException;

public enum LocationTarget {

	USER, TARGET;

	public static LocationTarget fromString(String name) throws ParsingException {
		name = name.toUpperCase();

		if (!TYPES.contains(name))
			throw new ParsingException("Invalid target location \"" + name + "\", expected user or target.");
		return LocationTarget.valueOf(name);
	}

	private static final Set<String> TYPES = Arrays.stream(LocationTarget.values()).map(Enum::toString)
			.collect(Collectors.toSet());

}
