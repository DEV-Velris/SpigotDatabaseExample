package fr.velris.myoxyss;

import fr.velris.myoxyss.manager.MDatabase;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Myoxyss extends JavaPlugin {

    private static Myoxyss instance;

    // Manager
    private MDatabase database;

    @Override
    public void onEnable() {
        instance = this;

        // Manager
        database = new MDatabase();
        database.initDatabase();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);
        getCommand("test").setExecutor(new TestCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Myoxyss getInstance() {
        return instance;
    }

    public MDatabase getDatabase() {
        return database;
    }
}
