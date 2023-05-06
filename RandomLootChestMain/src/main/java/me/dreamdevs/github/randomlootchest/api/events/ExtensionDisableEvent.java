package me.dreamdevs.github.randomlootchest.api.events;

import lombok.Getter;
import me.dreamdevs.github.randomlootchest.api.extensions.Extension;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class ExtensionDisableEvent extends Event {

    private Extension extension;
    private static final HandlerList list = new HandlerList();

    public ExtensionDisableEvent(Extension extension) {
        this.extension = extension;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}