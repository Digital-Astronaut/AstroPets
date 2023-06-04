package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus;

import net.mcjustice.astroapi.FileParameters.AstroBoolean;
import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobAttributeEditorMain extends Menu {

    private HashMap<Integer, AstroBoolean> mapAstroBoolSlots = new HashMap<>();

    private AstroMob selectedMob;

    public MobAttributeEditorMain(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;
        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    public HashMap<Integer, AstroBoolean> getMapAstroBoolSlots() {

        return mapAstroBoolSlots;
    }

    @Override
    public String getMenuName() {

        return ChatColor.BLUE + "" + ChatColor.BOLD + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', selectedMob.getMobDisplayName().getCurrentMemoryValue())) + " Attributes";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

        booleanAttributeItem(selectedMob.getCanPickupItems(), 11);
        booleanAttributeItem(selectedMob.getHasAI(), 13);
        booleanAttributeItem(selectedMob.getHasGravity(), 15);
        booleanAttributeItem(selectedMob.getIsGlowing(), 21);
        booleanAttributeItem(selectedMob.getIsInvulnerable(), 23);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (getMapAstroBoolSlots().containsKey(e.getRawSlot())) {

                getMapAstroBoolSlots().get(e.getRawSlot()).setCurrentMemoryValue(!getMapAstroBoolSlots().get(e.getRawSlot()).getCurrentMemoryValue());
            }

            open();
        }
    }

    public void booleanAttributeItem(AstroBoolean attribute, int slot) {

        ItemStack attributeItem;
        ItemMeta meta;

        List<String> lore = new ArrayList<>();

        if (attribute.getCurrentMemoryValue()) {
            attributeItem = new ItemStack(Material.GREEN_WOOL);
            meta = attributeItem.getItemMeta();

            meta.setDisplayName(ChatColor.GREEN + attribute.getPathName().replace("Attributes.", "") + " is enabled");
            lore.add(ChatColor.YELLOW + "Click to disable " + attribute.getPathName().replace("Attributes.", "").toLowerCase());
        } else {
            attributeItem = new ItemStack(Material.RED_WOOL);
            meta = attributeItem.getItemMeta();

            meta.setDisplayName(ChatColor.RED + attribute.getPathName().replace("Attributes.", "") + " is disabled");
            lore.add(ChatColor.YELLOW + "Click to enable " + attribute.getPathName().replace("Attributes.", "").toLowerCase());
        }

        meta.setLore(lore);
        attributeItem.setItemMeta(meta);

        mapAstroBoolSlots.put(slot, attribute);
        inventory.setItem(slot, attributeItem);
    }
}
