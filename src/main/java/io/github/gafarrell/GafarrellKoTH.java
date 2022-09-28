package io.github.gafarrell;

import io.github.gafarrell.commands.KothCommandHandler;
import io.github.gafarrell.database.MongoConnector;
import org.bukkit.plugin.java.JavaPlugin;

public class GafarrellKoTH extends JavaPlugin {
    private MongoConnector mongoConnector;

    @Override
    public void onEnable() {
        getServer().getLogger().info("Enabling GafarrellKoTH");
        getServer().getPluginCommand("koth").setExecutor(new KothCommandHandler());

        saveDefaultConfig();

        mongoConnector = new MongoConnector(getConfig().getString("mongo.uri"));
    }

    @Override
    public void onDisable() {

    }
}
