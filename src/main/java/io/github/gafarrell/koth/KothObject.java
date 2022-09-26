package io.github.gafarrell.koth;

import io.github.gafarrell.koth.coordinates.Region;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

class KothObject {
    private String Name;
    private final ArrayList<Region> CaptureRegions = new ArrayList<>();
    private float TimeToCapture, ActiveDuration;
    private ItemStack[] Rewards;
    private KothObject NextKoth;

    public KothObject(String name)
    {
        Name = name;
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration)
    {
        this(name);
        CaptureRegions.addAll(captureRegions);
        TimeToCapture = timeToCapture;
        ActiveDuration = activeDuration;
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration,  ItemStack[] rewards)
    {
        this(name, captureRegions, timeToCapture, activeDuration);
        Rewards = rewards.clone();
    }

    public KothObject(String name, ArrayList<Region> captureRegions, float timeToCapture, float activeDuration,  ItemStack[] rewards, KothObject nextKoth)
    {
        this(name, captureRegions, timeToCapture,activeDuration,rewards);
        NextKoth = nextKoth;
    }

    public ArrayList<Region> getCaptureRegions() {
        return CaptureRegions;
    }

    public float getActiveDuration() {
        return ActiveDuration;
    }

    public float getTimeToCapture() {
        return TimeToCapture;
    }

    public ItemStack[] getRewards() {
        return Rewards;
    }

    public KothObject getNextKoth() {
        return NextKoth;
    }

    public String getName() {
        return Name;
    }
}
