package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.MobUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astroapi.Utils.TextUtils;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SpecificMobTypeMenu extends PaginatedMenu {

    private String selectedMobType;

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



    }
}
