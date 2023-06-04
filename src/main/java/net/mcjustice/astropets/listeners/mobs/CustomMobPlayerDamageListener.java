package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class CustomMobPlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player p) {

            Entity entity;

            if (e.getDamager() instanceof Projectile proj) {

                entity = (Entity) proj.getShooter();

            } else {
                entity = e.getDamager();
            }

            if (entity.getCustomName() != null) {

                PersistentDataContainer container = e.getEntity().getPersistentDataContainer();

                NamespacedKey key = new NamespacedKey(AstroPets.getPlugin(), "entity_file_name");

                if (container.has(key, PersistentDataType.STRING)) {

                    AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(e.getEntity().getType().toString(), container.get(key, PersistentDataType.STRING).toUpperCase());


                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', AstroMobUtils.getRandomQuote(mob.getQuotesOnAttack().getCurrentMemoryValue(), "<Player>", p.getName())));
                }
            }
        }
    }
}
