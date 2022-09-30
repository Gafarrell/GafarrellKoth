package io.github.gafarrell.events;

import io.github.gafarrell.koth.KothController;
import io.github.gafarrell.koth.KothStorage;
import io.github.gafarrell.koth.KothTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class MoveHandler implements Listener {

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e)
    {
        ArrayList<KothController> activeKoths = new ArrayList<>(KothStorage.getCurrentControllers().stream().filter(KothController::isActive).toList());
        activeKoths.forEach(kothController -> {
            try {
                if (kothController.isPlayerWithinKoth(e.getTo())){
                    kothController.addActiveCaptor(e.getPlayer());
                }
                else {
                    kothController.removeActiveCaptor(e.getPlayer());
                }
            } catch (Exception ex) {
                e.getPlayer().sendMessage(ex.getMessage());
            }
        });
    }
}
