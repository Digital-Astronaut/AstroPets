package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.ChanceMenus.MobOffHandChanceEditor;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobOffHandEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    private HashMap<Integer, String> mapItemsToSlots = new HashMap<>();

    public MobOffHandEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        return ItemFile.getAllValidMatsAndEnabledHeldItems();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack displayItem;
            ItemMeta meta;

            if (Material.matchMaterial(s) != null) {
                displayItem = new ItemStack(Material.valueOf(s));
                meta = displayItem.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + s);
            } else {
                displayItem = ItemFile.getItemsMap().get(FileUtils.checkForYML(s).toUpperCase()).getItemStack(AstroPets.getPlugin());
            }
            List<String> lore = new ArrayList<>();

            meta = displayItem.getItemMeta();

            if (selectedMob.getMobOffHandItem().getCurrentMemoryValue().equalsIgnoreCase(s)) {
                lore.add(ChatColor.GREEN + "Currently selected");
                meta = displayItem.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.setLore(lore);
                displayItem.setItemMeta(meta);
            } else {
                lore.add(ChatColor.RED + "Not currently selected");
                lore.add(ChatColor.YELLOW + "Click to select this item");
            }
            meta.setLore(lore);
            displayItem.setItemMeta(meta);

            mapItemsToSlots.put(inventory.firstEmpty(), s);
            inventory.setItem(inventory.firstEmpty(), displayItem);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return 1232;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Off Hand Editor";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (mapItemsToSlots.containsKey(e.getRawSlot())) {

                if (selectedMob.getMobOffHandItem().getCurrentMemoryValue().equalsIgnoreCase(mapItemsToSlots.get(e.getRawSlot()))) {

                    new MobOffHandChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                } else {

                    selectedMob.getMobOffHandItem().setCurrentMemoryValue(mapItemsToSlots.get(e.getRawSlot()));

                    open();

                    if (e.getClick() == ClickType.RIGHT) {

                        new MobOffHandChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                }
            }
        }
    }
}
