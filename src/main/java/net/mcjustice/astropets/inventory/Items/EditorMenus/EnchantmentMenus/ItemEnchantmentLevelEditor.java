package net.mcjustice.astropets.inventory.Items.EditorMenus.EnchantmentMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemEnchantmentLevelEditor extends PaginatedMenu {

    private AstroItem selectedItem;
    private String selectedEnchantment;

    public ItemEnchantmentLevelEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, String selectedEnchantment, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);
        this.selectedEnchantment = selectedEnchantment;

        setMainMenu(new ItemsMainMenu(playerMenuUtility));
    }

    public void setSelectedItem(AstroItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    public AstroItem getSelectedItem() {
        return selectedItem;
    }

    // TODO: Use the max enchant value for custom enchants and a fixed one for Minecraft enchants.
    @Override
    public List<?> getData() {

        List<Material> mats = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            mats.add(Material.RED_WOOL);
        }

        return mats;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material m) {

            ItemStack wool = new ItemStack(m);
            ItemMeta meta = wool.getItemMeta();
            List<String> lore = new ArrayList<>();

            wool.setAmount(index + 1);

            if (getSelectedItem().getEnchantments().getCurrentMemoryValue().get(selectedEnchantment).equals(wool.getAmount())) {

                wool.setType(Material.GREEN_WOOL);
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Current level");
            } else {
                meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + wool.getAmount());
                lore.add(ChatColor.YELLOW + "Click to set the level to " + wool.getAmount());
            }

            meta.setLore(lore);

            wool.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), wool);
        }

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return 50;
    }

    @Override
    public String getMenuName()  {
        return ChatColor.BLUE + "" + ChatColor.BOLD + selectedEnchantment + " Level";
    }

    // TODO: Add a method to retrieve the item's name without color in the Menu class.
    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    if (metaNoColor.contains("Current level")) {
                        return;
                    }
                    getSelectedItem().getEnchantments().getCurrentMemoryValue().replace(selectedEnchantment, e.getCurrentItem().getAmount());
                    open();
                }
            }
        }
    }
}
