package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.MobUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astroapi.utils.TextUtils;
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
import java.util.Map;

public class SpecificMobTypeMenu extends PaginatedMenu {

    private String selectedMobType;

    private HashMap<Integer, AstroMob> mapMobSlots = new HashMap<>();

    public SpecificMobTypeMenu(PlayerMenuUtility playerMenuUtility, String selectedMobType, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMobType = selectedMobType;
        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {

        List<AstroMob> mobs = new ArrayList<>();

        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {
            if (entry.getValue().getParentFile().getName().equalsIgnoreCase(selectedMobType)) {
                mobs.add(entry.getValue());
            }
        }

        return mobs;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof AstroMob mob) {

            ItemStack eggItem = MobUtils.getMobDisplayEgg(mob.getParentFile().getName());
            ItemMeta eggMeta = eggItem.getItemMeta();

            eggMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', mob.getMobDisplayName().getCurrentMemoryValue()));

            eggItem.setItemMeta(eggMeta);

            mapMobSlots.put(inventory.firstEmpty(), mob);
            inventory.setItem(inventory.firstEmpty(), eggItem);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + TextUtils.capitalizeWordAndReplaceDelimiter(selectedMobType, "_") + " Mobs";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (mapMobSlots.containsKey(e.getRawSlot())) {
                new SpecificMobEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), mapMobSlots.get(e.getRawSlot()), this).open();
            }
        }
    }
}
