package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class WhitelistedTargetsEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    public WhitelistedTargetsEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        return null;
    }

    @Override
    public void loopData(Object o) {

    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return 0;
    }

    @Override
    public String getMenuName() {
        return null;
    }

    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) {

    }
}
