package me.dreamdevs.randomlootchest.objects;

import lombok.Setter;
import me.dreamdevs.randomlootchest.api.objects.IRandomItem;
import me.dreamdevs.randomlootchest.RandomLootChestMain;
import me.dreamdevs.randomlootchest.api.objects.IChestGame;
import me.dreamdevs.randomlootchest.api.utils.Util;
import org.bukkit.Particle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Setter
public class ChestGame implements IChestGame {

    private final String id;
    private String title;
    private long time;
    private int maxItems;
    private int maxItemsInTheSameType;
    private File chestFile;
    private Particle particleType;
    private int particleAmount;
    private boolean particleUse;
    private double exp;
    private List<IRandomItem> items;

    public ChestGame(String id) {
        this.id = id;
        this.items = new ArrayList<>();
        chestFile = new File(RandomLootChestMain.getInstance().getDataFolder(), "chests/"+id+".yml");
        Util.tryCreateFile(chestFile);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public int getMaxItems() {
        return this.maxItems;
    }

    @Override
    public int getMaxItemsInTheSameType() {
        return this.maxItemsInTheSameType;
    }

    @Override
    public File getChestDataFile() {
        return this.chestFile;
    }

    @Override
    public Particle getParticle() {
        return this.particleType;
    }

    @Override
    public int getParticleAmount() {
        return this.particleAmount;
    }

    @Override
    public boolean useParticles() {
        return this.particleUse;
    }

    @Override
    public double getExp() {
        return this.exp;
    }

    @Override
    public List<IRandomItem> getItemStacks() {
        return this.items;
    }

    @Override
    public void setParticle(Particle particle) {
        this.particleType = particle;
    }

    @Override
    public void setUseParticles(boolean useParticles) {
        this.particleUse = useParticles;
    }

    @Override
    public void setParticlesAmount(int amount) {
        this.particleAmount = amount;
    }

}