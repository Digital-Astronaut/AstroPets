package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.ParticleUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astroapi.utils.TextUtils;
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

public class ParticleListView extends PaginatedMenu {

    AstroItem selectedItem;

    public ParticleListView(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public AstroItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(AstroItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public List<?> getData() {
        return ParticleUtils.getParticleTypeMap().get(getSelectedItem().getParticleType().getCurrentMemoryValue());
    }

    @Override
    public void loopData(Object o) {

        ItemStack particle = new ItemStack(Material.RED_WOOL);
        ItemMeta meta = particle.getItemMeta();

        List<String> lore = new ArrayList<>();
        String color;
        lore.clear();

        if (getData().get(index).toString().equalsIgnoreCase(getSelectedItem().getParticleEffect().getCurrentMemoryValue())) {
            particle = new ItemStack(Material.GREEN_WOOL);
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            color = ChatColor.GREEN + "";
            lore.add(ChatColor.GREEN + "Currently selected");
        } else {
            color = ChatColor.YELLOW + "";
            lore.add(ChatColor.YELLOW + "Click to set this as the particle effect");
        }
        meta.setDisplayName(color + getData().get(index).toString());
        meta.setLore(lore);

        particle.setItemMeta(meta);

        inventory.setItem(inventory.firstEmpty(), particle);

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + TextUtils.capitalizeString(getSelectedItem().getParticleType().getCurrentMemoryValue() + " Particle");
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
                    getSelectedItem().getParticleEffect().setCurrentMemoryValue(metaNoColor);
                    open();
                }
            }
        }
    }
}
