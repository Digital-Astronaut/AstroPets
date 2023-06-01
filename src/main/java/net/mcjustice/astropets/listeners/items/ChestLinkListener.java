package net.mcjustice.astropets.listeners.items;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.ItemFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChestLinkListener implements Listener {

    @EventHandler
    public void onPlayerLink(PlayerInteractEvent e) {

        if (!e.getPlayer().isSneaking()) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getClickedBlock() != null) {

                ItemStack i = e.getItem();

                ItemMeta meta = i.getItemMeta();

                String metaColor = i.getItemMeta().getDisplayName().replace("§", "&");

                String itemFileDisplayName = ChatColor.translateAlternateColorCodes('&', ItemFile.getItemsMap().get(FileUtils.checkForYML("chest_link").toUpperCase()).getDisplayName().getCurrentMemoryValue());

                if (i.getType().equals(ItemFile.getItemsMap().get(FileUtils.checkForYML("chest_link").toUpperCase()).getItemStack(AstroPets.getPlugin()).getType()) && i.hasItemMeta()) {

                    if (ChatColor.translateAlternateColorCodes('&', metaColor).equalsIgnoreCase(itemFileDisplayName)) {
                        if (e.getClickedBlock().getType() == Material.CHEST) {
                            Block b = e.getClickedBlock();
                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(ChatColor.BLUE + "Your linked chest is located at ");
                            lore.add(ChatColor.YELLOW + "" + b.getX() + ", " + b.getY() + ", " + b.getZ());
                            meta.setLore(lore);
                            i.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerOpen(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getItem() != null) {

                ItemStack i = e.getItem();

                ItemMeta meta = i.getItemMeta();

                String metaColor = i.getItemMeta().getDisplayName().replace("§", "&");

                String itemFileDisplayName = ChatColor.translateAlternateColorCodes('&', ItemFile.getItemsMap().get(FileUtils.checkForYML("chest_link").toUpperCase()).getDisplayName().getCurrentMemoryValue());

                if (i.getType().equals(ItemFile.getItemsMap().get(FileUtils.checkForYML("chest_link").toUpperCase()).getItemStack(AstroPets.getPlugin()).getType()) && i.hasItemMeta()) {

                    if (meta != null && meta.getLore() != null) {

                        if (ChatColor.translateAlternateColorCodes('&', metaColor).equalsIgnoreCase(itemFileDisplayName)) {

                            if (!meta.getLore().contains(ChatColor.stripColor("Shift click on a chest to link its inventory!"))) {

                                List<String> lore = meta.getLore();

                                if (lore.get(1) == null) {
                                    return;
                                }

                                String[] locationXYZ = lore.get(1).split(", ");
                                int x = Integer.parseInt(ChatColor.stripColor(locationXYZ[0]));
                                int y = Integer.parseInt(ChatColor.stripColor(locationXYZ[1]));
                                int z = Integer.parseInt(ChatColor.stripColor(locationXYZ[2]));
                                BlockState bs = e.getPlayer().getWorld().getBlockAt(x, y, z).getState();

                                if (bs.getBlock().getType() == Material.AIR || bs.getBlock().getType() != Material.CHEST) {
                                    e.getPlayer().sendMessage(ChatColor.RED + "Your linked chest has been broken!");
                                    lore.removeAll(lore);
                                    lore.add(ChatColor.BLUE + "Shift click on a chest to link its inventory!");
                                    meta.setLore(lore);
                                    i.setItemMeta(meta);
                                    return;
                                }
                                try {
                                    Chest c = (Chest) bs;
                                    e.getPlayer().openInventory(c.getInventory());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent e) {

        if (e.getConsumable().hasItemMeta()) {

            if (e.getConsumable().getItemMeta().hasDisplayName()) {

                String metaColor = e.getConsumable().getItemMeta().getDisplayName().replace("§", "&");

                String itemFileDisplayName = ChatColor.translateAlternateColorCodes('&', ItemFile.getItemsMap().get(FileUtils.checkForYML("chest_link").toUpperCase()).getDisplayName().getCurrentMemoryValue());

                if (e.getEntity() instanceof Player p) {

                    if (ChatColor.translateAlternateColorCodes('&', metaColor).equalsIgnoreCase(itemFileDisplayName)) {
                        System.out.println(metaColor + " matches " + itemFileDisplayName);
//                        e.setCancelled(true);
                        e.setConsumeItem(false);
                        p.updateInventory();

//                        if (p.getInventory().contains(Material.ARROW)) {
//
//                            if (e.getBow().hasItemMeta()) {
//                                if (e.getBow().getItemMeta().hasEnchants()) {
//                                    if (e.getBow().getItemMeta().hasEnchant(Enchantment.ARROW_INFINITE)) {
//
//                                    }
//                                }
//                            }
//
//                        }

                        e.setCancelled(true);
                    }
                }
            }
        }

    }
}
