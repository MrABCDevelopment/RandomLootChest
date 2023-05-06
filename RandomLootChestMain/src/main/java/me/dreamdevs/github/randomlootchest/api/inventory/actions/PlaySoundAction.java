package me.dreamdevs.github.randomlootchest.api.inventory.actions;

import me.dreamdevs.github.randomlootchest.api.events.ClickInventoryEvent;
import me.dreamdevs.github.randomlootchest.api.inventory.Action;
import me.dreamdevs.github.randomlootchest.utils.VersionUtil;
import org.bukkit.Sound;

public class PlaySoundAction implements Action {

    private Sound sound;
    private float volume;
    private float pitch;

    public PlaySoundAction(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void performAction(ClickInventoryEvent event) {
        if(!VersionUtil.is1_8_orOlder())
            event.getPlayer().playSound(event.getPlayer().getLocation(), sound, volume, pitch);
    }

}