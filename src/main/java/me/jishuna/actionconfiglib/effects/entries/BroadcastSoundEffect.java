package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.Sound;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.LocationTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target-location", "sound", "volume", "pitch", "x-offset", "y-offset", "z-offset" })
@RegisterEffect(name = "BROADCAST_SOUND")
public class BroadcastSoundEffect extends Effect {
	private final LocationTarget target;
	private final Sound sound;
	private final float volume;
	private final float pitch;
	private final double xOffset;
	private final double yOffset;
	private final double zOffset;

	public BroadcastSoundEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = LocationTarget.fromString(entry.getString("target-location"));

		String sound = entry.getStringOrThrow("sound");

		this.sound = Enums.getIfPresent(Sound.class, sound.toUpperCase()).toJavaUtil()
				.orElseThrow(() -> new ParsingException("The sound \"" + sound + "\" could not be found."));

		this.volume = (float) entry.getDoubleOrThrow("volume");
		this.pitch = (float) entry.getDoubleOrThrow("pitch");

		this.xOffset = entry.getDouble("x-offset", 0);
		this.yOffset = entry.getDouble("y-offset", 0);
		this.zOffset = entry.getDouble("z-offset", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetLocationOptional(this.target).ifPresent(location -> {
			location.getWorld().playSound(location.add(this.xOffset, this.yOffset, this.zOffset), sound, volume, pitch);
		});
	}
}
