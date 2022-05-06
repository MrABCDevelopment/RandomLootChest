package me.mrsandking.github.randomlootchest.inventory.actions;

import me.mrsandking.github.randomlootchest.events.RLCClickInventoryEvent;
import me.mrsandking.github.randomlootchest.inventory.Action;
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
    public void performAction(RLCClickInventoryEvent event) {
        event.getPlayer().playSound(event.getPlayer().getLocation(), sound, volume, pitch);
    }

}