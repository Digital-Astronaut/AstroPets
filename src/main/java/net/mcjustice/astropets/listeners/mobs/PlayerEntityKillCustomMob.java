package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class PlayerEntityKillCustomMob implements Listener {

    @EventHandler
    public void onCustomMobKill(EntityDeathEvent e) {

        Entity entity = e.getEntity();

        if (entity.getCustomName() != null) {

            PersistentDataContainer container = e.getEntity().getPersistentDataContainer();

            NamespacedKey key = new NamespacedKey(AstroPets.getPlugin(), "entity_file_name");

            if (container.has(key, PersistentDataType.STRING)) {

                AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(e.getEntity().getType().toString(), container.get(key, PersistentDataType.STRING));

                Random r = new Random();

//                if (!(mob.getMobDrops().getCurrentMemoryValue().isEmpty()) && mob.getMobDrops().getCurrentMemoryValue().size() != 0) {
//
//                    Object[] drops = mob.getMobDrops().getCurrentMemoryValue().keySet().toArray();
//
//                    String output = drops[r.nextInt(drops.length)].toString().replace("[", "").replace("]", "");
//
//                    System.out.println(output);
//
//                    ItemStack drop;
//
//                    if (ItemFile.itemsMapContainsItem(output.toUpperCase())) {
//                        drop = ItemFile.getItemsMap().get(FileUtils.checkForYML(output).toUpperCase()).getItemStack(AstroPets.getPlugin());
//                    } else {
//                        drop = new ItemStack(Material.valueOf(output.toUpperCase()));
//                    }
//
//                    int amount = (int) (Math.random() * mob.getMobDrops().getCurrentMemoryValue().get(output));
//
//                    while (amount <= 0) {
//
//                        amount = (int) (Math.random() * mob.getMobDrops().getCurrentMemoryValue().get(output));
//                    }
//
//                    drop.setAmount(amount);
//
//                    entity.getWorld().dropItemNaturally(entity.getLocation(), drop);
//                }
            }
        }
    }
}
