package io.wdsj.hybridfix_patch.residence;

import io.wdsj.hybridfix_patch.residence.config.Config;
import io.wdsj.hybridfix_patch.residence.listener.ResHookBlockFormListener;
import io.wdsj.hybridfix_patch.residence.listener.ResHookEntityChangeBlockListener;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class HybridFixResidence extends JavaPlugin {
    private final PluginManager pluginManager = getServer().getPluginManager();
    private static Logger logger;
    private static Config config;
    private static HybridFixResidence instance;
    private File dataFolder;

    @Override
    public void onLoad() {
        instance = this;
        logger = getLogger();
        dataFolder = getDataFolder();
        reloadConfiguration();
    }
    @Override
    public void onEnable() {
        pluginManager.registerEvents(new ResHookBlockFormListener(), this);
        pluginManager.registerEvents(new ResHookEntityChangeBlockListener(), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void reloadConfiguration() {
        try {
            createDirectory(dataFolder);
            config = new Config(this, dataFolder);
            config.saveConfig();
        } catch (Throwable t) {
            logger.severe("Failed to reload config " + t.getMessage());
        }
    }

    public static Config config() {
        return config;
    }

    public static Logger logger() {
        return logger;
    }

    public static HybridFixResidence getInstance() {
        return instance;
    }

    public void createDirectory(File dir) throws IOException {
        try {
            Files.createDirectories(dir.toPath());
        } catch (FileAlreadyExistsException e) { // Thrown if dir exists but is not a directory
            if (dir.delete()) createDirectory(dir);
        }
    }
}
