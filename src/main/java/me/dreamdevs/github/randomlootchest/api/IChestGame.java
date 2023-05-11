package me.dreamdevs.github.randomlootchest.api;

import me.dreamdevs.github.randomlootchest.api.objects.RandomItem;

import java.io.File;
import java.util.List;

/**
 * This interface allows to create new chests in extensions.
 * It opens new way to create and manage chests.
 */

public interface IChestGame {

    String getId();

    String getTitle();

    long getTime();

    int getMaxItems();

    int getMaxItemsInTheSameType();

    File getChestFile();

    String getParticleType();

    int getParticleAmount();

    boolean isParticleUse();

    List<RandomItem> getItems();

}