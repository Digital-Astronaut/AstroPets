package net.mcjustice.astropets.listeners.mobs;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astroapi.Utils.MaterialUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroMobUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.Random;

public class CustomMobSpawnListener implements Listener {

    @EventHandler
    public void onCustomMobSpawn(CreatureSpawnEvent e) {

        AstroMobUtils.spawnMobBasedOnChance(e.getEntity());

//        LivingEntity entity = e.getEntity();
//
//        File file = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Mobs" + File.separator + entity.getType());
//
//        File[] files = file.listFiles();
//
//        assert files != null;
//        if (files.length != 0) {
//
//            Random r = new Random();
//
//            int chance = r.nextInt(100) + 1;
//
//            String fileName = files[r.nextInt(files.length)].getName();
//
//            AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(entity.getType().toString(), fileName);
//
//            if (chance < mob.getSpawnChance().getCurrentMemoryValue()) {
//
//                LivingEntity spawned = e.getEntity();
//
//                spawned.setGlowing(mob.getIsGlowing().getCurrentMemoryValue());
//
//                spawned.setGravity(mob.getHasGravity().getCurrentMemoryValue());
//
//                spawned.setInvulnerable(mob.getIsInvulnerable().getCurrentMemoryValue());
//
//                spawned.setAI(mob.getHasAI().getCurrentMemoryValue());
//
//                spawned.setCanPickupItems(mob.getCanPickupItems().getCurrentMemoryValue());
//
//                spawned.setCustomName(ChatColor.translateAlternateColorCodes('&', mob.getMobDisplayName().getCurrentMemoryValue()));
//
//                spawned.setCustomNameVisible(true);
//
//                spawned.getPersistentDataContainer().set(new NamespacedKey(AstroPets.getPlugin(), "entity_file_name"),
//                        PersistentDataType.STRING,
//                        mob.getName().toUpperCase());
//
//                AttributeInstance attributeInstance = spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH);
//
//                attributeInstance.setBaseValue(40.0D);
//
//                EntityEquipment armor = spawned.getEquipment();
//
//                if (ItemFile.getAllValidHelmetsAndCustomHelmets().contains(mob.getMobHelmetItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobHelmetItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setHelmet(new ItemStack(Material.valueOf(mob.getMobHelmetItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setHelmet(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobHelmetItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setHelmetDropChance(Float.parseFloat(mob.getMobHelmetChance().getCurrentMemoryValue().toString()));
//                }
//
//                if (ItemFile.getAllValidChestplatesAndCustomChestplates().contains(mob.getMobChestplateItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobChestplateItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setChestplate(new ItemStack(Material.valueOf(mob.getMobChestplateItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setChestplate(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobChestplateItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setChestplateDropChance(Float.parseFloat(mob.getMobChestplateChance().getCurrentMemoryValue().toString()));
//                }
//
//                if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(mob.getMobLeggingsItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobLeggingsItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setLeggings(new ItemStack(Material.valueOf(mob.getMobLeggingsItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setLeggings(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobLeggingsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setLeggingsDropChance(Float.parseFloat(mob.getMobLeggingsChance().getCurrentMemoryValue().toString()));
//                }
//
//                if (ItemFile.getAllValidBootsAndCustomBoots().contains(mob.getMobBootsItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobBootsItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setBoots(new ItemStack(Material.valueOf(mob.getMobBootsItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setBoots(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobBootsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setBootsDropChance(Float.parseFloat(mob.getMobBootsChance().getCurrentMemoryValue().toString()));
//                }
//
//                if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobMainHandItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobMainHandItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobMainHandItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobMainHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobMainHandChance().getCurrentMemoryValue().toString()));
//                }
//
//                if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobOffHandItem().getCurrentMemoryValue())) {
//
//                    if (Material.matchMaterial(mob.getMobOffHandItem().getCurrentMemoryValue()) != null) {
//                        assert armor != null;
//                        armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobOffHandItem().getCurrentMemoryValue())));
//                    } else {
//                        assert armor != null;
//                        armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobOffHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
//                    }
//                    armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobOffHandChance().getCurrentMemoryValue().toString()));
//                }
//            }
//        }
    }
}
