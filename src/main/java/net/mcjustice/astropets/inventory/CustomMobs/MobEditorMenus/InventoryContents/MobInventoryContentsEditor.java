package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class MobInventoryContentsEditor extends Menu {

    private AstroMob selectedMob;

    public MobInventoryContentsEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public String getMenuName() {
        return ChatColor.translateAlternateColorCodes('&', selectedMob.getMobDisplayName().getCurrentMemoryValue()) + " Inventory";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {

        ItemStack helmet;

        if (Material.matchMaterial(selectedMob.getMobHelmet().getCurrentMemoryValue()) != null) {

            helmet = new ItemStack(Material.valueOf(selectedMob.getMobHelmet().getCurrentMemoryValue()));
        } else if (ItemFile.getAllValidHelmetsAndCustomHelmets().contains(selectedMob.getMobHelmet().getCurrentMemoryValue().toUpperCase())) {
            helmet = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobHelmet().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
        } else {
            helmet = new ItemStack(Material.valueOf(selectedMob.getMobHelmet().getDefaultValue()));
        }

        ItemStack chestplate;

        if (Material.matchMaterial(selectedMob.getMobChestplate().getCurrentMemoryValue()) != null) {

            chestplate = new ItemStack(Material.valueOf(selectedMob.getMobChestplate().getCurrentMemoryValue()));
        } else if (ItemFile.getAllValidChestplatesAndCustomChestplates().contains(selectedMob.getMobChestplate().getCurrentMemoryValue().toUpperCase())) {
            chestplate = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobChestplate().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
        } else {
            chestplate = new ItemStack(Material.valueOf(selectedMob.getMobChestplate().getDefaultValue()));
        }


        ItemStack leggings;

        if (Material.matchMaterial(selectedMob.getMobLeggings().getCurrentMemoryValue()) != null) {

            leggings = new ItemStack(Material.valueOf(selectedMob.getMobLeggings().getCurrentMemoryValue()));
        } else if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(selectedMob.getMobLeggings().getCurrentMemoryValue().toUpperCase())) {

            leggings = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobLeggings().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
        } else {
            leggings = new ItemStack(Material.valueOf(selectedMob.getMobLeggings().getDefaultValue()));
        }


        ItemStack boots;

        if (Material.matchMaterial(selectedMob.getMobBoots().getCurrentMemoryValue()) != null) {

            boots = new ItemStack(Material.valueOf(selectedMob.getMobBoots().getCurrentMemoryValue()));
        } else if (ItemFile.getAllValidBootsAndCustomBoots().contains(selectedMob.getMobBoots().getCurrentMemoryValue().toUpperCase())) {

            boots = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobLeggings().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
        } else {
            boots = new ItemStack(Material.valueOf(selectedMob.getMobBoots().getDefaultValue()));
        }

//        ItemMeta helmetMeta = helmet.getItemMeta();
//        ItemMeta chestplateMeta = chestplate.getItemMeta();
//        ItemMeta leggingsMeta = leggings.getItemMeta();
//        ItemMeta bootsMeta = boots.getItemMeta();

        inventory.setItem(13, helmet);
        inventory.setItem(22, chestplate);
        inventory.setItem(31, leggings);
        inventory.setItem(40, boots);

    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }
}
