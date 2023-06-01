package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ParticleEditorMain extends Menu {

    AstroItem selectedItem;

    public ParticleEditorMain(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, @Nullable Menu previousMenu) {
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
        return ChatColor.BLUE + "" + ChatColor.BOLD + "Particle Editor";
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void setMenuItems() {

        List<String> lore = new ArrayList<>();

        ItemStack[] allItems = new ItemStack[7];

        allItems[0] = new ItemStack(Material.ALLIUM);
        ItemMeta meta = allItems[0].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Normal Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[0].setItemMeta(meta);

        allItems[1] = new ItemStack(Material.OAK_SIGN);
        meta = allItems[1].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Directional Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[1].setItemMeta(meta);

        allItems[2] = new ItemStack(Material.REDSTONE);
        meta = allItems[2].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Redstone Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[2].setItemMeta(meta);

        allItems[3] = new ItemStack(Material.STONE);
        meta = allItems[3].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Material Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[3].setItemMeta(meta);

        allItems[4] = new ItemStack(Material.GREEN_DYE);
        meta = allItems[4].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Transitional Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[4].setItemMeta(meta);


        allItems[5] = new ItemStack(Material.NOTE_BLOCK);
        meta = allItems[5].getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Vibrational Particle");
        lore.add(ChatColor.YELLOW + "Click to set this as the particle type");
        allItems[5].setItemMeta(meta);

        allItems[6] = new ItemStack(Material.BARRIER);
        meta = allItems[6].getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Disable Particle");
        allItems[6].setItemMeta(meta);

        for (ItemStack is : allItems) {

            String metaNoColor = ChatColor.stripColor(is.getItemMeta().getDisplayName().replace(" Particle", "").toUpperCase());

            if (metaNoColor.equalsIgnoreCase("DISABLE")) {
                metaNoColor = metaNoColor + "D";

            }

            if (metaNoColor.equalsIgnoreCase(getSelectedItem().getParticleType().getCurrentMemoryValue())) {
                meta = is.getItemMeta();
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                lore.clear();
                lore.add(ChatColor.GREEN + "Currently selected");
                meta.setLore(lore);
                is.setItemMeta(meta);
            }
        }
        inventory.setItem(0, allItems[0]);
        inventory.setItem(4, allItems[1]);
        inventory.setItem(8, allItems[2]);
        inventory.setItem(11, allItems[3]);
        inventory.setItem(15, allItems[4]);
        inventory.setItem(22, allItems[5]);
        inventory.setItem(37, allItems[6]);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 36) {

            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    String metaNoColor = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace(" Particle", "").toUpperCase());

                    if (!getSelectedItem().getParticleType().getCurrentMemoryValue().equalsIgnoreCase(metaNoColor)) {
                        getSelectedItem().getParticleType().setCurrentMemoryValue(metaNoColor);
                        // add update particle effect here
                    }
                    updateParticleEffect(getSelectedItem().getParticleType().getCurrentMemoryValue());

                    System.out.println("Particle type set to " + getSelectedItem().getParticleType().getCurrentMemoryValue() + ". Effect set to " + getSelectedItem().getParticleEffect().getCurrentMemoryValue());
                    new ParticleTypeEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), getSelectedItem(), this).open();
                }
            }
        } else if (e.getRawSlot() == 37) {
            getSelectedItem().getParticleType().setCurrentMemoryValue("DISABLED");
            open();
        }
    }

    // TODO: Fix changing Particle Type in file breaking this.
    public void updateParticleEffect(String particleType) {

        switch(particleType) {
            case "NORMAL", "DIRECTIONAL":
                getSelectedItem().getParticleEffect().setCurrentMemoryValue("EXPLOSION_NORMAL");
                break;
            case "VIBRATIONAL":
                getSelectedItem().getParticleEffect().setCurrentMemoryValue("VIBRATION");
                break;
            case "REDSTONE":
                getSelectedItem().getParticleEffect().setCurrentMemoryValue("REDSTONE");
                break;
            case "TRANSITIONAL":
                getSelectedItem().getParticleEffect().setCurrentMemoryValue("DUST_COLOR_TRANSITION");
                break;
            case "MATERIAL":
                getSelectedItem().getParticleEffect().setCurrentMemoryValue("BLOCK_DUST");
                break;
        }
    }
}
