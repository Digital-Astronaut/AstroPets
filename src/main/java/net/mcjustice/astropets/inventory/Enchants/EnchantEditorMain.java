package net.mcjustice.astropets.inventory.Enchants;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.enchantments.CustomEnchant;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

public class EnchantEditorMain extends Menu {

    private CustomEnchant selectedEnchant;

    public EnchantEditorMain(PlayerMenuUtility playerMenuUtility, CustomEnchant selectedEnchant, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setSelectedEnchant(selectedEnchant);

        if (previousMenu != null)
            setMainMenu(new EnchantsMainMenu(playerMenuUtility));
    }

    public CustomEnchant getSelectedEnchant() { return selectedEnchant; }

    public void setSelectedEnchant(CustomEnchant selectedEnchant) { this.selectedEnchant = selectedEnchant; }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Editing " + ChatColor.translateAlternateColorCodes('&', getSelectedEnchant().getDisplayNameValue());
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
