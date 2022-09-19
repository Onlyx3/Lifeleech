package me.only.lifeleech.mainmodule;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import me.only.lifeleech.Main;

public class DeathHealthListener implements Listener {

    private static int healthLoss;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        double phealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        e.setKeepInventory(false);

        phealth -= healthLoss;

        if(phealth <= 0) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(p.getName(), "You ran out of life", null, null);
            p.kickPlayer("You ran out of life");
            return;
        };

        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(phealth);
        e.getDrops().add(Main.itemMap.get("HeartContainer").constructItem(1));
    }

    public static void setHealthLoss(int healthLoss) {
        DeathHealthListener.healthLoss = healthLoss;
    }
}
