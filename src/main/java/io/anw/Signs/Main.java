package io.anw.Signs;

import io.anw.AuroraUtils.Bukkit.AuroraPlugin;
import io.anw.Signs.Commands.Commands;
import io.anw.Signs.Listeners.SignListeners;
import io.anw.Signs.Utils.SQL.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Main extends AuroraPlugin {

    private static Main instance;
    /**
     * Retrieves a static instance of this class
     *
     * @return static instance of Main class
     */
    public static Main getInstance() { return instance; }

    public File configFile;
    public FileConfiguration Config;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        configFile = new File(getDataFolder(), "config.yml");
        getConfigManager().addFile(configFile);
        try {
            getConfigManager().firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Config = getConfigManager().getConfigFile("config.yml");
        getConfigManager().load();
        getConfigManager().save();

        addListener(new SignListeners());
        registerListeners();

        addCommandClass(Commands.class);
        registerCommands();

        DatabaseManager.getInstance().checkDatabase();

        SignRunnable.init();
    }
}
