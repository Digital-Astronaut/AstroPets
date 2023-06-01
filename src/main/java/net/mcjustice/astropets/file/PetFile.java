package net.mcjustice.astropets.file;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.mobs.AstroPet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;

public final class PetFile {

    public static HashMap<String, AstroPet> petsMap = new HashMap<>();

    public static void populatePetsMap() {

        File petsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Pets");

        File[] petsFileArray = petsFolder.listFiles();

        if (petsFileArray == null) return;

        if (petsFileArray.length > 0) {
            for (File f : petsFileArray) {
                if (!petsMap.containsKey(f.getName())) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding " + f.getName() + " to map!");

                    AstroPet astroPet = new AstroPet(f.getPath());

                    petsMap.put(f.getName().toUpperCase(), astroPet);
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Pets folder is empty!");
        }
    }

    public static HashMap<String, AstroPet> getPetsMap() {

        return petsMap;
    }

    public static void createNewPet(String filePath) {

        AstroPet astroPet = new AstroPet(FileUtils.checkForYML(filePath));

        if (!petsMapContainsPet(astroPet.getName().toUpperCase())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Pets map doesn't contain " + astroPet.getName().toUpperCase() + " adding now...");
            getPetsMap().put(astroPet.getName().toUpperCase(), astroPet);

            astroPet.reloadParams();
        }
    }

    public static boolean petsMapContainsPet(String petFile) {

        return getPetsMap().containsKey(petFile.toUpperCase());
    }

}
