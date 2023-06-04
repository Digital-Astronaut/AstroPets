package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.ChanceMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
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

public class MobOffHandChanceEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    public MobOffHandChanceEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {

        List<Material> mats = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            mats.add(Material.RED_WOOL);
        }

        return mats;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material m) {

            ItemStack wool = new ItemStack(m);

            ItemMeta meta = wool.getItemMeta();

            if (getMaxAmountOfDisplayedItems() < 64) {
                wool.setAmount(index + 1);
            }

            List<String> lore = new ArrayList<>();

            if (selectedMob.getMobOffHandChance().getCurrentMemoryValue() == (index + 1)) {
                wool.setType(Material.GREEN_WOOL);

                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Current chance (" + (index + 1) + ")");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                meta.setDisplayName(ChatColor.AQUA + "" + (index + 1));
                lore.add(ChatColor.AQUA + "" + ChatColor.BOLD + "Click to set the amount to " + (index + 1) + "!");
            }
            meta.setLore(lore);
            wool.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), wool);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {

        return selectedMob.getMobOffHandChance().getMaximumValue();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Off Hand Item Chance";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    selectedMob.getMobOffHandChance().setCurrentMemoryValue(Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                }
                open();
            }
        }
    }
}
