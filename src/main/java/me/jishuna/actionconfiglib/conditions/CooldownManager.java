package me.jishuna.actionconfiglib.conditions;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CooldownManager {
	private final Cache<UUID, Long> cooldownCache;
	private final long milliseconds;

	public CooldownManager(long ticks) {
		this.milliseconds = ticks * 50;
		this.cooldownCache = CacheBuilder.newBuilder().expireAfterWrite(this.milliseconds, TimeUnit.MILLISECONDS)
				.build();
	}

	public void setCooldown(UUID id) {
		this.cooldownCache.put(id, this.milliseconds);
	}

	public long getCooldownTime(UUID id) {
		Long time = this.cooldownCache.getIfPresent(id);

		if (time == null)
			return 0;

		return time - System.currentTimeMillis();
	}

	public boolean isOnCooldown(UUID id) {
		return this.cooldownCache.asMap().containsKey(id);
	}

}
