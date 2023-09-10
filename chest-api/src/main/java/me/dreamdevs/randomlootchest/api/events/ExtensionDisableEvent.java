package me.dreamdevs.randomlootchest.api.events;

import me.dreamdevs.randomlootchest.api.extensions.Extension;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public final class ExtensionDisableEvent extends Event {

    private final Extension extension;
    private static final HandlerList list = new HandlerList();

    public ExtensionDisableEvent(Extension extension) {
        this.extension = extension;
    }

    @Override
    public HandlerList getHandlers() {
        return list;
    }
}