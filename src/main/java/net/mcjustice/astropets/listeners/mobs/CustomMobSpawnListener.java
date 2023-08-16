package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomMobSpawnListener implements Listener {

    @EventHandler
    public void onCustomMobSpawn(CreatureSpawnEvent e) {

        AstroMobUtils.spawnMobBasedOnChance(e.getEntity());
    }
}
