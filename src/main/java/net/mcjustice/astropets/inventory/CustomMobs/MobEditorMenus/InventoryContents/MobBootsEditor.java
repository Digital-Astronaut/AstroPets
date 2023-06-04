package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.ChanceMenus.MobBootsChanceEditor;
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

public class MobBootsEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    private HashMap<Integer, String> mapItemsToSlots = new HashMap<>();

    public MobBootsEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        return ItemFile.getAllValidBootsAndCustomBoots();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack displayBoots;
            ItemMeta meta;

            if (Material.matchMaterial(s) != null) {
                displayBoots = new ItemStack(Material.valueOf(s));
            } else {
                displayBoots = ItemFile.getItemsMap().get(FileUtils.checkForYML(s).toUpperCase()).getItemStack(AstroPets.getPlugin());
            }
            List<String> lore = new ArrayList<>();

            if (selectedMob.getMobBootsItem().getCurrentMemoryValue().equalsIgnoreCase(s)) {
                lore.add(ChatColor.GREEN + "Currently selected");
                meta = displayBoots.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add(ChatColor.RED + "Not currently selected");
                lore.add(ChatColor.YELLOW + "Click to select this item");
                meta = displayBoots.getItemMeta();
            }
            meta.setDisplayName(ChatColor.AQUA + s);
            meta.setLore(lore);
            displayBoots.setItemMeta(meta);

            mapItemsToSlots.put(inventory.firstEmpty(), s);
            inventory.setItem(inventory.firstEmpty(), displayBoots);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Boots Editor";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (mapItemsToSlots.containsKey(e.getRawSlot())) {

                if (selectedMob.getMobBootsItem().getCurrentMemoryValue().equalsIgnoreCase(mapItemsToSlots.get(e.getRawSlot()))) {

                    new MobBootsChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                } else {

                    selectedMob.getMobBootsItem().setCurrentMemoryValue(mapItemsToSlots.get(e.getRawSlot()));

                    open();

                    if (e.getClick() == ClickType.RIGHT) {

                        new MobBootsChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                }
            }
        }
    }
}
