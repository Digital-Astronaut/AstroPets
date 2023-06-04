package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;

public class CustomMobTargetListener implements Listener {

    private String previousText;

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent e) {

        if (e.getTarget() != null) {

            if (e.getTarget() instanceof Player p) {

                if (e.getEntity().getCustomName() != null) {

                    PersistentDataContainer container = e.getEntity().getPersistentDataContainer();

                    NamespacedKey key = new NamespacedKey(AstroPets.getPlugin(), "entity_file_name");

                    if (container.has(key, PersistentDataType.STRING)) {

                        AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(e.getEntity().getType().toString(), container.get(key, PersistentDataType.STRING).toUpperCase());

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', AstroMobUtils.getRandomQuote(mob.getQuotesOnTarget().getCurrentMemoryValue(), "<Player>", p.getName())));
                    }
                }
            } else {

                if (e.getEntity().getCustomName() != null) {

                    PersistentDataContainer container = e.getEntity().getPersistentDataContainer();

                    NamespacedKey key = new NamespacedKey(AstroPets.getPlugin(), "entity_file_name");

                    if (container.has(key, PersistentDataType.STRING)) {

                        AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(e.getEntity().getType().toString(), container.get(key, PersistentDataType.STRING).toUpperCase());

                        if (mob.getWhitelistedMobs().getCurrentMemoryValue().contains(e.getTarget().getType().toString())) {
                            e.setCancelled(true);
                        }
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Mob doesn't have PDC");
                    }
                }
            }
        }
    }
}
