package net.mcjustice.astropets.inventory.Items;

import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsMainMenu extends PaginatedMenu {

    public HashMap<Integer, AstroItem> mapItemSlots = new HashMap<>();

    public HashMap<Integer, AstroItem> getMapItemSlots() {
        return mapItemSlots;
    }

    public ItemsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public List<?> getData() {
        return new ArrayList<>(ItemFile.getItemsMap().keySet());
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            AstroItem as = ItemFile.getItemsMap().get(s);

            ItemStack itemType = as.getItemStack(AstroPets.getPlugin());

            // was ItemFile.getItemsMap().get(s)
            mapItemSlots.put(inventory.firstEmpty(), as);
            inventory.setItem(inventory.firstEmpty(), itemType);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Items List";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (getMapItemSlots().containsKey(e.getRawSlot())) {

                if (e.getWhoClicked().isOp()) {

                    if (e.getClick() == ClickType.RIGHT) {

                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                        return;
                    }

                    new ItemEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getMapItemSlots().get(e.getRawSlot()), this).open();
                }
            }
        }
    }
}
