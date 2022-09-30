package io.github.gafarrell.koth;

import io.github.gafarrell.GafarrellKoTH;
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
        StringBuilder info = new StringBuilder("§b============================\n")
                .append("  §aName: ").append(name).append("\n")
                .append("  §aCapture Time: ").append(timeToCapture/1000).append(" sec\n")
                .append("  §aActive Duration: ").append(activeDuration/1000).append(" sec\n");

        if (nextKoth != null) info.append("  §aNext KoTH: ").append(nextKoth).append("\n");

        if (rewards != null) {
            info.append("§a  Rewards:\n");
            for (ItemStack item : rewards) {
                if (item.hasItemMeta())
                    info.append("    §a").append(item.getItemMeta().getDisplayName());
                else
                    info.append("    §a").append(item.getType().toString());
                info.append(" x").append(item.getAmount()).append('\n');
            }
        }

        info.append(captureRegion);

        return info.append("§b============================").toString();
    }


    @Override
    public boolean equals(Object other){
        if (!(other instanceof KothObject)) return false;

        return this.name.equalsIgnoreCase(((KothObject) other).name);
    }

    // Info
    public boolean hasNextKoth(){return nextKoth != null;}
    public boolean hasRewards(){return (rewards != null && rewards.length != 0); }

    /*
        Setters
     */
    public void setName(String name) {this.name = name; GafarrellKoTH.saveKoths();}
    public void setActiveDuration(long activeDuration) {this.activeDuration = activeDuration; GafarrellKoTH.saveKoths();}
    public void setTimeToCapture(long timeToCapture) {this.timeToCapture = timeToCapture; GafarrellKoTH.saveKoths();}
    public void setRewards(ItemStack[] rewards) {this.rewards = rewards; GafarrellKoTH.saveKoths();}
    public void setCaptureRegion(Region newRegion){this.captureRegion = newRegion; GafarrellKoTH.saveKoths();}

    public void setNextKoth(String nextKoth) {
        this.nextKoth = nextKoth;
        GafarrellKoTH.saveKoths();
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
