package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.PetFile;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;

public class CustomMobInteractEvent implements Listener {

    private String previousText;

    @EventHandler
    public void onCustomMobInteract(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

        Entity entity = e.getRightClicked();

        if (entity.getCustomName() != null) {

            if (e.getHand() == EquipmentSlot.OFF_HAND) {

                PersistentDataContainer container = entity.getPersistentDataContainer();

                NamespacedKey key = new NamespacedKey(AstroPets.getPlugin(), "entity_file_name");

                if (container.has(key, PersistentDataType.STRING)) {

                    AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(entity.getType().toString(), container.get(key, PersistentDataType.STRING).toUpperCase());

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', AstroMobUtils.getRandomQuote(mob.getQuotesOnRightClick().getCurrentMemoryValue(), "<Player>", p.getName())));

//                    String output;
//
//                    if (mob.getQuotesOnRightClick().getCurrentMemoryValue() != null && mob.getQuotesOnRightClick().size() != 0) {
//
//                        List<String> test = mob.getQuotesOnRightClick().getCurrentMemoryValue();
//
//                        System.out.println(test);
//
//                        int i = (int) (Math.random() * test.size());
//
//                        output = test.get(i);
//
//                        if (previousText != null) {
//
//                            if (output.equalsIgnoreCase(previousText)) {
//
//                                output = test.get(i);
//                            }
//                        }
//                        if (output.contains("<Player>")) {
//                            output = output.replace("<Player>", p.getName());
//                        }
//                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', output));
//
//                        previousText = output;
//                    }
                }
            }
        }
    }
}
