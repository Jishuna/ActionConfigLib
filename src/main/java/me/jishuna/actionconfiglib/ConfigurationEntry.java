package me.jishuna.actionconfiglib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigurationEntry {
	private final Map<?, ?> entryMap;

	public ConfigurationEntry(Map<?, ?> map) {
		this.entryMap = map;
	}

	public String getString(String key) {
		Object entry = this.entryMap.get(key);
		return entry != null ? entry.toString() : null;
	}
	
	public boolean getBoolean(String key, boolean def) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Boolean) ? (Boolean) entry : def;
	}

	public int getInt(String key) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).intValue() : 0;
	}

	public long getLong(String key) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).longValue() : 0l;
	}

	public double getDouble(String key) {
		Object entry = this.entryMap.get(key);
		return (entry instanceof Number) ? ((Number) entry).doubleValue() : 0d;
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

}
