package net.mcjustice.astropets.mobs;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.ItemFile;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Random;

public class AstroMobUtils {

    public static void spawnMobBasedOnChance(LivingEntity entity) {

        File file = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Mobs" + File.separator + entity.getType());

        File[] files = file.listFiles();

        assert files != null;
        if (files.length != 0) {

            Random r = new Random();

            int chance = r.nextInt(100) + 1;

            String fileName = files[r.nextInt(files.length)].getName();

            AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(entity.getType().toString(), fileName.toUpperCase());

            if (chance < mob.getSpawnChance().getCurrentMemoryValue()) {

                entity.setGlowing(mob.getIsGlowing().getCurrentMemoryValue());

                entity.setGravity(mob.getHasGravity().getCurrentMemoryValue());

                entity.setInvulnerable(mob.getIsInvulnerable().getCurrentMemoryValue());

                entity.setAI(mob.getHasAI().getCurrentMemoryValue());

                entity.setCanPickupItems(mob.getCanPickupItems().getCurrentMemoryValue());

                entity.setCustomName(ChatColor.translateAlternateColorCodes('&', mob.getMobDisplayName().getCurrentMemoryValue()));

                entity.setCustomNameVisible(true);

                entity.getPersistentDataContainer().set(new NamespacedKey(AstroPets.getPlugin(), "entity_file_name"),
                        PersistentDataType.STRING,
                        mob.getName().toUpperCase());

                AttributeInstance attributeInstance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);

                attributeInstance.setBaseValue(40.0D);

                EntityEquipment armor = entity.getEquipment();

                int armorChance = r.nextInt(100) + 1;

                if (mob.getMobHelmetEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobHelmetChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidHelmetsAndCustomHelmets().contains(mob.getMobHelmetItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobHelmetItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setHelmet(new ItemStack(Material.valueOf(mob.getMobHelmetItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setHelmet(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobHelmetItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setHelmetDropChance(Float.parseFloat(mob.getMobHelmetChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setHelmet(null);
                    }
                } else {
                    armor.setHelmet(null);
                }

                if (mob.getMobChestplateEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobChestplateChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidChestplatesAndCustomChestplates().contains(mob.getMobChestplateItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobChestplateItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setChestplate(new ItemStack(Material.valueOf(mob.getMobChestplateItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setChestplate(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobChestplateItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setChestplateDropChance(Float.parseFloat(mob.getMobChestplateChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setChestplate(null);
                    }
                } else {
                    armor.setChestplate(null);
                }

                if (mob.getMobLeggingsEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobLeggingsChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(mob.getMobLeggingsItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobLeggingsItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setLeggings(new ItemStack(Material.valueOf(mob.getMobLeggingsItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setLeggings(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobLeggingsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setLeggingsDropChance(Float.parseFloat(mob.getMobLeggingsChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setLeggings(null);
                    }
                } else {
                    armor.setLeggings(null);
                }

                if (mob.getMobBootsEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobBootsChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidBootsAndCustomBoots().contains(mob.getMobBootsItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobBootsItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setBoots(new ItemStack(Material.valueOf(mob.getMobBootsItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setBoots(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobBootsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setBootsDropChance(Float.parseFloat(mob.getMobBootsChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setBoots(null);
                    }
                } else {
                    armor.setBoots(null);
                }

                if (mob.getMobMainHandEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobMainHandChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobMainHandItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobMainHandItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobMainHandItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobMainHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobMainHandChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setItemInMainHand(null);
                    }
                } else {
                    armor.setItemInMainHand(null);
                }

                if (mob.getMobOffHandEnabled().getCurrentMemoryValue()) {

                    if (armorChance < mob.getMobOffHandChance().getCurrentMemoryValue()) {

                        if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobOffHandItem().getCurrentMemoryValue())) {

                            if (Material.matchMaterial(mob.getMobOffHandItem().getCurrentMemoryValue()) != null) {
                                assert armor != null;
                                armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobOffHandItem().getCurrentMemoryValue())));
                            } else {
                                assert armor != null;
                                armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobOffHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                            }
                            armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobOffHandChance().getCurrentMemoryValue().toString()));
                        }
                    } else {
                        armor.setItemInOffHand(null);
                    }
                } else {
                    armor.setItemInOffHand(null);
                }
            }
        }
    }

    public static void spawnMobAtPlayerLocation(AstroMob mob, Player p) {

        LivingEntity entity = (LivingEntity) p.getWorld().spawnEntity(p.getLocation(), EntityType.valueOf(mob.getParentFile().getName()));

        entity.setGlowing(mob.getIsGlowing().getCurrentMemoryValue());

        entity.setGravity(mob.getHasGravity().getCurrentMemoryValue());

        entity.setInvulnerable(mob.getIsInvulnerable().getCurrentMemoryValue());

        entity.setAI(mob.getHasAI().getCurrentMemoryValue());

        entity.setCanPickupItems(mob.getCanPickupItems().getCurrentMemoryValue());

        entity.setCustomName(ChatColor.translateAlternateColorCodes('&', mob.getMobDisplayName().getCurrentMemoryValue()));
        entity.setCustomNameVisible(true);

        entity.getPersistentDataContainer().set(new NamespacedKey(AstroPets.getPlugin(), "entity_file_name"), PersistentDataType.STRING, mob.getName().toUpperCase());

        AttributeInstance attributeInstance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        attributeInstance.setBaseValue(40.0D);

        EntityEquipment armor = entity.getEquipment();

        Random r = new Random();

        int chance = r.nextInt(100) + 1;

        if (mob.getMobHelmetEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobHelmetChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidHelmetsAndCustomHelmets().contains(mob.getMobHelmetItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobHelmetItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setHelmet(new ItemStack(Material.valueOf(mob.getMobHelmetItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setHelmet(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobHelmetItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setHelmetDropChance(Float.parseFloat(mob.getMobHelmetChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setHelmet(null);
            }
        } else {
            armor.setHelmet(null);
        }

        if (mob.getMobChestplateEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobChestplateChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidChestplatesAndCustomChestplates().contains(mob.getMobChestplateItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobChestplateItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setChestplate(new ItemStack(Material.valueOf(mob.getMobChestplateItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setChestplate(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobChestplateItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setChestplateDropChance(Float.parseFloat(mob.getMobChestplateChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setChestplate(null);
            }
        } else {
            armor.setChestplate(null);
        }


        if (mob.getMobLeggingsEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobLeggingsChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidLeggingsAndCustomLeggings().contains(mob.getMobLeggingsItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobLeggingsItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setLeggings(new ItemStack(Material.valueOf(mob.getMobLeggingsItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setLeggings(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobLeggingsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setLeggingsDropChance(Float.parseFloat(mob.getMobLeggingsChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setLeggings(null);
            }
        } else {
            armor.setLeggings(null);
        }

        if (mob.getMobBootsEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobBootsChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidBootsAndCustomBoots().contains(mob.getMobBootsItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobBootsItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setBoots(new ItemStack(Material.valueOf(mob.getMobBootsItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setBoots(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobBootsItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setBootsDropChance(Float.parseFloat(mob.getMobBootsChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setBoots(null);
            }
        } else {
            armor.setBoots(null);
        }

        if (mob.getMobMainHandEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobMainHandChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobMainHandItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobMainHandItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobMainHandItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobMainHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobMainHandChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setItemInMainHand(null);
            }
        } else {
            armor.setItemInMainHand(null);
        }

        if (mob.getMobOffHandEnabled().getCurrentMemoryValue()) {

            if (chance < mob.getMobOffHandChance().getCurrentMemoryValue()) {

                if (ItemFile.getAllValidMatsAndEnabledHeldItems().contains(mob.getMobOffHandItem().getCurrentMemoryValue())) {

                    if (Material.matchMaterial(mob.getMobOffHandItem().getCurrentMemoryValue()) != null) {
                        assert armor != null;
                        armor.setItemInMainHand(new ItemStack(Material.valueOf(mob.getMobOffHandItem().getCurrentMemoryValue())));
                    } else {
                        assert armor != null;
                        armor.setItemInMainHand(ItemFile.getItemsMap().get(FileUtils.checkForYML(mob.getMobOffHandItem().getCurrentMemoryValue()).toUpperCase()).getItemStack(AstroPets.getPlugin()));
                    }
                    armor.setItemInMainHandDropChance(Float.parseFloat(mob.getMobOffHandChance().getCurrentMemoryValue().toString()));
                }
            } else {
                armor.setItemInOffHand(null);
            }
        } else {
            armor.setItemInOffHand(null);
        }
    }

    public static String getRandomQuote(List<String> quotes, @Nullable String replace, @Nullable String replaceWith) {

        if (quotes != null && quotes.size() != 0) {

            Random r = new Random();

            if (replace != null && replaceWith != null) {

                if (quotes.get(r.nextInt(quotes.size())).contains(replace)) {
                    return quotes.get(r.nextInt(quotes.size())).replace(replace, replaceWith);
                }
            }

            return quotes.get(r.nextInt(quotes.size()));
        }
        return "";
    }
}
