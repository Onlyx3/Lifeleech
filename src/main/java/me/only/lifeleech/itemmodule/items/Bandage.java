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
import org.bukkit.potion.PotionEffectType;
import me.only.lifeleech.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Bandage extends ItemPrefab {

    private static Map<UUID, Long> cooldowns;

    public static void init() {
        cooldowns = new HashMap<>();
    }

    private static int bandageCooldown;
    private static int bandageDuration;
    private static int bandageRegeneration;

    public static void setBandageCooldown(int bandageCooldown) {
        Bandage.bandageCooldown = bandageCooldown;
    }

    public static void setBandageDuration(int bandageDuration) {
        Bandage.bandageDuration = bandageDuration;
    }

    public static void setBandageRegeneration(int bandageRegeneration) {
        Bandage.bandageRegeneration = bandageRegeneration;
    }

    @Override
    public String getDisplayName() {
        return "Bandage";
    }

    @Override
    public Material getItemMaterial() {
        return Material.PAPER;
    }

    @Override
    public List<String> getItemLore() {
        return List.of(Common.colorize("&7Grants a short burst of Regeneration."));
    }

    @Override
    public void handleLeftClick(Player p, ItemStack itemStack, PlayerInteractEvent event) {

    }

    @Override
    public void handleRightClick(Player p, ItemStack itemStack, PlayerInteractEvent event) {
        event.setCancelled(true);

        if(p.getHealth() == p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
            p.sendMessage(Common.colorize("&6You dont need to be healed right now."));
            return;
        }

        if((cooldowns.containsKey(p.getUniqueId()))) {
            long time = System.currentTimeMillis();
            if(time <= cooldowns.get(p.getUniqueId())) {

                p.sendMessage(Common.colorize("&6You cant bandage for another " +
                        Math.round((double) (cooldowns.get(p.getUniqueId()) - time) / 1000 ) +
                        "s."));
                return;
            } else {
                cooldowns.remove(p.getUniqueId());
            }
        }
        p.sendMessage(Common.colorize("&6You have used a bandage."));

        itemStack.setAmount(itemStack.getAmount() - 1);

        p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(bandageDuration,bandageRegeneration));
        p.playSound(p.getLocation(), Sound.ITEM_AXE_STRIP, 1f, 1.8f);
        cooldowns.put(p.getUniqueId(), System.currentTimeMillis() + bandageCooldown * 1000L);
    }

    @Override
    public void registerRecipe() {
        ItemStack item = constructItem(3);
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "recipebandage"), item);

        sr.shape("SWS", "SWS", "SWS");
        sr.setIngredient('S', Material.STRING);
        sr.setIngredient('W', Material.WHITE_WOOL);

        Bukkit.addRecipe(sr);
    }
}
