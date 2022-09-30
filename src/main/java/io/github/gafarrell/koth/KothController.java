package io.github.gafarrell.koth;

import io.github.gafarrell.events.KothCapEvent;
import io.github.gafarrell.koth.resources.Contestant;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class KothController {
    private KothObject koth;
    private int ID;
    private float remainingDuration;
    private boolean isActive = false;

    private final ArrayList<Contestant> activeCaptors = new ArrayList<>();
    private final List<Contestant> participants = new ArrayList<>();

    public KothController(KothObject koth, int ID)
    {
        this.koth = koth;
        this.ID = ID;
        remainingDuration = koth.getActiveDuration();
    }

    public void gameLoop(float DeltaTime)
    {
        remainingDuration -= DeltaTime;

        activeCaptors.forEach(contestant -> {
            contestant.gameLoop(DeltaTime);
        });

        Collections.sort(participants);

        if (remainingDuration <= 0 || (participants.size() > 0 && participants.get(0).getTime() >= koth.getTimeToCapture())){
            isActive = false;
        }
    }

    public void updateScoreboard()
    {
        // TODO: Implement scoreboard
    }

    public void concludeKoth()
    {
        if (participants.size() == 0) return;


        KothCapEvent winnerEvent = new KothCapEvent(koth.getName(), participants.get(0).getPlayer());
        Bukkit.getServer().getPluginManager().callEvent(winnerEvent);
        if (winnerEvent.isCancelled()) return;

        Player winner = participants.get(0).getPlayer();

        winner.sendMessage("Congratulations! You've won King of the Hill!");

        if (koth.hasRewards()){
            winner.getInventory().addItem(koth.getRewards());
        }

        if (koth.hasNextKoth()){
            KothController controller = KothStorage.makeController(koth.getNextKoth());

            assert controller != null;
            controller.start();
        }
    }

    public boolean isPlayerWithinKoth(Location playerLocation) throws Exception {
        return (koth.getCaptureRegion().IsPlayerInRegion(playerLocation));
    }

    public void removeActiveCaptor(Player p){
        if (activeCaptors.removeIf(contestant -> contestant.getPlayer() == p)){
            p.sendMessage("You have been removed from the active captors list.");
        }
    }

    public void addActiveCaptor(Player p) {
        if (activeCaptors.stream().anyMatch(contestant -> contestant.getPlayer() == p)) return;

        Contestant cont = participants.stream().filter(contestant -> contestant.getPlayer() == p).findFirst().orElse(null);

        if (cont != null){
            activeCaptors.add(cont);
            p.sendMessage("You have been re-added to the active captors list.");
        }
        else {
            Contestant contestant = new Contestant(p);
            p.sendMessage("You have been added to the active captors list.");
            activeCaptors.add(contestant);
            participants.add(contestant);
        }
    }

    public int getID() {
        return ID;
    }

    public String getName(){
        return koth.getName();
    }

    public void start(){ isActive = true; }
    public void pause(){ isActive = false; }

    public float getRemainingDuration() {
        return remainingDuration;
    }

    public boolean isActive() {
        return isActive;
    }
}
