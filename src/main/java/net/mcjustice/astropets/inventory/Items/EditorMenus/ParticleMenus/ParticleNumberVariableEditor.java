package net.mcjustice.astropets.inventory.Items.EditorMenus.ParticleMenus;

import net.mcjustice.astroapi.Inventory.Menu;
import net.mcjustice.astroapi.Inventory.PaginatedMenu;
import net.mcjustice.astroapi.Utils.PlayerMenuUtility;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParticleNumberVariableEditor extends PaginatedMenu {

    private String floatView = "Non-Specific";
    private String intView;

    private float particleSize = 1.0f;

    private AstroItem selectedItem;

    public ParticleNumberVariableEditor(PlayerMenuUtility playerMenuUtility, AstroItem selectedItem, String intView, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        setSelectedItem(selectedItem);
        this.intView = intView;

        setMainMenu(new ItemsMainMenu(playerMenuUtility));
    }

    public AstroItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(AstroItem selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public List<?> getData() {
        List<Material> mat = new ArrayList<>();

        for (int i = 0; i < getMaxAmountOfDisplayedItems(); i++) {

            mat.add(getDisplayMaterial());
        }
        return mat;
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof Material mat) {

            ItemStack is = new ItemStack(mat);

            List<String> lore = new ArrayList<>();
            ItemMeta meta = is.getItemMeta();

            if (getMaxAmountOfDisplayedItems() < 64) {
                is.setAmount(index + 1);
            }

            if (intView.equalsIgnoreCase("Vibration Duration")) {
                if (is.getAmount() == 1) {
                    meta.setDisplayName(ChatColor.AQUA + "" + is.getAmount() + " second");
                } else {
                    meta.setDisplayName(ChatColor.AQUA + "" + is.getAmount() + " seconds");
                }
            } else {
                meta.setDisplayName(ChatColor.AQUA + "" + (index + 1));
            }

            String metaNoColor = ChatColor.stripColor(meta.getDisplayName());

            if (metaNoColor.contains(" second")) {
                metaNoColor = metaNoColor.replace(" second", "").replace("s", "");
            }

            if (Integer.parseInt(metaNoColor) == getNumberParameterAmount()) {
                lore.add(ChatColor.GREEN + "Current amount");
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add(ChatColor.YELLOW + "Click to set the amount to " + (index + 1));
            }

            meta.setLore(lore);
            is.setItemMeta(meta);

            inventory.setItem(inventory.firstEmpty(), is);
        }
    }

    // TODO: Grab the value's max amount
    @Override
    public int getMaxAmountOfDisplayedItems() {
        switch (intView) {
            case "Particle Count", "Particle Size":
                return 45;
            case "Red Amount", "Blue Amount", "Green Amount", "Red Transition Amount", "Green Transition Amount", "Blue Transition Amount":
                return 255;
            case "X Offset", "Y Offset", "Z Offset":
                return 25;
            case "Vibration Duration":
                return 10;
            default:
                return 20;
        }
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + intView;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getRawSlot() < 45) {
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName()) {
                    int amount = 0;

                    if (!intView.equalsIgnoreCase("Vibration Duration")) {

                        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous Menu"))
                            return;

                        amount = Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                    }

                    if (intView.equalsIgnoreCase("Particle Size")) {

                        getSelectedItem().getParticleSize().setCurrentMemoryValue(Double.parseDouble(String.valueOf(e.getCurrentItem().getAmount())));
                    } else if (intView.equalsIgnoreCase("Particle Count")) {
                        getSelectedItem().getParticleCount().setCurrentMemoryValue(e.getCurrentItem().getAmount());
                    } else if (intView.equalsIgnoreCase("Red Amount")) {
                        getSelectedItem().getParticleColorRed().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Green Amount")) {
                        getSelectedItem().getParticleColorGreen().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Blue Amount")) {
                        getSelectedItem().getParticleColorBlue().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Red Transition Amount")) {
                        getSelectedItem().getParticleTransitionRed().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Green Transition Amount")) {
                        getSelectedItem().getParticleTransitionGreen().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Blue Transition Amount")) {
                        getSelectedItem().getParticleTransitionBlue().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("X Offset")) {
                        getSelectedItem().getOffsetX().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Y Offset")) {
                        getSelectedItem().getOffsetY().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Z Offset")) {
                        getSelectedItem().getOffsetZ().setCurrentMemoryValue(amount);
                    } else if (intView.equalsIgnoreCase("Vibration Duration")) {
                        getSelectedItem().getParticleVibrationDuration().setCurrentMemoryValue(e.getCurrentItem().getAmount());
                    }

                    open();
                }
            }
        }
    }

    public int getNumberParameterAmount() {
        switch (intView) {
            case "Particle Count":
                return getSelectedItem().getParticleCount().getCurrentMemoryValue();

            case "Particle Size":
                return (int) getSelectedItem().getParticleSize().getCurrentMemoryValue().intValue();

            case "Red Amount":
                return getSelectedItem().getParticleColorRed().getCurrentMemoryValue();

            case "Green Amount":
                return getSelectedItem().getParticleColorGreen().getCurrentMemoryValue();

            case "Blue Amount":
                return getSelectedItem().getParticleColorBlue().getCurrentMemoryValue();

            case "Red Transition Amount":
                return getSelectedItem().getParticleTransitionRed().getCurrentMemoryValue();

            case "Green Transition Amount":
                return getSelectedItem().getParticleTransitionGreen().getCurrentMemoryValue();

            case "Blue Transition Amount":
                return getSelectedItem().getParticleTransitionBlue().getCurrentMemoryValue();

            case "Vibration Duration":
                return getSelectedItem().getParticleVibrationDuration().getCurrentMemoryValue();

            case "X Offset":
                return getSelectedItem().getOffsetX().getCurrentMemoryValue();

            case "Y Offset":
                return getSelectedItem().getOffsetY().getCurrentMemoryValue();

            case "Z Offset":
                return getSelectedItem().getOffsetZ().getCurrentMemoryValue();
            default:
                return 0;
        }
    }

    public Material getDisplayMaterial() {

        switch (intView) {
            case "Particle Count", "Particle Size", "Vibration Duration", "X Offset", "Y Offset", "Z Offset":
                return Material.EMERALD;
            case "Red Amount", "Red Transition Amount":
                return Material.RED_DYE;
            case "Green Amount", "Green Transition Amount":
                return Material.GREEN_DYE;
            case "Blue Amount", "Blue Transition Amount":
                return Material.BLUE_DYE;
            default:
                return Material.STONE;
        }
    }
}
