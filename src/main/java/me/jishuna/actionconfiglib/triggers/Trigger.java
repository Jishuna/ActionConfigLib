package me.jishuna.actionconfiglib.triggers;

public class Trigger {
	private final String key;

	public Trigger(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return this.key;
	}
}
