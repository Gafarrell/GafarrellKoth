package io.github.gafarrell.koth.resources;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Region {
    // Members
    private int SideLength;
    private Vector worldPoint;
    private World world;

    // Constructor
    public Region(Location loc, int r){
        SideLength = r;
        worldPoint = new Vector(loc.getX(), loc.getY(), loc.getZ());
        world = loc.getWorld();
    }

    // Checks if player is within region.
    public boolean IsPlayerInRegion(Location playerLocation)
    {
        if (playerLocation.getWorld() != world) return false;
        Vector vec = new Vector(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()).subtract(worldPoint);

        return (Math.abs(vec.getX()) < SideLength/2f && Math.abs(vec.getY()) < SideLength/2f);
    }

    // Getters
    public int getSideLength(){return SideLength;}
    public Vector getLocation(){return worldPoint;}

    // Setters
    public void setSideLength(int sideLength) { SideLength = sideLength; }
    public void setWorld(World world) { this.world = world; }
    public void setWorldPoint(Vector worldPoint) { this.worldPoint = worldPoint; }

    public void setLocation(Location loc){
        worldPoint = new Vector(loc.getX(), loc.getY(), loc.getZ());
        world = loc.getWorld();
    }
}
