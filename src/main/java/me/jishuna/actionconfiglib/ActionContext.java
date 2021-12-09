package me.jishuna.actionconfiglib;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import me.jishuna.actionconfiglib.triggers.Trigger;

public class ActionContext {
	private final Trigger trigger;
	private final Event event;
	private final Entity user;
	private final Location userLocation;
	private final Entity opponent;
	private final Location targetLocation;
	private final Map<String, Object> customData;

	private ActionContext(Trigger trigger, Event event, Entity user, Location userLocation, Entity opponent,
			Location targetLocation, Map<String, Object> customData) {
		this.trigger = trigger;
		this.event = event;
		this.user = user;
		this.userLocation = userLocation;
		this.opponent = opponent;
		this.targetLocation = targetLocation;
		this.customData = customData;
	}

	public Entity getTarget(EntityTarget target) {
		if (target == EntityTarget.USER) {
			return user;
		} else {
			return opponent;
		}
	}

	public Optional<Entity> getTargetOptional(EntityTarget target) {
		return Optional.ofNullable(getTarget(target));
	}

	public LivingEntity getLivingTarget(EntityTarget target) {
		Entity entity = getTarget(target);

		if (entity instanceof LivingEntity living)
			return living;
		return null;
	}

	public Optional<LivingEntity> getLivingTargetOptional(EntityTarget target) {
		return Optional.ofNullable(getLivingTarget(target));
	}

	public Object getCustomEntry(String key) {
		return customData.get(key);
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public Event getEvent() {
		return event;
	}

	public Entity getUser() {
		return user;
	}

	public Location getUserLocation() {
		return userLocation;
	}

	public Entity getOpponent() {
		return opponent;
	}

	public Location getTargetLocation() {
		return targetLocation;
	}

	public static class Builder {
		private final Trigger trigger;
		private Event event;
		private Entity user;
		private Entity opponent;
		private Location userLocation;
		private Location targetLocation;
		private final Map<String, Object> customData = new HashMap<>();

		public Builder(Trigger trigger) {
			this.trigger = trigger;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}

		public Builder user(Entity user) {
			this.user = user;
			this.userLocation = user.getLocation();
			return this;
		}

		public Builder opponent(Entity opponent) {
			this.opponent = opponent;
			return this;
		}

		public Builder targetLocation(Location location) {
			this.targetLocation = location;
			return this;
		}

		public Builder customData(String key, Object value) {
			this.customData.put(key, value);
			return this;
		}

		public ActionContext build() {
			return new ActionContext(trigger, event, user, userLocation, opponent, targetLocation, customData);
		}
	}
}
