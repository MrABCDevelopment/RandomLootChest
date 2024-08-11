package me.dreamdevs.randomlootchest.api.object;

import org.bukkit.Particle;

import java.io.File;
import java.util.List;

public interface IChestGame {

    /**
     * Gets an Id of chest.
     */

    String getId();

    /**
     * Returns title of chest - title is coloured.
     */

    String getTitle();

    /**
     * This method returns the amount of time to open chest again.
     * Value is stored as a long type of variable.
     */

    long getTime();

    /**
     * This method returns the amount of maximum items that chests may contain.
     */

    int getMaxItems();

    int getMaxItemsInTheSameType();

    File getChestDataFile();

    Particle getParticle();

    int getParticleAmount();

    boolean useParticles();

    /**
     * Return the amount of experience that will be given to the player.
     */

    int getExp();

    List<IRandomItem> getItemStacks();

    void setTitle(String title);

    void setTime(long time);

    void setMaxItems(int maxItems);

    void setMaxItemsInTheSameType(int maxItemsInTheSameType);

    void setParticle(Particle particle);

    void setUseParticles(boolean useParticles);

    void setParticlesAmount(int amount);

    void setExp(int exp);

}