package io.github.gafarrell.koth;

import io.github.gafarrell.koth.resources.Region;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KothObject {
    private String name;
    private Region captureRegion = null;
    private long timeToCapture = 5000;
    private long activeDuration = 25000;
    private ItemStack[] rewards = null;
    private String nextKoth = null;

    public KothObject(String name)
    {
        this.name = name;
    }

    public KothObject(String name, Region captureRegions, long timeToCapture, long activeDuration)
    {
        this(name);
        this.captureRegion = captureRegion;
        this.timeToCapture = timeToCapture;
        this.activeDuration = activeDuration;
    }

    public KothObject(String name, Region captureRegion, long timeToCapture, long activeDuration,  ItemStack[] rewards)
    {
        this(name, captureRegion, timeToCapture, activeDuration);
        this.rewards = rewards.clone();
    }

    public KothObject(String name, Region captureRegion, long timeToCapture, long activeDuration,  ItemStack[] rewards, KothObject nextKoth)
    {
        this(name, captureRegion, timeToCapture,activeDuration,rewards);
        this.nextKoth = nextKoth.name;
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("============================\n")
                .append("Name: ").append(name).append("\n")
                .append("Capture Time: ").append(timeToCapture/1000).append(" sec\n")
                .append("Active Duration: ").append(activeDuration/1000).append(" sec\n");

        if (nextKoth != null) info.append("Next KoTH: ").append(nextKoth).append("\n");

        if (rewards != null) {
            info.append("Rewards:\n");
            for (ItemStack item : rewards) {
                if (item.hasItemMeta())
                    info.append("    ").append(item.getItemMeta().getDisplayName());
                else
                    info.append("    ").append(item.getType().toString());
                info.append(" x").append(item.getAmount()).append('\n');
            }
        }

        info.append(captureRegion);

        return info.append("============================\n").toString();
    }

    // Info
    public boolean hasNextKoth(){return nextKoth != null;}
    public boolean hasRewards(){return (rewards != null && rewards.length != 0); }

    /*
        Setters
     */
    public void setName(String name) {this.name = name; KothStorage.saveKoths();}
    public void setActiveDuration(long activeDuration) {this.activeDuration = activeDuration; KothStorage.saveKoths();}
    public void setTimeToCapture(long timeToCapture) {this.timeToCapture = timeToCapture; KothStorage.saveKoths();}
    public void setRewards(ItemStack[] rewards) {this.rewards = rewards; KothStorage.saveKoths();}
    public void setCaptureRegion(Region newRegion){this.captureRegion = newRegion; KothStorage.saveKoths();}

    public void setNextKoth(String nextKoth) {
        this.nextKoth = nextKoth;
        KothStorage.saveKoths();
    }

    /*
            Getters
         */
    public Region getCaptureRegion() {return captureRegion;}
    public long getActiveDuration() {
        return activeDuration;
    }
    public long getTimeToCapture() {
        return timeToCapture;
    }
    public ItemStack[] getRewards() {
        return rewards;
    }
    public String getNextKoth() {
        return nextKoth;
    }
    public String getName() {
        return name;
    }
}
