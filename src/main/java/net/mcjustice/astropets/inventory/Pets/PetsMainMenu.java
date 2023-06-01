package net.mcjustice.astropets.inventory.Pets;

import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.file.PetFile;
import net.mcjustice.astropets.mobs.AstroPet;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PetsMainMenu extends PaginatedMenu {

    public HashMap<Integer, AstroPet> mapPetSlots = new HashMap<>();

    public HashMap<Integer, AstroPet> getMapPetSlots() { return mapPetSlots; }

    public void setMapCrateSlots(HashMap<Integer, AstroPet> mapPetSlots) { this.mapPetSlots = mapPetSlots; }

    public PetsMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public List<?> getData() {

        return new ArrayList<>(PetFile.getPetsMap().keySet());
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack petType = new ItemStack(Material.valueOf(PetFile.getPetsMap().get(s).getPetType() + "_SPAWN_EGG"));
            ItemMeta meta = petType.getItemMeta();

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', PetFile.getPetsMap().get(s).getDisplayName()));

            petType.setItemMeta(meta);

            mapPetSlots.put(inventory.firstEmpty(), PetFile.getPetsMap().get(s));
            inventory.setItem(inventory.firstEmpty(), petType);
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

                    new PetEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getMapPetSlots().get(e.getRawSlot()), this).open();
                }
            }
        }
    }
}
