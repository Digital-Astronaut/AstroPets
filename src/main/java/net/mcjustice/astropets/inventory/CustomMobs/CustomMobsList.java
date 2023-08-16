package net.mcjustice.astropets.inventory.CustomMobs;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.MobUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.SpecificMobEditorMain;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomMobsList extends PaginatedMenu {

    private HashMap<Integer, AstroMob> mapMobSlots = new HashMap<>();

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

                if (e.getClick() == ClickType.LEFT) {

                    new SpecificMobEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getMapMobSlots().get(e.getRawSlot()), this).open();

                } else if (e.getClick() == ClickType.RIGHT) {

                    AstroMobUtils.spawnMobAtPlayerLocation(getMapMobSlots().get(e.getRawSlot()), (Player) e.getWhoClicked());
                }
            }
        }
    }
}
