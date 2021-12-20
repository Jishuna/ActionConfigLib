package me.jishuna.actionconfiglib;

import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.functional.EvaluationEnvironment;

public abstract class ConfigurationEntry {

	public abstract String getString(String key);

	public abstract String getStringOrThrow(String key) throws ParsingException;

	public abstract boolean getBoolean(String key, boolean def);

	public abstract boolean getBooleanOrThrow(String key) throws ParsingException;

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public abstract int getIntOrThrow(String key) throws ParsingException;

	public abstract int getInt(String key, int def);

	public abstract long getLong(String key);
	
	public abstract long getLongOrThrow(String key) throws ParsingException;

	public double getDouble(String key) {
		return getDouble(key, 0);
	}

	public abstract double getDoubleOrThrow(String key) throws ParsingException;
	
	public abstract double getDouble(String key, double def);

	public abstract CompiledExpression getEquation(String key, EvaluationEnvironment env);

	public abstract CompiledExpression getEquationOrThrow(String key, EvaluationEnvironment env) throws ParsingException;

	public abstract boolean has(String key);
}