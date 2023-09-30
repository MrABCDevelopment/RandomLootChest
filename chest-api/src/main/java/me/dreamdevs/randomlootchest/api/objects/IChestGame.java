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

    void setTitle(String title);

    void setTime(long time);

    void setMaxItems(int maxItems);

    void setMaxItemsInTheSameType(int maxItemsInTheSameType);

    void setParticle(Particle particle);

    void setUseParticles(boolean useParticles);

    void setParticlesAmount(int amount);

    void setExp(double exp);

}