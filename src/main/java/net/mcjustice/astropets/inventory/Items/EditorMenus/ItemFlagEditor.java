package net.mcjustice.astropets.inventory.Items.EditorMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemFlagEditor extends Menu {

    private AstroItem selectedItem;

    public ItemFlagEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setSelectedItem(selectedItem);

        setMainMenu(new ItemsMainMenu(playerMenuUtility));

    }

    public AstroItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(AstroItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Reward Item Flags";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        for (String flag : getSelectedItem().getItemFlags().getValidValues()) {

            ItemStack wool = new ItemStack(Material.RED_WOOL);
            ItemMeta meta = wool.getItemMeta();

            List<String> lore = new ArrayList<>();

            if (getSelectedItem().getItemFlags().getCurrentMemoryValue() == null) {
                getSelectedItem().getItemFlags().setCurrentMemoryValue(new ArrayList<>());
            }

            if (getSelectedItem().getItemFlags().getCurrentMemoryValue().contains(flag)) {
                wool.setType(Material.GREEN_WOOL);
                meta.setDisplayName(ChatColor.GREEN + flag.toUpperCase());
                lore.add(ChatColor.YELLOW + "Click to remove this flag");

            } else {
                meta.setDisplayName(ChatColor.RED + flag.toUpperCase());
                lore.add(ChatColor.YELLOW + "Click to add this flag");
            }

            meta.setLore(lore);
            wool.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), wool);
        }
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {
            if (e.getCurrentItem().hasItemMeta()) {

                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    if (getSelectedItem().getItemFlags().getCurrentMemoryValue() == null) {
                        getSelectedItem().getItemFlags().setCurrentMemoryValue(new ArrayList<>());
                    }

                    if (getSelectedItem().getItemFlags().getCurrentMemoryValue().contains(metaNoColor)) {
                        getSelectedItem().getItemFlags().getCurrentMemoryValue().remove(metaNoColor);

                    } else if (!getSelectedItem().getItemFlags().getCurrentMemoryValue().contains(metaNoColor)) {
                        getSelectedItem().getItemFlags().getCurrentMemoryValue().add(metaNoColor);
                    }

                    open();
                }
            }
        }
    }
}
