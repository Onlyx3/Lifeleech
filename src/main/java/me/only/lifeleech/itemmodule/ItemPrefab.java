package me.only.lifeleech.itemmodule;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.only.lifeleech.Main;
import me.only.lifeleech.util.Common;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemPrefab {

    public abstract String getDisplayName();

    public abstract Material getItemMaterial();

    public abstract List<String> getItemLore();

    public abstract void handleLeftClick(Player p, ItemStack itemStack, PlayerInteractEvent event);

    public abstract void handleRightClick(Player p, ItemStack itemStack, PlayerInteractEvent event);

    public ItemStack constructItem(int amount) {
        ItemStack itemStack = new ItemStack(getItemMaterial(), amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();

        itemMeta.setDisplayName(Common.colorize(getDisplayName()));
        List<String> lore = new ArrayList<>();
        getItemLore().forEach(l-> lore.add(Common.colorize(l)));
        itemMeta.setLore(lore);

        container.set(Main.itemIdentity, PersistentDataType.STRING, getId());
        itemStack.setItemMeta(itemMeta);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public String getId() {
        return getClass().getSimpleName();
    }

    public abstract void registerRecipe();

}
