package me.only.lifeleech.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {

    private int amount = 1;
    private final ItemMeta meta;
    private final Material m;

    private Items(Material m) {
        this.m = m;
        this.meta = Bukkit.getItemFactory().getItemMeta(m);
    }

    private Items(ItemStack is) {
        this.m = is.getType();
        this.amount = is.getAmount();

        if (is.hasItemMeta())
            this.meta = is.getItemMeta();
        else
            this.meta = Bukkit.getItemFactory().getItemMeta(m);
    }

    public static Items edit(ItemStack is) {
        return new Items(is);
    }

    public static Items builder(Material m) {
        return new Items(m);
    }

    public Items name(String name) {
        this.meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public Items amount(int amount) {
        this.amount = amount;
        return this;
    }

    public Items lore(String... lore) {
        List<String> itemLore;
        if (this.meta.hasLore())
            itemLore = this.meta.getLore();
        else
            itemLore = new ArrayList<>();

        for (String l : lore) {
            itemLore.add(ChatColor.translateAlternateColorCodes('&', l));
        }

        this.meta.setLore(itemLore);

        return this;
    }

    public Items enchant(Enchantment ench, int level) {
        this.meta.addEnchant(ench, level, true);
        return this;
    }

    public Items attribute(Attribute attr, String name, double modifier, AttributeModifier.Operation op) {
        this.meta.addAttributeModifier(attr, new AttributeModifier(name, modifier, op));
        return this;
    }

    public ItemStack build() {
        ItemStack is = new ItemStack(this.m);
        is.setItemMeta(this.meta);
        is.setAmount(this.amount);
        return is;
    }
}
