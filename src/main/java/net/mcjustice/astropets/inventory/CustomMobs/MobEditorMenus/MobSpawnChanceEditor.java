package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MobSpawnChanceEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    public MobSpawnChanceEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        List<Material> material = new ArrayList<>();

        Material matToAdd = Material.RED_WOOL;

        for (int i = 0; i < getMaxAmountOfDisplayedItems(); i++) {
            material.add(matToAdd);
        }
        return material;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material m) {

            ItemStack amount = new ItemStack(m);

            ItemMeta meta = amount.getItemMeta();

            List<String> lore = new ArrayList<>();

            if (getMaxAmountOfDisplayedItems() < 64) {
                amount.setAmount(index + 1);
            }

            if (selectedMob.getSpawnChance().getCurrentMemoryValue() == (index + 1)) {
                amount.setType(Material.GREEN_WOOL);
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Current chance (" + (index + 1) + ")");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                meta.setDisplayName(ChatColor.AQUA + "" + (index + 1));
                lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Click to set the amount to " + (index + 1) + "!");
            }
            amount.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), amount);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return selectedMob.getSpawnChance().getMaximumValue();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Mob Spawn Chance";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    selectedMob.getSpawnChance().setCurrentMemoryValue(Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                }
                open();
            }
        }
    }
}
