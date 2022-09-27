package io.github.gafarrell.koth.resources;

import org.bukkit.entity.Player;

import java.util.Comparator;

public class Contestant implements Comparable<Contestant> {
    private Player p;
    private float time = 0;

    public Contestant(Player p, float time){
        this.p = p;
        this.time = time;
    }

    public void addTime(float deltaTime){
        this.time += deltaTime;
    }

    @Override
    public int compareTo(Contestant other) {
        return Float.compare(time, other.time);
    }
}
