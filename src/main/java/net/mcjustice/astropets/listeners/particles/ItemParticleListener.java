package net.mcjustice.astropets.listeners.particles;

import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ItemParticleListener implements Listener {

    @EventHandler
    public void onItemHold(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if (p.getInventory().getItemInMainHand().hasItemMeta()) {
            if (p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {

                String metaNoColor = ChatColor.stripColor(p.getInventory().getItemInMainHand().getItemMeta()
                        .getDisplayName());

                String metaColor = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().replace("§", "&");

                String metaTest = ChatColor.translateAlternateColorCodes('&', metaColor);

                AstroItem item = null;

                for (AstroItem ai : ItemFile.getItemsMap().values()) {

                    String colorRemoved = ChatColor.translateAlternateColorCodes('&', ai.getDisplayName().getCurrentMemoryValue());

                    if (colorRemoved.equalsIgnoreCase(metaTest)) {
                        item = ai;
                    }
                }

                if (item == null) {
                    return;
                }

                if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("NORMAL")) {
                    p.getLocation().getWorld().spawnParticle(Particle.valueOf(item.getParticleEffect().getCurrentMemoryValue().toUpperCase()),
                            p.getLocation(),
                            item.getParticleCount().getCurrentMemoryValue(),
                            item.getOffsetX().getCurrentMemoryValue(),
                            item.getOffsetY().getCurrentMemoryValue(),
                            item.getOffsetZ().getCurrentMemoryValue());
                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("DIRECTIONAL")) {
                    p.getLocation().getWorld().spawnParticle(Particle.valueOf(item.getParticleEffect().getCurrentMemoryValue().toUpperCase()),
                            p.getLocation(), 0,
                            item.getOffsetX().getCurrentMemoryValue(),
                            item.getOffsetY().getCurrentMemoryValue(),
                            item.getOffsetZ().getCurrentMemoryValue());
                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("REDSTONE")) {
                    Particle.DustOptions dustOptions = new Particle.DustOptions(
                            Color.fromRGB(item.getParticleColorRed().getCurrentMemoryValue(),
                                    item.getParticleColorGreen().getCurrentMemoryValue(),
                                    item.getParticleColorBlue().getCurrentMemoryValue()),
                            Float.parseFloat(item.getParticleSize().getCurrentMemoryValue().toString()));

                    p.getLocation().getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), item.getParticleCount().getCurrentMemoryValue(), dustOptions);
                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("MATERIAL")) {

                    if (Material.getMaterial(item.getParticleBlock().getCurrentMemoryValue()).isBlock()) {
                        BlockData data = Material.getMaterial(item.getParticleBlock().getCurrentMemoryValue()).createBlockData();

                        p.getLocation().getWorld().spawnParticle(Particle.valueOf(item.getParticleEffect().getCurrentMemoryValue().toUpperCase()), p.getLocation(), item.getParticleCount().getCurrentMemoryValue(), data);
                    } else {
                        System.out.println("The type " + item.getParticleBlock().getCurrentMemoryValue() + " is not a solid block");
                    }

                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("TRANSITIONAL")) {

                    Particle.DustTransition transition = new Particle.DustTransition(
                            Color.fromRGB(item.getParticleColorRed().getCurrentMemoryValue(),
                                    item.getParticleColorGreen().getCurrentMemoryValue(),
                                    item.getParticleColorBlue().getCurrentMemoryValue()),
                            Color.fromRGB(item.getParticleTransitionRed().getCurrentMemoryValue(),
                                    item.getParticleTransitionGreen().getCurrentMemoryValue(),
                                    item.getParticleTransitionBlue().getCurrentMemoryValue()),
                            Float.parseFloat(item.getParticleSize().getCurrentMemoryValue().toString()));

                    p.getLocation().getWorld().spawnParticle(Particle.DUST_COLOR_TRANSITION, p.getLocation(), 0, transition);
                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("VIBRATIONAL")) {

                    Vibration vibration = new Vibration(new Vibration.Destination.EntityDestination(p), item.getParticleVibrationDuration().getCurrentMemoryValue());

                    p.getLocation().getWorld().spawnParticle(Particle.VIBRATION, p.getLocation(), item.getParticleCount().getCurrentMemoryValue(), vibration);
                } else if (item.getParticleType().getCurrentMemoryValue().equalsIgnoreCase("DISABLED")) {
                    return;
                } else {
                    System.out.println("There must be an error. The type " + item.getParticleType().getCurrentMemoryValue() + " does not exist. Try Redstone, directional, transitional, material, shape, or normal.");
                }
            }
        }
    }
}
