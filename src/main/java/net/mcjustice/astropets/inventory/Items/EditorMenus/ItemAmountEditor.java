package net.mcjustice.astropets.inventory.Items.EditorMenus;

import net.mcjustice.astroapi.FileParameters.AstroInteger;
import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemAmountEditor extends PaginatedMenu {

    AstroItem selectedItem;

    public ItemAmountEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public void setSelectedItem(AstroItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public AstroItem getSelectedItem() { return selectedItem; }

    @Override
    public List<?> getData() {
        List<Material> material = new ArrayList<>();

        Material matToAdd;

        if (getSelectedItem() == null) {
            matToAdd = Material.STONE;
        } else {
            matToAdd = Material.getMaterial(getSelectedItem().getItemResultMaterial().getCurrentMemoryValue().toUpperCase());
        }

        for (int i = 0; i < getMaxAmountOfDisplayedItems(); i++) {
            material.add(matToAdd);
        }
        return material;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material m) {
            ItemStack amount = new ItemStack(m, index + 1);

            ItemMeta meta = amount.getItemMeta();

            if (getSelectedItem().getItemResultAmount().getCurrentMemoryValue() == amount.getAmount()) {
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Current amount");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Click to set the amount to " + (index + 1) + "!");
            }
            amount.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), amount);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return 64;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Item Amount Selector";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    getSelectedItem().getItemResultAmount().setCurrentMemoryValue(e.getCurrentItem().getAmount());
                }
                open();
            }
        }

    }
}
