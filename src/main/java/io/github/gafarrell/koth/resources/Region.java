package io.github.gafarrell.koth.resources;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public class Region {
    // Members
    private int sideLength;
    private Vector worldPoint;
    private World world;

    // Constructor
    public Region(Location loc, int r){
        sideLength = r;
        worldPoint = new Vector(loc.getBlockX()+0.5, loc.getBlockY()+0.5, loc.getBlockZ()+0.5);
        world = loc.getWorld();
    }

    public Region(String storageString){
        String[] capRegion = storageString.split(",");
        if (capRegion.length != 5) return;

        world = Bukkit.getServer().getWorld(capRegion[0]);
        worldPoint = new Vector(Double.parseDouble(capRegion[1]), Double.parseDouble(capRegion[2]), Double.parseDouble(capRegion[3]));
        sideLength = Integer.parseInt(capRegion[4]);
    }

    // Checks if player is within region.
    public boolean IsPlayerInRegion(Location playerLocation) {
        if (playerLocation.getWorld() != world) return false;
        Vector vec = new Vector(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ()).subtract(worldPoint);
        return (Math.abs(vec.getX()) < sideLength/2f && Math.abs(vec.getZ()) < sideLength/2f);
    }

    @Override
    public String toString(){
        String info = "";
        info += String.format("§bLocation: (%f, %f, %f)\n", worldPoint.getX(), worldPoint.getY(), worldPoint.getZ());
        info += "§bWorld: " + world.getName() + "\n";
        info += String.format("§bSize: %dx%1$d\n", sideLength);
        return info;
    }

    public String toStorage(){
        StringBuilder builder = new StringBuilder();
        builder.append(world.getName()).append(',')
                .append(worldPoint.getX()).append(',')
                .append(worldPoint.getY()).append(',')
                .append(worldPoint.getZ()).append(',')
                .append(sideLength);

        return builder.toString();
    }

    public void displayRegion(){
        Location location = new Location(world, worldPoint.getX(), worldPoint.getY(), worldPoint.getZ());
        for (int x = (int) (worldPoint.getX()-(sideLength/2f)); x < (int) (worldPoint.getX()+(sideLength/2f)); x++){
            location.setX(x);
            location.setZ((worldPoint.getZ()-(sideLength/2f)));
            world.playEffect(location, Effect.HAPPY_VILLAGER, 5);
            location.setZ((worldPoint.getZ()+(sideLength/2f)));
            world.playEffect(location, Effect.HAPPY_VILLAGER, 5);
        }

        for (int z = (int) (worldPoint.getZ()-(sideLength/2f)); z < (int) (worldPoint.getZ()+(sideLength/2f)); z++){
            location.setZ(z);
            location.setX((worldPoint.getX()-(sideLength/2f)));
            world.playEffect(location, Effect.HAPPY_VILLAGER, 5);
            location.setX((worldPoint.getX()+(sideLength/2f)));
            world.playEffect(location, Effect.HAPPY_VILLAGER, 5);
        }
    }

    // Getters
    public int getSideLength(){return sideLength;}
    public Vector getLocation(){return worldPoint;}

    // Setters
    public void setSideLength(int sideLength) { this.sideLength = sideLength; }
    public void setWorld(World world) { this.world = world; }
    public void setWorldPoint(Vector worldPoint) { this.worldPoint = worldPoint; }

    public void setLocation(Location loc){
        worldPoint = new Vector(loc.getX(), loc.getY(), loc.getZ());
        world = loc.getWorld();
    }
}
