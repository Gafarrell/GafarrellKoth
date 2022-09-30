package io.github.gafarrell;

import io.github.gafarrell.commands.KothCommandCompletion;
import io.github.gafarrell.commands.KothCommandHandler;
import io.github.gafarrell.events.MoveHandler;
import io.github.gafarrell.koth.KothObject;
import io.github.gafarrell.koth.KothStorage;
import io.github.gafarrell.koth.KothTimer;
import io.github.gafarrell.koth.resources.Region;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GafarrellKoTH extends JavaPlugin {
    // Static members
    private static File kothFile;
    private static FileConfiguration kothConfig;
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getLogger().info("Enabling GafarrellKoTH");

        getServer().getPluginCommand("koth").setExecutor(new KothCommandHandler());
        getServer().getPluginCommand("koth").setTabCompleter(new KothCommandCompletion());
        getServer().getPluginManager().registerEvents(new MoveHandler(), this);

        loadCustomConfigs();
        loadKoths();

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, KothTimer.GetInstance(), 10, 10);

    }


    @Override
    public void onDisable() {
        saveConfig();
    }

    /**
     *  Loads custom config files or creates them if they are not found.
     */
    private static void loadCustomConfigs() {
        try {
            kothFile = new File(plugin.getDataFolder(), "koths.yml");

            if (!kothFile.exists()){
                kothFile.getParentFile().mkdirs();
                plugin.saveResource("koths.yml", false);
            }

            kothConfig = new YamlConfiguration();

            kothConfig.load(kothFile);

        } catch (IOException | InvalidConfigurationException e) {
            plugin.getLogger().warning(e.getMessage());
        }
    }

    public static File getKothFile() {
        return kothFile;
    }

    /**
     * Gets the KoTH config file.
     * @return KoTH config file
     */
    public static FileConfiguration getKothConfig() {
        if (kothConfig == null) plugin.reloadConfig();
        return kothConfig;
    }

    /**
     *  Saves koths currently stored. Does not save active KoTHs
     */
    public static void saveKoths()
    {
        FileConfiguration saveFile = getKothConfig();

        for (KothObject object : KothStorage.getCurrentKoths().values())
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
            saveFile.save(getKothFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Loads KoTH Objects from Koths.yml
     */
    public static void loadKoths() {
        FileConfiguration saveFile = getKothConfig();
        Set<String> koths = saveFile.getKeys(false);

        if (koths == null || koths.size() == 0) Bukkit.getServer().getLogger().info("No previous koths found.");
        else Bukkit.getServer().getLogger().info("Found saved KoTHs! loading now.");

        assert koths != null;
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

            newKoth.setRewards(rewards.toArray(new ItemStack[0]));
            newKoth.setNextKoth(saveFile.getString("next-koth"));

            KothStorage.getCurrentKoths().put(newKoth.getName(), newKoth);
        }
    }
}
