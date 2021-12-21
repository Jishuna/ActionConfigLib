package me.jishuna.actionconfiglib.effects.entries;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.base.Enums;

import me.jishuna.actionconfiglib.ActionContext;
import me.jishuna.actionconfiglib.ArgumentFormat;
import me.jishuna.actionconfiglib.ConfigurationEntry;
import me.jishuna.actionconfiglib.effects.Effect;
import me.jishuna.actionconfiglib.effects.RegisterEffect;
import me.jishuna.actionconfiglib.enums.EntityTarget;
import me.jishuna.actionconfiglib.exceptions.ParsingException;

@ArgumentFormat(format = { "target", "sound", "volume", "pitch", "x-offset", "y-offset", "z-offset" })
@RegisterEffect(name = "PLAY_SOUND")
public class SoundEffect extends Effect {
	protected final EntityTarget target;
	protected final Sound sound;
	protected final float volume;
	protected final float pitch;
	protected final double xOffset;
	protected final double yOffset;
	protected final double zOffset;

	public SoundEffect(ConfigurationEntry entry) throws ParsingException {
		this.target = EntityTarget.fromString(entry.getString("target"));

		String sound = entry.getStringOrThrow("sound");

		this.sound = Enums.getIfPresent(Sound.class, sound.toUpperCase()).toJavaUtil()
				.orElseThrow(() -> new ParsingException("The sound \"" + sound + "\" could not be found."));

		this.volume = (float) entry.getDoubleOrThrow("volume");
		this.pitch = (float) entry.getDoubleOrThrow("pitch");

		this.xOffset = entry.getDouble("xOffset", 0);
		this.yOffset = entry.getDouble("yOffset", 0);
		this.zOffset = entry.getDouble("zOffset", 0);
	}

	@Override
	public void evaluate(ActionContext context) {
		context.getTargetOptional(this.target).ifPresent(entity -> {
			if (entity instanceof Player player)
				player.playSound(player.getLocation().add(this.xOffset, this.yOffset, this.zOffset), sound, volume,
						pitch);
		});
	}
}
