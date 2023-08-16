package net.mcjustice.astropets.file;

import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMobFile {

    public static HashMap<String, AstroMob> customMobsMap = new HashMap<>();

    public static HashMap<String, AstroMob> customMobsMapMappedToFolders = new HashMap<>();

    public static void populateCustomMobsMap() {

        File customMobsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Mobs");

        File[] customMobsFileArray = customMobsFolder.listFiles();

        if (customMobsFileArray == null) return;

        if (customMobsFileArray.length > 0) {

            for (File f : customMobsFileArray) {

                File customMobFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Custom Mobs" + File.separator + f.getName());

                File[] customMobsInFile = customMobFolder.listFiles();

                if (customMobsInFile == null) return;

                if (customMobsInFile.length > 0) {
                    for (File mob : customMobsInFile) {

                        if (!getCustomMobsMapMappedToFolders().containsKey(mob.getAbsolutePath())) {

                            AstroMob astroMob = new AstroMob(mob.getPath());

                            customMobsMapMappedToFolders.put(mob.getAbsolutePath().toUpperCase(), astroMob);
                        }
                    }
                }
            }
        }
    }

    public static HashMap<String, AstroMob> getCustomMobsMapMappedToFolders() {

        return customMobsMapMappedToFolders;
    }

    public static void createNewCustomMob2(String absolutePath) {

        AstroMob astroMob = new AstroMob(FileUtils.checkForYML(absolutePath));

        if (!customMobsMapMappedToFoldersContainsMob(astroMob.getAbsolutePath().toUpperCase())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Custom mobs map doesn't contain " + astroMob.getAbsolutePath().toUpperCase() + " adding now...");
            getCustomMobsMapMappedToFolders().put(astroMob.getAbsolutePath().toUpperCase(), astroMob);

            astroMob.reloadParams();
        }
    }

    public static boolean customMobsMapMappedToFoldersContainsMob(String absolutePath) {

        return getCustomMobsMapMappedToFolders().containsKey(absolutePath.toUpperCase());
    }

    public static boolean customMobsMapMappedToFolderContainsMultiple(String mobName) {

        int count = 0;

        for (String s : getCustomMobsMapMappedToFolders().keySet()) {
            if (s.toUpperCase().contains(mobName.toUpperCase())) {
                count++;
            }
        }

        return count >= 2;
    }

    public static boolean customMobsMapMappedToFoldersContainsPartialKey(String keyToCheck) {

        for (String s : getCustomMobsMapMappedToFolders().keySet()) {
            if (s.toUpperCase().contains(keyToCheck.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean customMobsMapMappedToFoldersContainsPartialKeys(String mobType, String mobName) {

        for (String s : getCustomMobsMapMappedToFolders().keySet()) {
            if (s.toUpperCase().contains(mobType.toUpperCase()) && s.toUpperCase().contains(mobName.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static AstroMob getCustomMobMappedToFolder(String mobType, String mobName) {

        AstroMob mob = null;

        for (Map.Entry<String, AstroMob> entry : getCustomMobsMapMappedToFolders().entrySet()) {
            if (entry.getKey().contains(mobType) && entry.getKey().contains(mobName)) {
                mob = entry.getValue();
            }
        }
        return mob;
    }

    public static List<AstroMob> getCustomMobsMappedToFolder(String mobType) {

        List<AstroMob> mobs = new ArrayList<>();

        for (Map.Entry<String, AstroMob> entry : getCustomMobsMapMappedToFolders().entrySet()) {
            if (entry.getKey().contains(mobType)) {
                mobs.add(entry.getValue());
            }
        }
        return mobs;
    }
}
