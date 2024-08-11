package me.dreamdevs.randomlootchest.api.event;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extension.Extension;

public class ExtensionEvent extends RandomLootChestEvent {

	private final @Getter Extension extension;

	public ExtensionEvent(Extension extension) {
		this.extension = extension;
	}

}