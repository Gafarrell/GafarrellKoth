package io.github.gafarrell.koth;

import io.github.gafarrell.koth.resources.Region;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KothObject {
    private String name;
    private final ArrayList<Region> captureRegions = new ArrayList<>();
    private float timeToCapture = 5000, activeDuration = 25000;
    private ItemStack[] rewards = null;
    private KothObject nextKoth = null;

    public KothObject(String name)
    {
        this.name = name;
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration)
    {
        this(name);
        this.captureRegions.addAll(captureRegions);
        this.timeToCapture = timeToCapture;
        this.activeDuration = activeDuration;
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration,  ItemStack[] rewards)
    {
        this(name, captureRegions, timeToCapture, activeDuration);
        this.rewards = rewards.clone();
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration,  ItemStack[] rewards, KothObject nextKoth)
    {
        this(name, captureRegions, timeToCapture,activeDuration,rewards);
        this.nextKoth = nextKoth;
    }

    @Override
    public String toString(){
        String info = "============================\n";
        info += "Name: " + name + "\n";
        info += "Capture Time: " + timeToCapture + "\n";
        info += "Active Duration: " + activeDuration + "\n";
        if (nextKoth != null) info += "Next KoTH: " + nextKoth.name + "\n";
        info += "Region Count: " + captureRegions.size() + "\n";
        for (Region r : captureRegions){
            info += r;
        }
        info += "============================\n";
        return info;
    }

    // Info
    public boolean hasNextKoth(){return nextKoth != null;}
    public boolean hasRewards(){return (rewards != null || rewards.length != 0); }

    /*
        Setters
     */
    public void setName(String name) {this.name = name;}
    public void setActiveDuration(float activeDuration) {this.activeDuration = activeDuration;}
    public void setTimeToCapture(float timeToCapture) {this.timeToCapture = timeToCapture;}
    public void setRewards(ItemStack[] rewards) {this.rewards = rewards;}
    public void addCaptureRegion(Region region){captureRegions.add(region);}
    public void removeCaptureRegion(Region region){captureRegions.remove(region);}
    public void removeCaptureRegion(int regionID){captureRegions.remove(regionID);}

    /*
        Getters
     */
    public ArrayList<Region> getCaptureRegions() {return captureRegions;}
    public float getActiveDuration() {
        return activeDuration;
    }
    public float getTimeToCapture() {
        return timeToCapture;
    }
    public ItemStack[] getRewards() {
        return rewards;
    }
    public KothObject getNextKoth() {
        return nextKoth;
    }
    public String getName() {
        return name;
    }
}
