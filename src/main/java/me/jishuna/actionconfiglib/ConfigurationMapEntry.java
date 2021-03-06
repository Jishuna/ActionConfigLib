package me.jishuna.actionconfiglib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.jishuna.actionconfiglib.exceptions.ParsingException;
import redempt.crunch.CompiledExpression;
import redempt.crunch.Crunch;
import redempt.crunch.exceptions.ExpressionCompilationException;
import redempt.crunch.functional.EvaluationEnvironment;

public class ConfigurationMapEntry extends ConfigurationEntry {
	private final Map<?, ?> entryMap;

	public ConfigurationMapEntry(Map<?, ?> map) {
		this.entryMap = map;
	}

	public String getString(String key) {
		Object entry = this.entryMap.get(key);
		return entry != null ? entry.toString() : null;
	}

	public String getStringOrThrow(String key) throws ParsingException {
		Object entry = this.entryMap.get(key);

		if (entry == null)
			throw new ParsingException("Invalid value \"null\" for key \"" + key + "\", String expected.");

		return entry.toString();
	}

	public boolean getBoolean(String key, boolean def) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Boolean) ? (Boolean) entry : def;
	}

	public boolean getBooleanOrThrow(String key) throws ParsingException {
		Object entry = this.entryMap.get(key);

		if (entry instanceof Boolean bool)
			return bool;
		throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString()) + "\" for key \""
				+ key + "\", True/False expected.");
	}

	public int getIntOrThrow(String key) throws ParsingException {
		Object entry = this.entryMap.get(key);

		if (entry instanceof Number num)
			return num.intValue();
		throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString()) + "\" for key \""
				+ key + "\", Integer expected.");
	}

	public int getInt(String key, int def) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).intValue() : def;
	}

	public long getLong(String key) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).longValue() : 0l;
	}

	public long getLongOrThrow(String key) throws ParsingException {
		Object entry = this.entryMap.get(key);

		if (entry instanceof Number num)
			return num.longValue();
		throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString()) + "\" for key \""
				+ key + "\", Integer expected.");
	}

	public double getDoubleOrThrow(String key) throws ParsingException {
		Object entry = this.entryMap.get(key);

		if (entry instanceof Number num)
			return num.doubleValue();
		throw new ParsingException("Invalid value \"" + (entry == null ? "null" : entry.toString()) + "\" for key \""
				+ key + "\", Number expected.");
	}

	public double getDouble(String key, double def) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).doubleValue() : def;
	}

	public CompiledExpression getEquation(String key, EvaluationEnvironment env) {
		Object entry = this.entryMap.get(key);

		if (entry == null)
			return null;

		try {
			return Crunch.compileExpression(entry.toString(), env);
		} catch (ExpressionCompilationException ex) {
			return null;
		}
	}

	public CompiledExpression getEquationOrThrow(String key, EvaluationEnvironment env) throws ParsingException {
		Object entry = this.entryMap.get(key);
		ParsingException exception = new ParsingException("Invalid value \""
				+ (entry == null ? "null" : entry.toString()) + "\" for key \"" + key + "\", Expression expected.");

		if (entry == null)
			throw exception;

		try {
			return Crunch.compileExpression(entry.toString(), env);
		} catch (ExpressionCompilationException ex) {
			throw exception;
		}
	}

	public List<Map<?, ?>> getMapList(String key) {
		Object entry = this.entryMap.get(key);
		List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();

		if (entry == null || !(entry instanceof List<?> list)) {
			return result;
		}

		for (Object object : list) {
			if (object instanceof Map) {
				result.add((Map<?, ?>) object);
			}
		}
		return result;
	}

	public List<String> getStringList(String key) {
		List<?> list = getList(key);

		if (list == null) {
			return new ArrayList<String>(0);
		}

		List<String> result = new ArrayList<String>();

		for (Object object : list) {
			if ((object instanceof String)) {
				result.add(String.valueOf(object));
			}
		}

		return result;
	}
	
	public boolean has(String key) {
		return this.entryMap.containsKey(key);
	}

	private List<?> getList(String key) {
		Object val = this.entryMap.get(key);
		return (List<?>) ((val instanceof List) ? val : null);
	}

}
