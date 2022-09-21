package me.only.lifeleech;

import me.only.lifeleech.itemmodule.ItemHandler;
import me.only.lifeleech.itemmodule.ItemPrefab;
import me.only.lifeleech.itemmodule.items.Bandage;
import me.only.lifeleech.itemmodule.items.HeartContainer;
import me.only.lifeleech.mainmodule.DeathHealthListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    ConfigHelper mainConfig;
    private static Main instance;

    public static NamespacedKey itemIdentity;
    public static Map<String, ItemPrefab> itemMap;

    @Override
    public void onEnable() {

        instance = this;

        setupConfigs();

        itemIdentity = new NamespacedKey(this, "itemIdentity");
        itemIdentity = new NamespacedKey(this, "craftingIdentity");

        itemMap = new HashMap<>();

        Bandage.init(); //Init of Bandage Hashmap
        registerItems(  // Registers Items from Itemmodule and their recipes
                new HeartContainer(),
                new Bandage()
        );

        registerListeners( // Registers all listeners
                new ItemHandler(),
                new DeathHealthListener()
        );

    }

    private void setupConfigs() {
        mainConfig = new ConfigHelper("config.yml", this);
        mainConfig.saveDefaultConfig();
    }
    private void loadConfig() {
        //Configs
        FileConfiguration config = mainConfig.getConfig();

        //Listener Options
        DeathHealthListener.setHealthLoss(config.getInt("Life.lossOnDeath", 2));
        DeathHealthListener.setMinHealth(config.getInt("Life.minHealth", 2));

        //Item Options
        HeartContainer.setHealthCap(config.getInt("Life.healthCap", 40));
        HeartContainer.setHeartHealthValue(config.getInt("Life.heartHealthValue", 2));

        Bandage.setBandageCooldown(config.getInt("Life.bandageCooldown", 20));
        Bandage.setBandageDuration(config.getInt("Life.bandageDuration", 100));
        Bandage.setBandageRegeneration(config.getInt("Life.bandageRegeneration", 1));
    }


    private void registerItems(ItemPrefab... customItems) {
        Arrays.asList(customItems).forEach(ci-> {
            itemMap.put(ci.getId(), ci);
            ci.registerRecipe();
        });
    }

    private void registerListeners(Listener... listeners) {
        Arrays.asList(listeners).forEach(l-> Bukkit.getPluginManager().registerEvents(l, this));
    }

    public static Main getInstance() {
        return instance;
    }
}
/*
    TODO: life bounties
    TODO: various other stuff idk

    TODO: Items buff items that interact with HP


    TODO: Mob changes
    TODO: Elder Guardian: Significantly reduced damage if not damaged by trident
 */
