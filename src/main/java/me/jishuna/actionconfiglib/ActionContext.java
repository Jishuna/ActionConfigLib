package me.jishuna.actionconfiglib;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.triggers.Trigger;

public class ActionContext {
	private final Trigger trigger;
	private final Event event;
	private final ItemStack item;
	private final Entity user;
	private final Location origin;
	private final Entity opponent;
	private final Location targetLocation;
	private final Map<String, Object> customData;

	private ActionContext(Trigger trigger, Event event, ItemStack item, Entity user, Location origin, Entity opponent,
			Location targetLocation, Map<String, Object> customData) {
		this.trigger = trigger;
		this.event = event;
		this.item = item;
		this.user = user;
		this.origin = origin;
		this.opponent = opponent;
		this.targetLocation = targetLocation;
		this.customData = customData;
	}
	
	public ItemStack getItemDirect() {
		return this.item;
	}

	public Optional<ItemStack> getItem() {
		return Optional.ofNullable(getItemDirect());
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

	public Location getTargetLocation(LocationTarget target) {
		switch (target) {
		case OPPONENT:
			return (opponent != null) ? opponent.getLocation() : null;
		case ORIGIN:
			return getOrigin();
		case TARGET:
			return getTargetLocation();
		case USER:
			return (user != null) ? user.getLocation() : null;
		default:
			return null;
		}
	}

	public Optional<Location> getTargetLocationOptional(LocationTarget target) {
		return Optional.ofNullable(getTargetLocation(target));
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

	public Location getOrigin() {
		return (origin != null) ? origin.clone() : null;
	}

	public Entity getOpponent() {
		return opponent;
	}

	public Location getTargetLocation() {
		return (targetLocation != null) ? targetLocation.clone() : null;
	}

	public static class Builder {
		private final Trigger trigger;
		private Event event;
		private ItemStack item;
		private Entity user;
		private Entity opponent;
		private Location origin;
		private Location targetLocation;
		private final Map<String, Object> customData = new HashMap<>();

		public Builder(Trigger trigger) {
			this.trigger = trigger;
		}

		public Builder event(Event event) {
			this.event = event;
			return this;
		}
		
		public Builder item(ItemStack item) {
			this.item = item;
			return this;
		}

		public Builder user(Entity user) {
			this.user = user;
			this.origin = user.getLocation();
			return this;
		}

		public Builder opponent(Entity opponent) {
			this.opponent = opponent;
			this.targetLocation = opponent.getLocation();
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
			return new ActionContext(trigger, event, item, user, origin, opponent, targetLocation, customData);
		}
	}
}
