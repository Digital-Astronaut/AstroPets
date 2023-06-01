package net.mcjustice.astropets.listeners.enchantments;

import net.mcjustice.astropets.AstroPets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TreecapitatorEvent implements Listener {

    int totalDamageToReduceBy;

    @EventHandler
    public void onPlayerBreakWood(BlockBreakEvent e) {

        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {

            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.getByKey(NamespacedKey.fromString("treecapitator", AstroPets.getPlugin())))) {

                if ((b.getType() == Material.SPRUCE_LOG) || (b.getType() == Material.DARK_OAK_LOG)
                        || (b.getType() == Material.OAK_LOG) || (b.getType() == Material.ACACIA_LOG)
                        || (b.getType() == Material.BIRCH_LOG) || (b.getType() == Material.JUNGLE_LOG)
                        || (b.getType() == Material.STRIPPED_ACACIA_LOG) || (b.getType() == Material.STRIPPED_BIRCH_LOG)
                        || (b.getType() == Material.STRIPPED_DARK_OAK_LOG) || (b.getType() == Material.STRIPPED_JUNGLE_LOG)
                        || (b.getType() == Material.STRIPPED_OAK_LOG) || (b.getType() == Material.STRIPPED_SPRUCE_LOG)) {
                    if (p.isSneaking()) {

                        totalDamageToReduceBy = 0;

                        breakBlock(b, p);

                        ItemStack treeCap = e.getPlayer().getInventory().getItemInMainHand();

                        ItemMeta meta = treeCap.getItemMeta();

                        if (meta instanceof Damageable dMeta) {

                            int damage = dMeta.getDamage();

                            System.out.println(totalDamageToReduceBy);

                            dMeta.setDamage(damage + totalDamageToReduceBy);

                            treeCap.setItemMeta(dMeta);

                        }
                    }
                    return;
                }
            }
        }
    }

    public void breakBlock(Block bl, Player p) {

        bl.breakNaturally();

        totalDamageToReduceBy++;

        Location e = new Location(bl.getWorld(), bl.getLocation().getBlockX(), (bl.getLocation().getBlockY() + 1),
                bl.getLocation().getBlockZ());
        ;
        Block b = e.getBlock();
        if ((b.getType() == Material.SPRUCE_LOG) || (b.getType() == Material.DARK_OAK_LOG)
                || (b.getType() == Material.OAK_LOG) || (b.getType() == Material.ACACIA_LOG)
                || (b.getType() == Material.BIRCH_LOG) || (b.getType() == Material.JUNGLE_LOG)
                || (b.getType() == Material.STRIPPED_ACACIA_LOG) || (b.getType() == Material.STRIPPED_BIRCH_LOG)
                || (b.getType() == Material.STRIPPED_DARK_OAK_LOG) || (b.getType() == Material.STRIPPED_JUNGLE_LOG)
                || (b.getType() == Material.STRIPPED_OAK_LOG) || (b.getType() == Material.STRIPPED_SPRUCE_LOG)) {
            breakBlock(b, p);
        }
    }
}
