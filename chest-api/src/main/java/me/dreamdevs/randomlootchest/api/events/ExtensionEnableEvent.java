package me.dreamdevs.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ExtensionEnableEvent extends Event {

    private Extension extension;
    private static final HandlerList list = new HandlerList();

    public ExtensionEnableEvent(Extension extension) {
        this.extension = extension;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}