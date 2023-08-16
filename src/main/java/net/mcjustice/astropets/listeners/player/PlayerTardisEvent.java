package net.mcjustice.astropets.listeners.player;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerTardisEvent implements Listener {

    @EventHandler
    public void playerEnterTardisEvent(PlayerMoveEvent e) {

        Location tardisEnter = new Location(e.getPlayer().getWorld(), 131, 34, -250, 90, -1.5f);

        if (((int) e.getPlayer().getLocation().getX()) == 118 && ((int) e.getPlayer().getLocation().getY()) == 80 && ((int) e.getPlayer().getLocation().getZ()) == -329) {
            e.getPlayer().teleport(tardisEnter);
            System.out.println("Entering " + e.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void playerLeaveTardisEvent(PlayerMoveEvent e) {

        Location tardisLeave = new Location(e.getPlayer().getWorld(), 118, 80, -330, -180f, 0f);

        if (((int) e.getPlayer().getLocation().getX()) == 132 && ((int) e.getPlayer().getLocation().getY()) == 34 && ((int) e.getPlayer().getLocation().getZ()) == -250) {

            e.getPlayer().teleport(tardisLeave);
            System.out.println("Entering " + e.getPlayer().getLocation());
        }
    }
}
