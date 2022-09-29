package io.github.gafarrell;

import io.github.gafarrell.commands.KothCommandHandler;
import io.github.gafarrell.database.SQLiteConnector;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class GafarrellKoTH extends JavaPlugin {
    private SQLiteConnector SQLite;

    @Override
    public void onEnable() {
        getServer().getLogger().info("Enabling GafarrellKoTH");
        getServer().getPluginCommand("koth").setExecutor(new KothCommandHandler());

        try {
            initSQLLite();
        } catch (IOException e) {
            getLogger().info("Exception occurred while initializing SQLite.");
            getLogger().info(e.getMessage());
        }
    }

    @Override
    public void onDisable() {

    }

    private void initSQLLite() throws IOException {
        saveDefaultConfig();

        String path;

        if (getConfig().isString("sqlite.path.koth"))
        {
            path = getConfig().getString("sqlite.path");
        }
        else
        {
            path = getDataFolder() + "koth.db";
            getConfig().set("sqlite.path", getDataFolder().getPath() + "koth.db");
        }

        File file = new File(path);

        if (file.createNewFile()){
            getLogger().info("Database file not found! Creating one now...");
        }
        else
        {
            getLogger().info("Database file found! Loading...");
        }

        SQLite = new SQLiteConnector(path, getLogger());
    }
}
