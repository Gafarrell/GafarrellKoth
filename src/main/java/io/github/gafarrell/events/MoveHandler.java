package io.github.gafarrell.events;

import io.github.gafarrell.koth.KothController;
import io.github.gafarrell.koth.KothTimer;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class MoveHandler implements Listener {

    private ArrayList<KothController> activeKoths;
    public MoveHandler()
    {
        activeKoths = KothTimer.GetInstance().GetActiveKoths();
    }

    public void OnPlayerMove(PlayerMoveEvent e)
    {
        activeKoths.forEach(kothController -> {
            if (kothController.isPlayerWithinKoth(e.getTo())){
                kothController.addActiveCaptor(e.getPlayer());
            }
        });
    }
}
