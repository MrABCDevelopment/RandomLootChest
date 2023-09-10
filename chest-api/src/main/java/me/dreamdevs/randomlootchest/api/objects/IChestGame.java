package me.dreamdevs.randomlootchest.api.objects;

import org.bukkit.Particle;

import java.io.File;
import java.util.List;

public interface IChestGame {

    String getId();

    String getTitle();

    long getTime();

    int getMaxItems();

    int getMaxItemsInTheSameType();

    File getChestDataFile();

    Particle getParticle();

    int getParticleAmount();

    boolean useParticles();

    double getExp();

    List<IRandomItem> getItemStacks();

}