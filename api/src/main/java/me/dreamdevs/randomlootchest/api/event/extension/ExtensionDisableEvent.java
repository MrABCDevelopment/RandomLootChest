package me.dreamdevs.randomlootchest.api.event.extension;

import me.dreamdevs.randomlootchest.api.event.ExtensionEvent;
import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extension.Extension;

@Getter
public final class ExtensionDisableEvent extends ExtensionEvent {

    public ExtensionDisableEvent(Extension extension) {
        super(extension);
    }

}