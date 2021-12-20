package me.jishuna.actionconfiglib;

import java.util.Map;

import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.Crunch;
import redempt.crunch.exceptions.ExpressionCompilationException;
import redempt.crunch.functional.EvaluationEnvironment;

public class ConfigurationListEntry extends ConfigurationEntry {
	private final Map<String, String> entryMap;

	public ConfigurationListEntry(Map<String, String> map) {
		this.entryMap = map;
	}

	public String getString(String key) {
		String entry = this.entryMap.get(key);
		return entry != null ? entry : null;
	}

	public String getStringOrThrow(String key) throws ParsingException {
		String entry = this.entryMap.get(key);

		if (entry == null)
			throw new ParsingException("Invalid value \"null\" for key \"" + key + "\", String expected.");

		return entry;
	}

	public boolean getBoolean(String key, boolean def) {
		String entry = this.entryMap.get(key);
		return (entry == null) ? def : Boolean.parseBoolean(entry);
	}

	public boolean getBooleanOrThrow(String key) throws ParsingException {
		String entry = this.entryMap.get(key);

		if (entry == null)
			throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString())
					+ "\" for key \"" + key + "\", True/False expected.");
		return Boolean.parseBoolean(entry);

	}

	public int getInt(String key) {
		return getInt(key, 0);
	}

	public int getIntOrThrow(String key) throws ParsingException {
		String entry = this.entryMap.get(key);

		try {
			return Integer.parseInt(entry);
		} catch (NumberFormatException ex) {
			throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString())
					+ "\" for key \"" + key + "\", Integer expected.");
		}
	}

	public int getInt(String key, int def) {
		String entry = this.entryMap.get(key);

		try {
			return Integer.parseInt(entry);
		} catch (NumberFormatException ex) {
			return def;
		}
	}

	public long getLong(String key) {
		String entry = this.entryMap.get(key);

		try {
			return Long.parseLong(entry);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	public long getLongOrThrow(String key) throws ParsingException {
		String entry = this.entryMap.get(key);

		try {
			return Long.parseLong(entry);
		} catch (NumberFormatException ex) {
			throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString())
					+ "\" for key \"" + key + "\", Integer expected.");
		}
	}

	public double getDoubleOrThrow(String key) throws ParsingException {
		String entry = this.entryMap.get(key);

		try {
			return Double.parseDouble(entry);
		} catch (NumberFormatException ex) {
			throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString())
					+ "\" for key \"" + key + "\", Number expected.");
		}
	}

	public double getDouble(String key, double def) {
		String entry = this.entryMap.get(key);

		try {
			return Double.parseDouble(entry);
		} catch (NumberFormatException ex) {
			return def;
		}
	}

	public CompiledExpression getEquation(String key, EvaluationEnvironment env) {
		String entry = this.entryMap.get(key);

		if (entry == null)
			return null;

		try {
			return Crunch.compileExpression(entry, env);
		} catch (ExpressionCompilationException ex) {
			return null;
		}
	}

	public CompiledExpression getEquationOrThrow(String key, EvaluationEnvironment env) throws ParsingException {
		String entry = this.entryMap.get(key);
		ParsingException exception = new ParsingException("Invalid value \""
				+ (entry == null ? "null" : entry.toString()) + "\" for key \"" + key + "\", Expression expected.");

		if (entry == null)
			throw exception;

		try {
			return Crunch.compileExpression(entry, env);
		} catch (ExpressionCompilationException ex) {
			throw exception;
		}
	}
	
	public boolean has(String key) {
		return this.entryMap.containsKey(key);
	}
}
