package io.github.gafarrell.koth.resources;

import org.bukkit.entity.Player;

public class Contestant implements Comparable<Contestant> {
    private Player p;
    private long time = 0;

    public Contestant(Player p){
        this.p = p;
    }

    public void gameLoop(float deltaTime){
        this.time += deltaTime;
    }

    public void sendMessage(String message){
        p.sendMessage(message);
    }

    public long getTime() {
        return time;
    }

    public Player getPlayer() {
        return p;
    }


    @Override
    public int compareTo(Contestant other) {
        return Long.compare(other.time, time);
    }
}
