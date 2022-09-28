package io.github.gafarrell;

import io.github.gafarrell.commands.KothCommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class GafarrellKoTH extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getLogger().info("Enabling GafarrellKoTH");
        getServer().getPluginCommand("koth").setExecutor(new KothCommandHandler());
    }

    @Override
    public void onDisable() {

    }
}
