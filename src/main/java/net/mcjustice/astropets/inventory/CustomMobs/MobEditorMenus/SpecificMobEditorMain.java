package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.MobInventoryContentsEditor;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpecificMobEditorMain extends Menu {

    private AstroMob selectedMob;

    public SpecificMobEditorMain(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;
        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public String getMenuName() {
        return (ChatColor.BLUE + "" + ChatColor.BOLD + "Editing ") + ChatColor.translateAlternateColorCodes('&', selectedMob.getMobDisplayName().getCurrentMemoryValue());
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        // Add name, lore, and quote editors
        ItemStack attributeEditor = new ItemStack(Material.DIAMOND);
        ItemStack targetEditor = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack spawnChance = new ItemStack(Material.SPAWNER);
        ItemStack invContents = new ItemStack(Material.CHEST);

        ItemMeta meta;

        meta = attributeEditor.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Attributes");
        attributeEditor.setItemMeta(meta);

        meta = targetEditor.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Whitelisted Targets");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        targetEditor.setItemMeta(meta);

        meta = spawnChance.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Spawn Chance");
        spawnChance.setItemMeta(meta);

        meta = invContents.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Inventory Contents");
        invContents.setItemMeta(meta);

        inventory.setItem(10, attributeEditor);
        inventory.setItem(12, targetEditor);
        inventory.setItem(14, spawnChance);
        inventory.setItem(16, invContents);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (e.getRawSlot() == 10) {

                new MobAttributeEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
            } else if (e.getRawSlot() == 12) {

                new WhitelistedTargetsEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
            } else if (e.getRawSlot() == 14) {

                new MobSpawnChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
            } else if (e.getRawSlot() == 16) {

                new MobInventoryContentsEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
            }
        }
    }
}
