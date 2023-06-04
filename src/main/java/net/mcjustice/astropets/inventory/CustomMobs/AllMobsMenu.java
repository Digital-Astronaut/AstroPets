package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.MobUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astroapi.Utils.TextUtils;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllMobsMenu extends PaginatedMenu {

    public AllMobsMenu(PlayerMenuUtility playerMenuUtility, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        return MobUtils.getEntityTypeListString();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack eggItem = MobUtils.getMobDisplayEgg(s);
            ItemMeta eggMeta = eggItem.getItemMeta();

            eggMeta.setDisplayName(ChatColor.GREEN + TextUtils.capitalizeWordAndReplaceDelimiter(s, "_"));

            List<String> lore = new ArrayList<>();

            int amountOfType = 0;

            for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {
                if (entry.getValue().getParentFile().getName().equalsIgnoreCase(s)) {
                    amountOfType++;
                }
            }
            if (amountOfType == 1) {
                lore.add(ChatColor.YELLOW + "Currently " + amountOfType + " mob of this type");
                lore.add(ChatColor.YELLOW + "Click to view it");
            } else if (amountOfType == 0) {
                lore.add(ChatColor.YELLOW + "Currently " + amountOfType + " mobs of this type");
                lore.add(ChatColor.YELLOW + "Click to create one");
            } else {
                lore.add(ChatColor.YELLOW + "Currently " + amountOfType + " mobs of this type");
                lore.add(ChatColor.YELLOW + "Click to view them");
            }

            eggMeta.setLore(lore);

            eggItem.setItemMeta(eggMeta);

            inventory.setItem(inventory.firstEmpty(), eggItem);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Select a mob type";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {

            for (String s : e.getCurrentItem().getItemMeta().getLore()) {
                if (ChatColor.stripColor(s).contains("Click to view")) {

                    new SpecificMobTypeMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace(" ", "_")), this).open();
                }
            }

            // Add mob creator with type here.
        }


    }
}
