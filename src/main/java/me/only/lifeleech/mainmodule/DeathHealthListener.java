package me.only.lifeleech.mainmodule;

import me.only.lifeleech.util.Common;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import me.only.lifeleech.Main;

public class DeathHealthListener implements Listener {

    private static int healthLoss;
    private static int minHealth;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        double phealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if(phealth <= minHealth) return;

        phealth -= healthLoss;

        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Math.max(phealth,minHealth));
        e.getDrops().add(Main.itemMap.get("HeartContainer").constructItem(1));
        p.sendMessage(Common.colorize("&6Because you died, you lost " + healthLoss + " hp!"));
    }

    public static void setHealthLoss(int healthLoss) {
        DeathHealthListener.healthLoss = healthLoss;
    }

    public static void setMinHealth(int minHealth) {
        DeathHealthListener.minHealth = minHealth;
    }
}
