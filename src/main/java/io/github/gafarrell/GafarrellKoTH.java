package io.github.gafarrell;

import io.github.gafarrell.commands.KothCommandHandler;
import io.github.gafarrell.events.MoveHandler;
import io.github.gafarrell.koth.KothStorage;
import io.github.gafarrell.koth.KothTimer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;

public class GafarrellKoTH extends JavaPlugin {
    private static File kothFile;
    private static FileConfiguration kothConfig;
    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getLogger().info("Enabling GafarrellKoTH");

        getServer().getPluginCommand("koth").setExecutor(new KothCommandHandler());
        getServer().getPluginManager().registerEvents(new MoveHandler(), this);

        loadCustomConfigs();

        KothStorage.loadKoths();

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, KothTimer.GetInstance(getServer()), 10, 10);

    }

    @Override
    public void onDisable() {
        saveConfig();
    }

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

    public static FileConfiguration getKothConfig() {
        if (kothConfig == null) plugin.reloadConfig();
        return kothConfig;
    }
}
