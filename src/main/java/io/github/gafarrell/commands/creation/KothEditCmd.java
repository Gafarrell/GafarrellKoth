package io.github.gafarrell.commands.creation;

import io.github.gafarrell.commands.KothCmd;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class KothEditCmd extends KothCmd {

    public KothEditCmd(CommandSender commandSender, String[] args) {
        super(commandSender, args);
    }

    @Override
    public boolean Execute() {
        if (args.length < 3 || (args.length-1)%2 != 0){
            errorMessage = "Not enough arguments to make an edit!";
        }

        KothObject kothToEdit = KothStorage.getKothByName(args[0]);

        if (kothToEdit == null) {
            errorMessage = "Koth " + args[0] + " does not exist.";
            return (successful = false);
        }

        for (int i = 1; i < args.length; i+=2){
            switch (args[i].toLowerCase()) {
                case "radius", "sidelength" -> {
                    int newRadius = Integer.parseInt(args[i + 1]);
                    kothToEdit.getCaptureRegion().setSideLength(newRadius);
                }

                case "region" -> {
                    if (!(commandSender instanceof Player)) return false;

                    Player p = (Player) commandSender;
                    String[] coords = args[i + 1].split(",");

                    if (coords.length != 3) return false;
                    Location newLoc = p.getLocation();

                    newLoc.setX(Double.parseDouble(coords[0]));
                    newLoc.setY(Double.parseDouble(coords[1]));
                    newLoc.setZ(Double.parseDouble(coords[2]));

                    kothToEdit.getCaptureRegion().setLocation(newLoc);
                }

                case "duration" ->{
                    kothToEdit.setActiveDuration(Long.parseLong(args[i+1])*1000);
                }

                case "capturetime" -> {
                    kothToEdit.setTimeToCapture(Long.parseLong(args[i+1])*1000);
                }
            }
        }

        return true;
    }
}
