package net.mcjustice.astropets.file;

import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.ItemUtils;
import net.mcjustice.astroapi.utils.MaterialUtils;
import net.mcjustice.astropets.AstroPets;
import net.mcjustice.astropets.items.AstroItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFile {

    public static HashMap<String, AstroItem> itemsMap = new HashMap<>();

    private static List<String> validMatsAndRegisteredItems = new ArrayList<>();

    public static void populateItemsMap() {

        File itemsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Items");

        File[] itemsFileArray = itemsFolder.listFiles();

        if (itemsFileArray == null) return;

        if (itemsFileArray.length > 0) {
            for (File f : itemsFileArray) {
                if (!itemsMap.containsKey(f.getName())) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Adding item " + f.getName() + " to map!");

                    AstroItem astroItem = new AstroItem(f.getPath());

                    itemsMap.put(f.getName().toUpperCase(), astroItem);
                }
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Items folder is empty!");
        }
    }

    public static List<String> getFileNames() {

        File itemsFolder = new File(AstroPets.getPlugin().getDataFolder().getAbsoluteFile() + File.separator + "Items");

        File[] itemsFileArray = itemsFolder.listFiles();

        List<String> files = new ArrayList<>();
        assert itemsFileArray != null;
        if (itemsFileArray.length > 0) {
            for (File f : itemsFileArray) {
                files.add(f.getName());
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Items folder is empty!");
            return new ArrayList<>();
        }
        return files;
    }

    public static HashMap<String, AstroItem> getItemsMap() {

        return itemsMap;
    }

    public static void createNewItem(String fileName) {

        AstroItem astroItem = new AstroItem(FileUtils.checkForYML(fileName));

        if (!itemsMapContainsItem(astroItem.getName().toUpperCase())) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Items map doesn't contain " + astroItem.getName().toUpperCase() + " adding now...");
            getItemsMap().put(astroItem.getName().toUpperCase(), astroItem);

            astroItem.reloadParams();
        }
    }

    public static boolean itemsMapContainsItem(String itemFile) {

        return getItemsMap().containsKey(itemFile.toUpperCase());
    }

    public static List<String> getAllValidMatsAndEnabledItems() {

//        List<String> validMatsAndRegisteredItems = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();
            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

//                System.out.println("Adding: " + entry.getValue().getDisplayName().getCurrentValue() + ": " + entry.getValue().getIsEnabled().getCurrentValue());
                validMatsAndRegisteredItems.add(entry.getKey().replace(".YML", ""));
            }
            else {
//                System.out.println("Removing: " + entry.getValue().getDisplayName().getCurrentValue() + ": " + entry.getValue().getIsEnabled().getCurrentValue());

                validMatsAndRegisteredItems.remove(entry.getKey().replace(".YML", ""));
            }
        }
        validMatsAndRegisteredItems.addAll(ItemUtils.itemMaterialsList());

        return validMatsAndRegisteredItems;
    }

    public static void updateAllValidMatsAndEnabledItems() {

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();
            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                validMatsAndRegisteredItems.add(entry.getKey().replace(".YML", ""));
            } else {
                validMatsAndRegisteredItems.remove(entry.getKey().replace(".YML", ""));
            }
        }
    }

    public static List<String> getAllValidMatsAndEnabledHeldItems() {

        List<String> validMatsAndRegisteredItems = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();
            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                validMatsAndRegisteredItems.add(entry.getKey().replace(".YML", ""));
            }
        }

        validMatsAndRegisteredItems.addAll(MaterialUtils.allValidMaterialsStringList());

        return validMatsAndRegisteredItems;
    }

    public static List<String> getAllValidHelmetsAndCustomHelmets() {

        List<String> validHelmetsAndCustomHelmets = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();

            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                entry.getValue().getItemResultMaterial().onReload();

                if (MaterialUtils.getAllHelmetMaterials().contains(entry.getValue().getItemResultMaterial().getCurrentValue().toString().toUpperCase())) {

                    validHelmetsAndCustomHelmets.add(entry.getKey().replace(".YML", ""));
                }
            }
        }

        validHelmetsAndCustomHelmets.addAll(MaterialUtils.getAllHelmetMaterials());

        return validHelmetsAndCustomHelmets;
    }

    public static List<String> getAllValidChestplatesAndCustomChestplates() {

        List<String> validChestplatesAndCustomChestplates = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();

            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                entry.getValue().getItemResultMaterial().onReload();

                if (MaterialUtils.getAllChestplateMaterials().contains(entry.getValue().getItemResultMaterial().getCurrentValue().toString().toUpperCase())) {

                    validChestplatesAndCustomChestplates.add(entry.getKey().replace(".YML", ""));
                }
            }
        }

        validChestplatesAndCustomChestplates.addAll(MaterialUtils.getAllChestplateMaterials());

        return validChestplatesAndCustomChestplates;
    }

    public static List<String> getAllValidLeggingsAndCustomLeggings() {

        List<String> validLeggingsAndCustomLeggings = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {

            entry.getValue().getIsEnabled().onReload();

            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                entry.getValue().getItemResultMaterial().onReload();

                if (MaterialUtils.getAllLeggingMaterials().contains(entry.getValue().getItemResultMaterial().getCurrentValue().toString().toUpperCase())) {

                    validLeggingsAndCustomLeggings.add(entry.getKey().replace(".YML", ""));
                }
            }
        }

        validLeggingsAndCustomLeggings.addAll(MaterialUtils.getAllLeggingMaterials());

        return validLeggingsAndCustomLeggings;
    }

    public static List<String> getAllValidBootsAndCustomBoots() {

        List<String> validBootsAndCustomBoots = new ArrayList<>();

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {
            entry.getValue().getIsEnabled().onReload();

            if (Boolean.parseBoolean(entry.getValue().getIsEnabled().getCurrentValue().toString())) {

                entry.getValue().getItemResultMaterial().onReload();

                if (MaterialUtils.getAllBootMaterials().contains(entry.getValue().getItemResultMaterial().getCurrentValue().toString().toUpperCase())) {

                    validBootsAndCustomBoots.add(entry.getKey().replace(".YML", ""));
                }
            }
        }

        validBootsAndCustomBoots.addAll(MaterialUtils.getAllBootMaterials());

        return validBootsAndCustomBoots;
    }
}
