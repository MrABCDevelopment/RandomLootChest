package me.dreamdevs.randomlootchest.api.event.extension;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.event.ExtensionEvent;
import me.dreamdevs.randomlootchest.api.extension.Extension;

@Getter
public class ExtensionEnableEvent extends ExtensionEvent {

    public ExtensionEnableEvent(Extension extension) {
        super(extension);
    }

}