package net.mcjustice.astropets.inventory.Enchants;

import net.mcjustice.astroapi.Enchantments.CustomEnchantment;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astroapi.Utils.TextUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.enchantments.CustomEnchant;
import net.mcjustice.astropets.file.EnchantmentFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchantsMainMenu extends PaginatedMenu {

    public HashMap<Integer, CustomEnchant> mapEnchantSlots = new HashMap<>();

    public HashMap<Integer, CustomEnchant> getMapEnchantSlots() {
        return mapEnchantSlots;
    }

    public void setMapCrateSlots(HashMap<Integer, CustomEnchant> mapPetSlots) {
        this.mapEnchantSlots = mapPetSlots;
    }

    public EnchantsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public List<?> getData() {

        return new ArrayList<>(EnchantmentFile.getEnchantsMap().keySet());
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack enchantType = new ItemStack(Material.ENCHANTED_BOOK, 1);
            ItemMeta meta = enchantType.getItemMeta();

            CustomEnchant ce = EnchantmentFile.getEnchantsMap().get(s);

            meta.addEnchant(new CustomEnchantment(AstroPets.getPlugin(), ce.getFileNameValue().toLowerCase().replace(".yml", "")), ce.getStartLevelValue(), true);

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ce.getDisplayNameValue()));

            meta.setLore(List.of(ChatColor.GRAY + TextUtils.capitalizeString(ce.getDisplayNameValue()) + " " + TextUtils.convertIntToRoman(ce.getStartLevelValue())));

            enchantType.setItemMeta(meta);

            mapEnchantSlots.put(inventory.firstEmpty(), EnchantmentFile.getEnchantsMap().get(s));
            inventory.setItem(inventory.firstEmpty(), enchantType);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {

        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Pets List";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < getSlots() - 9) {
            if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                if (e.getWhoClicked().isOp()) {

                    if (e.getClick() == ClickType.RIGHT) {

                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                        return;
                    }

                    new EnchantEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getMapEnchantSlots().get(e.getRawSlot()), this).open();
                }
            }
        }

    }
}
