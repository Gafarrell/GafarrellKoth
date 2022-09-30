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

public class KothStorage {
    private final static HashMap<String, KothObject> currentKoths = new HashMap<>();
    private final static ArrayList<KothController> currentControllers = new ArrayList<>();

    public static boolean put(KothObject koth){
        boolean added = (currentKoths.putIfAbsent(koth.getName(), koth) == null);
        saveKoths();
        return added;
    }

    public static boolean remove(KothObject object){
        boolean removed = currentKoths.remove(object.getName()) != null;
        if (removed) GafarrellKoTH.getKothConfig().set(object.getName(), null);
        saveKoths();
        return removed;
    }

    public static boolean remove(String name){
        Bukkit.getServer().getLogger().info(currentKoths.keySet().toString());
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

    public static void saveKoths()
    {
        FileConfiguration saveFile = GafarrellKoTH.getKothConfig();

        for (KothObject object : currentKoths.values())
        {
            Bukkit.getServer().getLogger().info("Saving config file...");
            String path = object.getName() + ".";

            saveFile.set(path + "capture-region", object.getCaptureRegion().toStorage());
            saveFile.set(path + "capture-time", object.getTimeToCapture());
            saveFile.set(path + "active-duration", object.getActiveDuration());
            if (object.hasRewards()) saveFile.set(path + "rewards", object.getRewards());
            if (object.hasNextKoth()) saveFile.set(path + "next-koth", object.getNextKoth());
        }

        try {
            saveFile.save(GafarrellKoTH.getKothFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadKoths() {
        FileConfiguration saveFile = GafarrellKoTH.getKothConfig();
        Set<String> koths = saveFile.getKeys(false);

        if (koths == null || koths.size() == 0) Bukkit.getServer().getLogger().info("No previous koths found.");
        else Bukkit.getServer().getLogger().info("Found saved KoTHs! loading now.");

        for (String kothName : koths){
            String path = kothName + ".";
            Bukkit.getServer().getLogger().info(path);

            KothObject newKoth = new KothObject(kothName);

            newKoth.setCaptureRegion(new Region(saveFile.getString(path + "capture-region")));
            newKoth.setTimeToCapture(saveFile.getLong(path + "capture-time"));
            newKoth.setActiveDuration(saveFile.getLong(path + "active-duration"));

            List<ItemStack> rewards = new ArrayList<>();
            if (saveFile.isList(path + "rewards")){
                for (Object item : saveFile.getList(path + "rewards"))
                    if (item != null) rewards.add((ItemStack) item);
            }


            newKoth.setNextKoth(saveFile.getString("next-koth"));

            currentKoths.put(newKoth.getName(), newKoth);
        }
    }


    public static ArrayList<KothController> getCurrentControllers() {
        return currentControllers;
    }

    public static int GetKothCount(){return currentKoths.size();}
}
