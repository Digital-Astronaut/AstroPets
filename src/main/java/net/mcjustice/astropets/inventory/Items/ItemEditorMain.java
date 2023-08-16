package net.mcjustice.astropets.inventory.Items;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.EditorMenus.ItemAmountEditor;
import net.mcjustice.astropets.inventory.Items.EditorMenus.EnchantmentMenus.ItemEnchantmentsEditor;
import net.mcjustice.astropets.inventory.Items.EditorMenus.ItemFlagEditor;
import net.mcjustice.astropets.inventory.Items.EditorMenus.ItemMaterialEditor;
import net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus.ParticleEditorMain;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemEditorMain extends Menu {

    private AstroItem selectedItem;

    public ItemEditorMain(PlayerMenuUtility playerMenuUtility, AstroItem astroItem, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);
        setSelectedItem(astroItem);

        if (previousMenu != null) {
            setMainMenu(new ItemsMainMenu(playerMenuUtility));
        }
    }

    public AstroItem getSelectedItem() { return selectedItem; }

    public void setSelectedItem(AstroItem selectedItem) { this.selectedItem = selectedItem; }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Editing " + ChatColor.translateAlternateColorCodes('&', getSelectedItem().getDisplayNameValue());
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setMenuItems() {

        System.out.println(getSelectedItem().getItemResultAmount().getCurrentMemoryValue());
        System.out.println(getSelectedItem().getItemResultMaterial().getCurrentMemoryValue());

        ItemStack editItem = new ItemStack(Material.getMaterial(getSelectedItem().getItemResultMaterial().getCurrentMemoryValue().toUpperCase()), getSelectedItem().getItemResultAmount().getCurrentMemoryValue());

        ItemStack editEnchants = new ItemStack(Material.ENCHANTED_BOOK, 1);

        ItemStack editItemFlags = new ItemStack(Material.WHITE_BANNER, 1);

        ItemStack editRecipe = new ItemStack(Material.CRAFTING_TABLE, 1);

        ItemStack editParticles = new ItemStack(Material.REDSTONE, 1);

        ItemStack editSounds = new ItemStack(Material.NOTE_BLOCK, 1);

        List<String> lore = new ArrayList<>();

        ItemMeta meta = editItem.getItemMeta();

        String click = "Click to edit this item's ";

        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Display Item");
        lore.add(ChatColor.YELLOW + "Left " + click.toLowerCase() + " display item");
        lore.add(ChatColor.YELLOW + "Right click to edit this item's amount");
        meta.setLore(lore);
        editItem.setItemMeta(meta);

        lore.clear();
        meta = editEnchants.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Enchantments");
        lore.add(ChatColor.YELLOW + click + "enchantments");
        meta.setLore(lore);
        editEnchants.setItemMeta(meta);

        lore.clear();
        meta = editItemFlags.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Item Flags");
        lore.add(ChatColor.YELLOW + click + "item flags");
        meta.setLore(lore);
        editItemFlags.setItemMeta(meta);

        lore.clear();
        meta = editRecipe.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Recipe");
        lore.add(ChatColor.YELLOW + click + "crafting recipe");
        meta.setLore(lore);
        editRecipe.setItemMeta(meta);

        lore.clear();
        meta = editParticles.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Particles");
        lore.add(ChatColor.YELLOW + click + "particles");
        meta.setLore(lore);
        editParticles.setItemMeta(meta);

        lore.clear();
        meta = editSounds.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Sounds");
        lore.add(ChatColor.YELLOW + click + "sounds");
        meta.setLore(lore);
        editSounds.setItemMeta(meta);

        inventory.setItem(11, editItem);
        inventory.setItem(13, editEnchants);
        inventory.setItem(15, editItemFlags);
        inventory.setItem(29, editRecipe);
        inventory.setItem(31, editParticles);
        inventory.setItem(33, editSounds);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() == 11) {
            if (e.getClick() == ClickType.LEFT)

                new ItemMaterialEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
            else if (e.getClick() == ClickType.RIGHT) {

                new ItemAmountEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
            }
        } else if (e.getRawSlot() == 13) {

            new ItemEnchantmentsEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
        } else if (e.getRawSlot() == 15) {

            new ItemFlagEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();

        } else if (e.getRawSlot() == 31) {

            new ParticleEditorMain(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
        }
    }
}
