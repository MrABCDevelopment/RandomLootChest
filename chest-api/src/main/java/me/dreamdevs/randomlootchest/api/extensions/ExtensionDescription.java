package me.dreamdevs.randomlootchest.api.extensions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter @Setter
public class ExtensionDescription {

    private String extensionName;
    private String extensionVersion;
    private String extensionMain;
    private String extensionAuthor;
    private Material extensionMaterial;

    public ExtensionDescription() {}

    public ExtensionDescription(String extensionName, String extensionVersion, String extensionMain) {
        this.extensionName = extensionName;
        this.extensionVersion = extensionVersion;
        this.extensionMain = extensionMain;
    }

    public boolean verify() {
        return extensionName != null && extensionVersion != null && extensionMain != null;
    }

}