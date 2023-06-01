package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.MobUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.SpecificMobEditorMain;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomMobsList extends PaginatedMenu {

    private HashMap<Integer, AstroMob> mapMobSlots = new HashMap<>();

    private String selectedMob;

    public CustomMobsList(PlayerMenuUtility playerMenuUtility, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    public HashMap<Integer, AstroMob> getMapMobSlots() {

        return mapMobSlots;
    }

    @Override
    public List<?> getData() {
        return new ArrayList<>(CustomMobFile.getCustomMobsMapMappedToFolders().values());
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof AstroMob am) {

            ItemStack eggItem = MobUtils.getMobDisplayEgg(am.getParentFile().getName());

            ItemMeta eggMeta = eggItem.getItemMeta();

            eggMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', am.getMobDisplayName().getCurrentMemoryValue()));

            eggItem.setItemMeta(eggMeta);

            mapMobSlots.put(inventory.firstEmpty(), am);
            inventory.setItem(inventory.firstEmpty(), eggItem);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Custom Mob List";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (getMapMobSlots().containsKey(e.getRawSlot())) {

                new SpecificMobEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getMapMobSlots().get(e.getRawSlot()), this).open();
            }
        }
    }
}
