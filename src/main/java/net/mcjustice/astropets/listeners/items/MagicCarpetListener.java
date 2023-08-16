package net.mcjustice.astropets.listeners.items;

import net.mcjustice.astropets.AstroPets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MagicCarpetListener implements Listener {

    @EventHandler
    public void onMagicCarpetRide(PlayerMoveEvent e) {

        Player p = e.getPlayer();

//        if (p.isFlying()) {
//
//            double test = p.getVelocity().getY() - 1.0;
//
//            Location loc = p.getLocation();
//
//            Block b = p.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ());
//
//            Material type = b.getType();
//
//            b.setType(Material.EMERALD_BLOCK);
//
//            Bukkit.getScheduler().runTaskLater(AstroPets.getPlugin(), () -> b.setType(type), 10L);
//        }
    }
}
