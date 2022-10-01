package io.github.gafarrell.koth;

import io.github.gafarrell.GafarrellKoTH;
import io.github.gafarrell.koth.resources.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static io.github.gafarrell.GafarrellKoTH.saveKoths;

public class KothStorage {
    private final static HashMap<String, KothObject> currentKoths = new HashMap<>();
    private final static ArrayList<KothController> currentControllers = new ArrayList<>();

    public static boolean put(KothObject koth){
        boolean added = (currentKoths.putIfAbsent(koth.getName(), koth) == null);
        saveKoths();
        return added;
    }

    public static boolean remove(KothObject object){
        if (isKothActive(object)) return false;
        boolean removed = currentKoths.remove(object.getName()) != null;
        if (removed) GafarrellKoTH.getKothConfig().set(object.getName(), null);
        saveKoths();
        return removed;
    }

    public static boolean remove(String name){
        if (isKothActive(name)) return false;
        boolean removed = currentKoths.remove(name) != null;
        if (removed) GafarrellKoTH.getKothConfig().set(name, null);
        saveKoths();
        return removed;
    }

    public static String getKothNames(){
        if (currentKoths.isEmpty()) return "No KoTHs to Display";

        StringBuilder builder = new StringBuilder();

        for (String s : currentKoths.keySet()){
            builder.append(s);
            builder.append(", ");
        }

        builder.deleteCharAt(builder.length()-2).trimToSize();
        return builder.toString();
    }

    public static String getActiveKothList(){
        StringBuilder builder = new StringBuilder();
        for (KothController k : currentControllers){
            builder.append("Koth {ID: ").append(k.getID())
                    .append(", Name: ").append(k.getName())
                    .append(", Time remaining: ").append(k.getRemainingDuration()/1000).append(" seconds")
                    .append("}\n");
        }
        return builder.toString();
    }

    public static boolean isKothActive(KothObject obj){return (currentControllers.stream().anyMatch(kothController -> kothController.isCurrentlyUsing(obj)));}
    public static boolean isKothActive(String name){return (currentControllers.stream().anyMatch(kothController -> kothController.isCurrentlyUsing(name)));}

    public static KothObject getKothByName(String name){
        return currentKoths.get(name);
    }

    public static void removeController(KothController kothcontroller){currentControllers.remove(kothcontroller);}

    public static KothController makeController(String name){
        KothObject koth = currentKoths.get(name);
        if (koth == null) return null;

        else {
            KothController controller = new KothController(koth, currentControllers.size()+1);
            currentControllers.add(controller);
            return controller;
        }
    }

    public static void pauseKoth(String name){
        KothController controllerToPause = currentControllers.stream().filter(controller -> controller.isCurrentlyUsing(name)).findFirst().orElse(null);

        if (controllerToPause == null) return;

        controllerToPause.pause();
    }

    public static void gameLoop(float deltaTime){
        ArrayList<KothController> flagged = new ArrayList<>();

        currentControllers.forEach(kothController -> {
            kothController.gameLoop(deltaTime);

            if (!kothController.isActive()){
                kothController.concludeKoth();
                flagged.add(kothController);
            }
        });

        currentControllers.removeAll(flagged);
    }

    public static HashMap<String, KothObject> getCurrentKoths() {
        return currentKoths;
    }

    public static ArrayList<KothController> getCurrentControllers() {
        return currentControllers;
    }

    public static int GetKothCount(){return currentKoths.size();}
}
