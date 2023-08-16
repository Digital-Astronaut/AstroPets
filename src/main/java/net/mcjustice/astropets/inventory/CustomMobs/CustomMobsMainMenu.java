package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomMobsMainMenu extends Menu {

    public CustomMobsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Mob Editor";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void setMenuItems() {

        ItemStack mobList = new ItemStack(Material.DRAGON_EGG);

        ItemStack mobCreator = new ItemStack(Material.MOOSHROOM_SPAWN_EGG);

        ItemMeta meta = mobList.getItemMeta();

        ItemMeta creatorMeta = mobCreator.getItemMeta();

        meta.setDisplayName(ChatColor.AQUA + "Custom Mobs");

        List<String> lore = new ArrayList<>();

        lore.add(ChatColor.YELLOW + "Left click to view custom mobs");
        lore.add(ChatColor.YELLOW + "Right click to view all mobs");

        creatorMeta.setDisplayName(ChatColor.AQUA + "Mob Creator");

        meta.setLore(lore);
        mobList.setItemMeta(meta);

        lore.clear();
        lore.add(ChatColor.YELLOW + "Click to create a new custom mob");
        creatorMeta.setLore(lore);
        mobCreator.setItemMeta(creatorMeta);

        inventory.setItem(12, mobList);
        inventory.setItem(14, mobCreator);

    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 27) {

            if (e.getRawSlot() == 12) {
                if (e.getClick() == ClickType.LEFT) {

                    new CustomMobsList(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();

                } else if (e.getClick() == ClickType.RIGHT) {

                    new AllMobsMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), this).open();
                    // add menu to show all mob spawn eggs so that you can select the type of mob to filter by.
                }
            } else if (e.getRawSlot() == 14) {

            }
        }

    }
}
