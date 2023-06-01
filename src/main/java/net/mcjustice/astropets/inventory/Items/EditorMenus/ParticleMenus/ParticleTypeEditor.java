package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astroapi.Utils.TextUtils;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

public class ParticleTypeEditor extends Menu {

    AstroItem selectedItem;

    public ParticleTypeEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    public AstroItem getSelectedItem() {
        return selectedItem;
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + TextUtils.capitalizeString(getSelectedItem().getParticleType().getCurrentMemoryValue() + " Particles");
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {
        particleParameterButtons();
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 27) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    if (metaNoColor.equalsIgnoreCase("Particle List")) {
                        new ParticleListView(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
                    } else if (metaNoColor.equalsIgnoreCase("Particle Count") || metaNoColor.equalsIgnoreCase("Particle Size") || metaNoColor.equalsIgnoreCase("Vibration Duration")) {
                        new ParticleNumberVariableEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), metaNoColor, this).open();
                    } else if (metaNoColor.equalsIgnoreCase("Block List")) {
                        new ParticleBlockMenu(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(),  this).open();
                    } else if (metaNoColor.equalsIgnoreCase("Particle Color")
                            || metaNoColor.equalsIgnoreCase("Particle Offset Values")
                            || metaNoColor.equalsIgnoreCase("Transition Color")) {

                        new ParticleColorAndDirectionEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), metaNoColor, this).open();
                    }
                }
            }
        }

    }

    public void particleParameterButtons() {

        // boolean particle, boolean shape, boolean block, boolean size, boolean count,
        // boolean offSets, boolean time, boolean colors, boolean toColors

        String type = getSelectedItem().getParticleType().getCurrentMemoryValue().toUpperCase();

        switch (type) {

            case "NORMAL":
                loadParticleParameters(true, true, false, false, true, true, false, false, false);
                break;
            case "REDSTONE":
                loadParticleParameters(false, true, false, true, true, false, false, true, false);
                break;
            case "DIRECTIONAL":
                loadParticleParameters(true, true, false, false, false, true, false, true, false);
                break;
            case "VIBRATIONAL":
                loadParticleParameters(false, false, false, false, true, false, true, false, false);
                break;
            case "MATERIAL":
                // set the particle parameter to true when I add the code in the method
                loadParticleParameters(true, true, true, false, true, false, false, false, false);
                break;
            case "SHAPE":
                loadParticleParameters(true, true, true, false, true, false, false, true, true);
                break;
            case "TRANSITIONAL":
                loadParticleParameters(false, true, false, true, false, false, false, true, true);
                break;
            default:
                loadParticleParameters(false, false, false, false, false, false, false, false, false);
        }
    }

    public void loadParticleParameters(boolean particle, boolean shape, boolean block, boolean size, boolean count,
                                       boolean offSets, boolean time, boolean colors, boolean toColors) {

        ItemMeta meta;

        if (particle) {
            ItemStack particleItem = new ItemStack(Material.BEACON);
            meta = particleItem.getItemMeta();
            // add the list changing the name based on if it is a block particle or not
            // (only list the 4 parameters if it is).
            meta.setDisplayName(ChatColor.GREEN + "Particle List");
            particleItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), particleItem);
        }

        if (block) {
            ItemStack blockItem = new ItemStack(Material.GOLD_BLOCK);
            meta = blockItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Block List");
            blockItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), blockItem);
        }

        if (size) {
            ItemStack sizeItem = new ItemStack(Material.CHEST);
            meta = sizeItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Particle Size");
            sizeItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), sizeItem);

        }

        if (count) {
            ItemStack countItem = new ItemStack(Material.OAK_SIGN);
            meta = countItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Particle Count");
            countItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), countItem);

        }

        if (offSets) {
            ItemStack offsetsItem = new ItemStack(Material.HONEY_BLOCK);
            meta = offsetsItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Particle Offset Values");
            offsetsItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), offsetsItem);
        }

        if (time) {
            ItemStack timeItem = new ItemStack(Material.NOTE_BLOCK);
            meta = timeItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Vibration Duration");
            timeItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), timeItem);
        }

        if (colors) {
            ItemStack colorsItem = new ItemStack(Material.CYAN_DYE);
            meta = colorsItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Particle Color");
            colorsItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), colorsItem);
        }

        if (toColors) {
            ItemStack toColorsItem = new ItemStack(Material.GREEN_DYE);
            meta = toColorsItem.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Transition Color");
            toColorsItem.setItemMeta(meta);
            inventory.setItem(inventory.firstEmpty(), toColorsItem);
        }
    }
}
