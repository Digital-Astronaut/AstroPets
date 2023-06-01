package net.mcjustice.astropets.inventory.Pets;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.mobs.AstroPet;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

public class PetEditorMain extends Menu {

    private AstroPet selectedPet;

    public PetEditorMain(PlayerMenuUtility playerMenuUtility, AstroPet selectedPet, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setSelectedPet(selectedPet);

        if (previousMenu != null)
            setMainMenu(new PetsMainMenu(playerMenuUtility));
    }

    public AstroPet getSelectedPet() { return selectedPet; }

    public void setSelectedPet(AstroPet selectedPet) { this.selectedPet = selectedPet; }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Editing " + ChatColor.translateAlternateColorCodes('&', getSelectedPet().getDisplayName());
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {

    }

    @Override
    public void handleMenu(InventoryClickEvent inventoryClickEvent) {

    }
}
