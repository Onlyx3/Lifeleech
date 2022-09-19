package me.only.lifeleech.itemmodule.items;

import me.only.lifeleech.itemmodule.ItemPrefab;
import me.only.lifeleech.util.Common;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import me.only.lifeleech.Main;

import java.util.List;

public class HeartContainer extends ItemPrefab {

    private static int healthCap;
    private static int heartHealthValue;

    public static void setHealthCap(int healthCap) {
        HeartContainer.healthCap = healthCap;
    }

    public static void setHeartHealthValue(int heartHealthValue) {
        HeartContainer.heartHealthValue = heartHealthValue;
    }

    @Override
    public String getDisplayName() {
        return Common.colorize("&4Heart Container");
    }

    @Override
    public Material getItemMaterial() {
        return Material.GHAST_TEAR;
    }

    @Override
    public List<String> getItemLore() {
        return List.of(Common.colorize("&7Right Click to gain Max Health."));
    }

    @Override
    public void handleLeftClick(Player p, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player p, ItemStack itemStack, PlayerInteractEvent event) {
        event.setCancelled(true);

        double health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        if(health >= healthCap) {
            p.sendMessage(Common.colorize("&aYou have reached the maximum amount of hearts!"));
            return;
        }

        itemStack.setAmount(itemStack.getAmount() - 1);

        health += heartHealthValue;
        if(health >= healthCap) health = healthCap;

        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        p.setHealth(p.getHealth() + heartHealthValue);
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }

    @Override
    public void registerRecipe() {
        ItemStack item = constructItem(1);
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "recipeheartcontainer"), item);

        sr.shape("DTD", "TST", "DTD");

        sr.setIngredient('D', Material.DIAMOND_BLOCK);
        sr.setIngredient('T', Material.TOTEM_OF_UNDYING);
        sr.setIngredient('S', Material.NETHER_STAR);

        Bukkit.addRecipe(sr);
    }

}
