package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.ChanceMenus.MobHelmetChanceEditor;
import net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents.ChanceMenus.MobLeggingsChanceEditor;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobHelmetEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    private HashMap<Integer, String> mapItemsToSlots = new HashMap<>();

    public MobHelmetEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public String getMenuName() {

        return ChatColor.BLUE + "" + ChatColor.BOLD + "Helmet Editor";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {

            if (mapItemsToSlots.containsKey(e.getRawSlot())) {

                if (selectedMob.getMobHelmetItem().getCurrentMemoryValue().equalsIgnoreCase(mapItemsToSlots.get(e.getRawSlot()))) {

                    new MobHelmetChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                } else {

                    selectedMob.getMobHelmetItem().setCurrentMemoryValue(mapItemsToSlots.get(e.getRawSlot()));

                    open();

                    if (e.getClick() == ClickType.RIGHT) {

                        new MobHelmetChanceEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                }
            }
        }
    }

    @Override
    public List<?> getData() {
        return ItemFile.getAllValidHelmetsAndCustomHelmets();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack displayHelmet;
            ItemMeta meta;

            if (Material.matchMaterial(s) != null) {
                displayHelmet = new ItemStack(Material.valueOf(s));
            } else {
                displayHelmet = ItemFile.getItemsMap().get(FileUtils.checkForYML(s).toUpperCase()).getItemStack(AstroPets.getPlugin());
            }
            List<String> lore = new ArrayList<>();

            if (selectedMob.getMobHelmetItem().getCurrentMemoryValue().equalsIgnoreCase(s)) {
                lore.add(ChatColor.GREEN + "Currently selected");
                meta = displayHelmet.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add(ChatColor.RED + "Not currently selected");
                lore.add(ChatColor.YELLOW + "Click to select this item");
                meta = displayHelmet.getItemMeta();
            }
            meta.setDisplayName(ChatColor.AQUA + s);
            meta.setLore(lore);
            displayHelmet.setItemMeta(meta);

            mapItemsToSlots.put(inventory.firstEmpty(), s);
            inventory.setItem(inventory.firstEmpty(), displayHelmet);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }
}
