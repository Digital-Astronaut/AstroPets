package net.mcjustice.astropets.inventory.Items.EditorMenus;

import net.mcjustice.astroapi.fileparameters.AstroString;
import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.MaterialUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemMaterialEditor extends PaginatedMenu {

    AstroItem selectedItem;

    public ItemMaterialEditor(PlayerMenuUtility playerMenuUtility, AstroItem astroItem, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setSelectedItem(astroItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public AstroItem getSelectedItem() { return selectedItem; }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    @Override
    public List<?> getData() {
        return MaterialUtils.allValidMaterialsList();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material mat) {
            ItemStack material = new ItemStack(mat);
            List<String> lore = new ArrayList<>();
            ItemMeta meta = material.getItemMeta();
            String color;
            lore.clear();

            if (getSelectedItem().getItemResultMaterial().getCurrentMemoryValue().equalsIgnoreCase(mat.toString())) {
                lore.add(ChatColor.GREEN + "Currently selected");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                lore.add(ChatColor.YELLOW + "Right click to set the result amount");
                color = ChatColor.GREEN + "";
                meta.setLore(lore);
                meta.setDisplayName(color + getData().get(index).toString());
                material.setItemMeta(meta);
//                setCurrentValueDisplayItem(material);
            } else {
                color = ChatColor.YELLOW + "";
                lore.add(color + "Left click to set this as the result material for this item");
                lore.add(color + "Right click to set this as the result material and select the amount");
            }
            meta.setLore(lore);
            meta.setDisplayName(color + getData().get(index).toString());

            material.setItemMeta(meta);
            meta.getLore().clear();

            inventory.setItem(inventory.firstEmpty(), material);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().indexOf(Material.POINTED_DRIPSTONE) + 1;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Item Material Selector";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {

            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                    if (e.getClick() == ClickType.LEFT || e.getClick() == ClickType.RIGHT) {

                        if (getSelectedItem() != null) {
                            getSelectedItem().getItemResultMaterial().setCurrentMemoryValue(e.getCurrentItem().getType().toString());
                        }

                        if (e.getClick() == ClickType.RIGHT) {
                            new ItemAmountEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
                            return;
                        }
                        open();
                    }
                }
            }
        }
    }
}
