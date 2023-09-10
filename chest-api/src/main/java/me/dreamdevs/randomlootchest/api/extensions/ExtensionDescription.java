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

}