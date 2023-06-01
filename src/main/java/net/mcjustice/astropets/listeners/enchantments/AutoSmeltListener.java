package net.mcjustice.astropets.listeners.enchantments;

import net.mcjustice.astropets.AstroPets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AutoSmeltListener implements Listener {

    @EventHandler
    public void onPlayerBreakOre(BlockBreakEvent e) {
        Block b = e.getBlock();
        Player p = e.getPlayer();

        if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {

            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.getByKey(NamespacedKey.fromString("auto_smelt", AstroPets.getPlugin())))) {

                if ((b.getType() == Material.IRON_ORE) || (b.getType() == Material.COPPER_ORE)
                        || (b.getType() == Material.DEEPSLATE_COPPER_ORE)
                        || (b.getType() == Material.DEEPSLATE_GOLD_ORE)
                        || (b.getType() == Material.DEEPSLATE_IRON_ORE)
                        || (b.getType() == Material.GOLD_ORE)
                        || (b.getType() == Material.NETHER_GOLD_ORE)) {

                    int xp = e.getExpToDrop();

                    breakBlock(b, p, xp);
                }
                return;
            }
        }
    }

    // TODO: We can use treecapitator method to make a vein miner plugin.
    // TODO: Add check for fortune/luck!
    public void breakBlock(Block bl, Player p, int xp) {

        String ingot = bl.getType().name().replace("_ORE", "_INGOT").replace("DEEPSLATE_", "").replace("NETHER_", "");

        Location e = new Location(bl.getWorld(), bl.getLocation().getBlockX(), (bl.getLocation().getBlockY() + 1),
                bl.getLocation().getBlockZ());

        bl.setType(Material.AIR);
        bl.getWorld().dropItem(bl.getLocation(), new ItemStack(Material.valueOf(ingot), 1));
        p.getWorld().spawn(p.getLocation().add(1,0,0), ExperienceOrb.class).setExperience(xp);
    }
}
