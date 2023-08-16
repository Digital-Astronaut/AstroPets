package net.mcjustice.astropets.inventory.Items.EditorMenus.EnchantmentMenus;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemEnchantmentsEditor extends PaginatedMenu {

    AstroItem selectedItem;

    public ItemEnchantmentsEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        setMainMenu(new ItemsMainMenu(playerMenuUtility));
    }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    public AstroItem getSelectedItem() { return selectedItem; }

    // Was ItemUtils.getValidEnchantments() but we're gonna try this.
    @Override
    public List<?> getData() {
        return getSelectedItem().getEnchantments().getValidKeys();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack wool = new ItemStack(Material.RED_WOOL);
            ItemMeta meta = wool.getItemMeta();

            List<String> lore = new ArrayList<>();

            if (getSelectedItem().getEnchantments().getCurrentMemoryValue() == null) {
                getSelectedItem().getEnchantments().setCurrentMemoryValue(new HashMap<>());
            }

            getSelectedItem().getEnchantments().getCurrentMemoryValue().keySet().forEach(key -> key.toUpperCase());

            if (getSelectedItem().getEnchantments().getCurrentMemoryValue().containsKey(s)) {
                wool.setType(Material.GREEN_WOOL);
                wool.setAmount(getSelectedItem().getEnchantments().getCurrentMemoryValue().get(s));

                meta.setDisplayName(ChatColor.GREEN + s.toUpperCase());
                lore.add(ChatColor.YELLOW + "Left click to remove this enchantment");
                lore.add(ChatColor.YELLOW + "Right click to set the level of this enchantment");
            } else {
                wool.setType(Material.RED_WOOL);
                meta.setDisplayName(ChatColor.RED + s.toUpperCase());
                lore.add(ChatColor.YELLOW + "Left click to add this enchantment");
                lore.add(ChatColor.YELLOW + "Right click to add this enchantment and set the level");
            }
            meta.setLore(lore);
            wool.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), wool);
        }

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Reward Enchantments";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    if (e.getClick() == ClickType.LEFT) {
                        if (getSelectedItem().getEnchantments().getCurrentMemoryValue().containsKey(metaNoColor.toUpperCase())) {
                            getSelectedItem().getEnchantments().getCurrentMemoryValue().remove(metaNoColor.toUpperCase());
                        } else if (!getSelectedItem().getEnchantments().getCurrentMemoryValue().containsKey(metaNoColor.toUpperCase())) {

                            getSelectedItem().getEnchantments().getCurrentMemoryValue().put(metaNoColor.toUpperCase(), 1);
                        }
                        open();
                    } else if (e.getClick() == ClickType.RIGHT) {
                        if (!getSelectedItem().getEnchantments().getCurrentMemoryValue().containsKey(metaNoColor.toUpperCase())) {
                            getSelectedItem().getEnchantments().getCurrentMemoryValue().put(metaNoColor.toUpperCase(), 1);
                        }

                        new ItemEnchantmentLevelEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), metaNoColor.toUpperCase(), this).open();
                    }
                }
            }
        }

    }
}
