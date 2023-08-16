package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus.InventoryContents;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
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

public class MobInventoryContentsEditor extends Menu {

    private AstroMob selectedMob;

    public MobInventoryContentsEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, @Nullable Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD + ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', selectedMob.getMobDisplayName().getCurrentMemoryValue())) + " Inventory";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {

        ItemStack helmet;
        ItemStack offHand;
        ItemStack chestplate;
        ItemStack mainHand;
        ItemStack leggings;
        ItemStack boots;

        ItemMeta meta;

        List<String> lore = new ArrayList<>();

        if (selectedMob.getMobHelmetEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobHelmetItem().getCurrentMemoryValue()) != null) {

                helmet = new ItemStack(Material.valueOf(selectedMob.getMobHelmetItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidHelmetsAndCustomHelmets().contains(selectedMob.getMobHelmetItem().getCurrentMemoryValue().toUpperCase())) {
                helmet = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobHelmetItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                helmet = new ItemStack(Material.valueOf(selectedMob.getMobHelmetItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's helmet");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's helmet");

            meta = helmet.getItemMeta();
        } else {
            helmet = new ItemStack(Material.BARRIER);
            meta = helmet.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Helmet currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable a helmet for this mob");
        }
        meta.setLore(lore);
        helmet.setItemMeta(meta);

        lore.clear();

        if (selectedMob.getMobOffHandEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobOffHandItem().getCurrentMemoryValue()) != null) {

                offHand = new ItemStack(Material.valueOf(selectedMob.getMobOffHandItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidMatsAndEnabledItems().contains(selectedMob.getMobOffHandItem().getCurrentMemoryValue().toUpperCase())) {
                offHand = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobOffHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                offHand = new ItemStack(Material.valueOf(selectedMob.getMobOffHandItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's off hand item");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's off hand item");

            meta = offHand.getItemMeta();
        } else {
            offHand = new ItemStack(Material.BARRIER);
            meta = offHand.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Off hand item currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable an off hand item for this mob");
        }
        meta.setLore(lore);
        offHand.setItemMeta(meta);

        lore.clear();

        if (selectedMob.getMobChestplateEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobChestplateItem().getCurrentMemoryValue()) != null) {

                chestplate = new ItemStack(Material.valueOf(selectedMob.getMobChestplateItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidChestplatesAndCustomChestplates().contains(selectedMob.getMobChestplateItem().getCurrentMemoryValue().toUpperCase())) {
                chestplate = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobChestplateItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                chestplate = new ItemStack(Material.valueOf(selectedMob.getMobChestplateItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's chestplate");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's chestplate");

            meta = chestplate.getItemMeta();
        } else {
            chestplate = new ItemStack(Material.BARRIER);
            meta = chestplate.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Chestplate currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable a chestplate for this mob");
        }
        meta.setLore(lore);
        chestplate.setItemMeta(meta);

        lore.clear();

        if (selectedMob.getMobMainHandEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobMainHandItem().getCurrentMemoryValue()) != null) {

                mainHand = new ItemStack(Material.valueOf(selectedMob.getMobMainHandItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidMatsAndEnabledItems().contains(selectedMob.getMobMainHandItem().getCurrentMemoryValue().toUpperCase())) {
                mainHand = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobMainHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                mainHand = new ItemStack(Material.valueOf(selectedMob.getMobMainHandItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's main hand item");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's main hand item");

            meta = mainHand.getItemMeta();
        } else {
            mainHand = new ItemStack(Material.BARRIER);
            meta = mainHand.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Main hand item currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable a main hand item for this mob");
        }
        meta.setLore(lore);
        mainHand.setItemMeta(meta);

        lore.clear();

        if (selectedMob.getMobLeggingsEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobLeggingsItem().getCurrentMemoryValue()) != null) {

                leggings = new ItemStack(Material.valueOf(selectedMob.getMobLeggingsItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(selectedMob.getMobLeggingsItem().getCurrentMemoryValue().toUpperCase())) {
                leggings = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobLeggingsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                leggings = new ItemStack(Material.valueOf(selectedMob.getMobLeggingsItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's leggings");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's leggings");

            meta = leggings.getItemMeta();
        } else {
            leggings = new ItemStack(Material.BARRIER);
            meta = leggings.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Leggings currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable leggings for this mob");
        }
        meta.setLore(lore);
        leggings.setItemMeta(meta);

        lore.clear();

        if (selectedMob.getMobBootsEnabled().getCurrentMemoryValue()) {
            if (Material.matchMaterial(selectedMob.getMobBootsItem().getCurrentMemoryValue()) != null) {

                boots = new ItemStack(Material.valueOf(selectedMob.getMobBootsItem().getCurrentMemoryValue()));
            } else if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(selectedMob.getMobBootsItem().getCurrentMemoryValue().toUpperCase())) {
                boots = ItemFile.getItemsMap().get(FileUtils.checkForYML(selectedMob.getMobBootsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin());
            } else {
                boots = new ItemStack(Material.valueOf(selectedMob.getMobBootsItem().getDefaultValue()));
            }
            lore.add(ChatColor.YELLOW + "Left click to change this mob's boots");
            lore.add(ChatColor.YELLOW + "Right click to remove this mob's boots");

            meta = leggings.getItemMeta();
        } else {
            boots = new ItemStack(Material.BARRIER);
            meta = boots.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "Boots currently disabled");
            lore.add(ChatColor.YELLOW + "Click to enable boots for this mob");
        }
        meta.setLore(lore);
        boots.setItemMeta(meta);

        lore.clear();

        inventory.setItem(13, helmet);
        inventory.setItem(21, offHand);
        inventory.setItem(22, chestplate);
        inventory.setItem(23, mainHand);
        inventory.setItem(31, leggings);
        inventory.setItem(40, boots);

    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {
            if (e.getRawSlot() == 13) {
                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobHelmetEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobHelmetEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobHelmetEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobHelmetEnabled().setCurrentMemoryValue(false);
                    open();
                }
            } else if (e.getRawSlot() == 21) {

                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobOffHandEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobOffHandEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobOffHandEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobOffHandEnabled().setCurrentMemoryValue(false);
                    open();
                }

            } else if (e.getRawSlot() == 22) {
                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobChestplateEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobChestplateEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobChestplateEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobChestplateEnabled().setCurrentMemoryValue(false);
                    open();
                }
            } else if (e.getRawSlot() == 23) {

                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobMainHandEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobMainHandEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobMainHandEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobMainHandEnabled().setCurrentMemoryValue(false);
                    open();
                }

            } else if (e.getRawSlot() == 31) {
                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobLeggingsEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobLeggingsEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobLeggingsEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobLeggingsEnabled().setCurrentMemoryValue(false);
                    open();
                }
            } else if (e.getRawSlot() == 40) {
                if (e.getClick() == ClickType.LEFT) {
                    if (!selectedMob.getMobBootsEnabled().getCurrentMemoryValue()) {
                        selectedMob.getMobBootsEnabled().setCurrentMemoryValue(true);
                        open();
                    } else {
                        new MobBootsEditor(MenuManager.getPlayerMenuUtility((Player) e.getWhoClicked()), selectedMob, this).open();
                    }
                } else if (e.getClick() == ClickType.RIGHT) {
                    selectedMob.getMobBootsEnabled().setCurrentMemoryValue(false);
                    open();
                }
            }
        }
    }
}
