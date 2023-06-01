package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParticleColorAndDirectionEditor extends Menu {

    private AstroItem selectedItem;
    private String view;

    public ParticleColorAndDirectionEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, String view, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);

        this.view = view;

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public String getView() { return view; }

    public AstroItem getSelectedItem() { return selectedItem; }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + getView();
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void setMenuItems() {
        List<String> lore = new ArrayList<>();

        ItemStack firstVal = new ItemStack(Material.GREEN_WOOL);
        ItemStack secondVal = new ItemStack(Material.GREEN_WOOL);
        ItemStack thirdVal = new ItemStack(Material.GREEN_WOOL);

        ItemMeta meta = firstVal.getItemMeta();

        if (getView().equalsIgnoreCase("Particle Offset Values")) {

            firstVal = new ItemStack(Material.GREEN_WOOL);

            meta.setDisplayName(ChatColor.YELLOW + "X Offset");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getOffsetX().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            firstVal.setItemMeta(meta);

            lore.clear();
            secondVal = new ItemStack(Material.GREEN_WOOL);
            meta = secondVal.getItemMeta();

            meta.setDisplayName(ChatColor.YELLOW + "Y Offset");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getOffsetY().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            secondVal.setItemMeta(meta);

            lore.clear();
            thirdVal = new ItemStack(Material.GREEN_WOOL);
            meta = thirdVal.getItemMeta();

            meta.setDisplayName(ChatColor.YELLOW + "Z Offset");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getOffsetZ().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            thirdVal.setItemMeta(meta);

        } else if (getView().equalsIgnoreCase("Particle Color")) {

            firstVal = new ItemStack(Material.RED_DYE);
            meta.setDisplayName(ChatColor.RED + "Red Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleColorRed().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            firstVal.setItemMeta(meta);

            lore.clear();
            secondVal = new ItemStack(Material.GREEN_DYE);
            meta = secondVal.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Green Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleColorGreen().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            secondVal.setItemMeta(meta);

            lore.clear();
            thirdVal = new ItemStack(Material.BLUE_DYE);
            meta = thirdVal.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + "Blue Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleColorBlue().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            thirdVal.setItemMeta(meta);

        } else if (getView().equalsIgnoreCase("Transition Color")) {

            firstVal = new ItemStack(Material.RED_DYE);
            meta.setDisplayName(ChatColor.RED + "Red Transition Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleTransitionRed().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            firstVal.setItemMeta(meta);

            lore.clear();
            secondVal = new ItemStack(Material.GREEN_DYE);
            meta = secondVal.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Green Transition Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleTransitionGreen().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            secondVal.setItemMeta(meta);

            lore.clear();
            thirdVal = new ItemStack(Material.BLUE_DYE);
            meta = thirdVal.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE + "Blue Transition Amount");
            lore.add(ChatColor.YELLOW + "Currently " + getSelectedItem().getParticleTransitionBlue().getCurrentMemoryValue());
            lore.add(ChatColor.YELLOW + "Click to edit!");
            meta.setLore(lore);

            thirdVal.setItemMeta(meta);
        }

        inventory.setItem(inventory.firstEmpty(), firstVal);
        inventory.setItem(inventory.firstEmpty(), secondVal);
        inventory.setItem(inventory.firstEmpty(), thirdVal);

    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 9) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {

                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

                    new ParticleNumberVariableEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), metaNoColor, this).open();
                }
            }
        }
    }
}
