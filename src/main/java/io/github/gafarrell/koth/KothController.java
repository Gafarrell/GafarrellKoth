package io.github.gafarrell.koth;

import io.github.gafarrell.koth.coordinates.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class KothController {
    private KothObject koth;
    private float RemainingDuration;
    private HashMap<Player, Float> Participants = new HashMap<>();
    private ArrayList<Player> activeCaptors = new ArrayList<>();
    private boolean IsActive = false;

    public KothController(KothObject koth)
    {
        this.koth = koth;
        RemainingDuration = koth.getActiveDuration();
    }

    public void AdjustDuration(float DeltaTime)
    {
        RemainingDuration -= DeltaTime;
        Participants.keySet().forEach(player -> {
            Participants.put(player, Participants.get(player)+DeltaTime);
        });
        if (RemainingDuration <= 0){
            IsActive = false;
        }
    }

    public void ConcludeKoth()
    {

    }

    public boolean IsPlayerWithinKoth(Location playerLocation)
    {
        for (Region r : koth.getCaptureRegions())
        {
            if (r.IsPlayerInRegion(playerLocation)) return true;
        }
        return false;
    }

    public void RemoveActiveCaptor(Player p){
        activeCaptors.remove(p);
    }

    public void AddActiveCaptor(Player p) {
        if (activeCaptors.contains(p)) return;

        activeCaptors.add(p);
        Participants.putIfAbsent(p, 0f);
    }

    public void Start(){IsActive = true;}
    public void Pause(){IsActive = false;}


    public boolean isActive() {
        return IsActive;
    }
}
