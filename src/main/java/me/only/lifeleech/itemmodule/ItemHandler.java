package me.only.lifeleech.itemmodule;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import me.only.lifeleech.Main;

public class ItemHandler implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR) && isCustomItem(heldItem)) {

            ItemPrefab customItem = Main.itemMap.get(getItemId(heldItem));
            customItem.handleRightClick(player, heldItem, e);
        }

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_AIR) && isCustomItem(heldItem)) {

            ItemPrefab customItem = Main.itemMap.get(getItemId(heldItem));
            customItem.handleLeftClick(player, heldItem, e);
        }
    }

    private boolean isCustomItem(ItemStack itemStack) {
        return (itemStack.hasItemMeta() && itemStack.getItemMeta().getPersistentDataContainer().has(Main.itemIdentity, PersistentDataType.STRING));
    }

    private String getItemId(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(Main.itemIdentity, PersistentDataType.STRING);
    }

}
