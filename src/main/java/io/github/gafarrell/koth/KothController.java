package io.github.gafarrell.koth;

import io.github.gafarrell.koth.resources.Contestant;
import io.github.gafarrell.koth.resources.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class KothController {
    private KothObject koth;
    private float remainingDuration;
    private boolean isActive = false;

    private final ArrayList<Player> activeCaptors = new ArrayList<>();
    private final List<Contestant> participants = new ArrayList<Contestant>(){
        @Override
        public boolean add(Contestant c){
            participants.add(c);
            Collections.sort(participants);
            return true;
        }
    };

    public KothController(KothObject koth)
    {
        this.koth = koth;
        remainingDuration = koth.getActiveDuration();
    }

    public void adjustDuration(float DeltaTime)
    {
        remainingDuration -= DeltaTime;
        participants.forEach(contestant -> {

        });
        if (remainingDuration <= 0){
            isActive = false;
        }
    }

    public void concludeKoth()
    {
        if (koth.hasRewards()){

            // TODO: Distribute rewards
        }

        if (koth.hasNextKoth()){
            // TODO: Start next KoTH region.
        }
    }

    public boolean isPlayerWithinKoth(Location playerLocation)
    {
        for (Region r : koth.getCaptureRegions())
        {
            if (r.IsPlayerInRegion(playerLocation)) return true;
        }
        return false;
    }

    public void removeActiveCaptor(Player p){
        activeCaptors.remove(p);
    }

    public void addActiveCaptor(Player p) {
        if (activeCaptors.contains(p)) return;

        activeCaptors.add(p);
    }

    public void start(){ isActive = true; }
    public void Pause(){ isActive = false; }


    public boolean isActive() {
        return isActive;
    }
}
