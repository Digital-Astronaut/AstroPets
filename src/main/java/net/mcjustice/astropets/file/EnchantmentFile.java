package net.mcjustice.astropets.file;

import net.mcjustice.astroapi.enchantments.CustomEnchantment;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.enchantments.CustomEnchant;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;

public class EnchantmentFile {

    public static HashMap<String, CustomEnchant> enchantsMap = new HashMap<>();
    public static HashMap<CustomEnchant, CustomEnchantment> enchantsMap2 = new HashMap<>();

    public static void mapEnchantmentToEnchantFile() {

        File enchantsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Enchantments");

        File[] enchantsFileArray = enchantsFolder.listFiles();

        if (enchantsFileArray == null) return;

        if (enchantsFileArray.length > 0) {
            for (File f : enchantsFileArray) {
                enchantsMap.get(f.getName());
            }
        }
    }

    public static void populateEnchantsMap() {

        File enchantsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Enchantments");

        File[] enchantsFileArray = enchantsFolder.listFiles();

        if (enchantsFileArray == null) return;

        if (enchantsFileArray.length > 0) {
            for (File f : enchantsFileArray) {
                if (!enchantsMap.containsKey(f.getName())) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding " + f.getName() + " to map!");

                    CustomEnchant enchant = new CustomEnchant(f.getPath());

                    enchantsMap.put(f.getName().toUpperCase(), enchant);
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Pets folder is empty!");
        }
    }

    public static HashMap<String, CustomEnchant> getEnchantsMap() {

        return enchantsMap;
    }

    public static void createNewEnchantment(String filePath) {

        CustomEnchant customEnchant = new CustomEnchant(FileUtils.checkForYML(filePath));

        if (!enchantsMapContainsPet(customEnchant.getName().toUpperCase())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Enchants map doesn't contain " + customEnchant.getName().toUpperCase() + " adding now...");
            getEnchantsMap().put(customEnchant.getName().toUpperCase(), customEnchant);

            customEnchant.reloadParams();
        }
    }

    public static boolean enchantsMapContainsPet(String enchantFile) {

        return getEnchantsMap().containsKey(enchantFile.toUpperCase());
    }
}
