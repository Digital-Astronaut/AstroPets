package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.ParticleUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
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

public class ParticleBlockMenu extends PaginatedMenu {

    AstroItem selectedItem;

    public ParticleBlockMenu(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    public AstroItem getSelectedItem() { return selectedItem; }

    @Override
    public List<?> getData() {
        return ParticleUtils.getValidParticleBlocksMaterial();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material mat) {
            ItemStack material = new ItemStack(mat);
            List<String> lore = new ArrayList<>();
            ItemMeta meta = material.getItemMeta();
            String color;
            lore.clear();

            if (getSelectedItem().getParticleBlock().getCurrentMemoryValue().equalsIgnoreCase(mat.toString())) {
                lore.add(ChatColor.GREEN + "Currently selected");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                color = ChatColor.GREEN + "";
            } else {
                color = ChatColor.YELLOW + "";
                lore.add(color + "Click to set this as the block for this particle effect");
            }
            meta.setLore(lore);
            meta.setDisplayName(color + getData().get(index).toString());

            material.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), material);
        }

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().indexOf(Material.POINTED_DRIPSTONE) + 1;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Particle Block";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 27) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    getSelectedItem().getParticleBlock().setCurrentMemoryValue(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));

                    open();
                }
            }
        }
    }
}
