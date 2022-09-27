package io.github.gafarrell.koth;

import io.github.gafarrell.koth.resources.Region;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

class KothObject {
    private String name;
    private final ArrayList<Region> captureRegions = new ArrayList<>();
    private float timeToCapture, activeDuration;
    private ItemStack[] rewards;
    private KothObject nextKoth;

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

    public boolean hasNextKoth(){return nextKoth != null;}
    public boolean hasRewards(){return (rewards != null || rewards.length != 0); }


    public ArrayList<Region> getCaptureRegions() {
        return captureRegions;
    }

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
