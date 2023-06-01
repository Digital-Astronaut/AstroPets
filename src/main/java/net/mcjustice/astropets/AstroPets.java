package net.mcjustice.astropets;

import net.mcjustice.astroapi.Commands.CommandManager;
import net.mcjustice.astroapi.File.CustomMobAstroFile;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astroapi.Utils.EnchantmentUtils;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astroapi.Utils.ItemUtils;
import net.mcjustice.astropets.commands.custommobs.CustomMobMenu;
import net.mcjustice.astropets.commands.custommobs.CustomMobReload;
import net.mcjustice.astropets.commands.custommobs.CustomMobUpdate;
import net.mcjustice.astropets.commands.enchantments.EnchantCreate;
import net.mcjustice.astropets.commands.enchantments.EnchantMenu;
import net.mcjustice.astropets.commands.enchantments.EnchantReload;
import net.mcjustice.astropets.commands.items.ItemCreate;
import net.mcjustice.astropets.commands.items.ItemMenu;
import net.mcjustice.astropets.commands.items.ItemReload;
import net.mcjustice.astropets.commands.items.ItemUpdate;
import net.mcjustice.astropets.commands.pets.PetCreate;
import net.mcjustice.astropets.commands.pets.PetMenu;
import net.mcjustice.astropets.commands.pets.PetReload;
import net.mcjustice.astropets.enchantments.CustomEnchant;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.EnchantmentFile;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.file.PetFile;
import net.mcjustice.astropets.items.AstroItem;
import net.mcjustice.astropets.listeners.PetInteractEvent;
import net.mcjustice.astropets.listeners.enchantments.AutoSmeltListener;
import net.mcjustice.astropets.listeners.enchantments.TreecapitatorEvent;
import net.mcjustice.astropets.listeners.items.ChestLinkListener;
import net.mcjustice.astropets.listeners.particles.ItemParticleListener;
import net.mcjustice.astropets.mobs.AstroMob;
import net.mcjustice.astropets.mobs.AstroPet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import net.mcjustice.astroapi.AstroAPI;

import java.io.File;
import java.util.Map;

public final class AstroPets extends JavaPlugin {

    public static final AstroAPI api = (AstroAPI) Bukkit.getServer().getPluginManager().getPlugin("AstroAPI");

    public static AstroPets ASTROPETS;

    public static FileUtils FILEUTILS;

    @Override
    public void onEnable() {

        if (api == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't find AstroAPI. Shutting down.");
            return;
        }

        if (api.isEnabled()) {
            ASTROPETS = this;
            FILEUTILS = new FileUtils(this);
            MenuManager.setupListener(getServer(), this);

            FileUtils.createNewFolder("Pets");
            FileUtils.createNewFolder("Custom Enchantments");
            FileUtils.createNewFolder("Items");
            FileUtils.createNewFolder("Custom Mobs");

            for (EntityType entity : EntityType.values()) {
                if (entity.isAlive() && !entity.toString().equalsIgnoreCase("PLAYER") && !entity.toString().equalsIgnoreCase("ARMOR_STAND")) {
                    FileUtils.createNewFolder("Custom Mobs", entity.toString());
                }
            }

            populateFileMaps();

            addRecipes();

            registerListeners();

            try {
                createCommands();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully registered AstroPets!");
        }
    }

    public void addRecipes() {

        ItemStack saddle = new ItemStack(Material.SADDLE, 1);
        ItemStack nameTag = new ItemStack(Material.NAME_TAG, 1);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "saddle"), saddle);
        ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey(this, "nametag"), nameTag);

        recipe.shape("  X", "XXX", "X X");
        recipe2.shape("YYY", "YZY", "YYY");

        recipe.setIngredient('X', Material.LEATHER);
        recipe2.setIngredient('Z', Material.DIRT);

        Bukkit.addRecipe(recipe);
        Bukkit.addRecipe(recipe2);

        ItemStack leather = new ItemStack(Material.LEATHER, 1);

        FurnaceRecipe spiceRecipe = new FurnaceRecipe(new NamespacedKey(this, "leather"), leather, Material.ROTTEN_FLESH, 1.0f, 100);

        Bukkit.addRecipe(spiceRecipe);


        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {

            String key = entry.getKey();
            AstroItem value = entry.getValue();

            if (Boolean.parseBoolean(value.getIsRecipeEnabled().getCurrentValue().toString()) == true) {

                ItemStack resultItem = new ItemStack(value.getItemStack(this));

                ShapedRecipe itemRecipe = new ShapedRecipe(new NamespacedKey(this, value.getItemNameValue().replace(".yml", "")), resultItem);

                itemRecipe.shape(value.getRecipeFirstRowValue(), value.getRecipeSecondRowValue(), value.getRecipeThirdRowValue());

                for (char c : value.getIngredientChars()) {

                    if (ItemFile.getItemsMap().get(key).getParamByPath("Recipe.Ingredients." + c + ".Material") != null) {

                        String pathToCheck = ItemFile.getItemsMap().get(key).getParamByPath("Recipe.Ingredients." + c + ".Material").getCurrentValue().toString();

                        if (pathToCheck != null) {
                            if (Material.matchMaterial(pathToCheck) != null) {
                                itemRecipe.setIngredient(c, Material.valueOf(pathToCheck.toUpperCase()));
                            } else {
                                if (ItemFile.getItemsMap().containsKey(FileUtils.checkForYML(pathToCheck).toUpperCase())) {
                                    AstroItem requiredItem = ItemFile.getItemsMap().get(FileUtils.checkForYML(pathToCheck).toUpperCase());

                                    itemRecipe.setIngredient(c, requiredItem.getItemStack(this));
                                } else {
                                    System.out.println("Items file doesn't contain " + pathToCheck.toUpperCase());
                                }
                            }
                        }
                    }
                }
                Bukkit.addRecipe(itemRecipe);
                System.out.println("Successfully registered " + itemRecipe.getKey());
            }
        }
    }

    public void populateFileMaps() {

        PetFile.populatePetsMap();

        EnchantmentFile.populateEnchantsMap();

        EnchantmentUtils.registerEnchantments(this, this.getDataFolder().getAbsoluteFile() + File.separator + "Custom Enchantments");

        if (!EnchantmentFile.getEnchantsMap().isEmpty()) {

            ItemUtils.reloadValidEnchants();
        }

        ItemFile.populateItemsMap();

        CustomMobFile.populateCustomMobsMap();

        reloadFiles();

    }

    public void reloadFiles() {

        for (Map.Entry<String, AstroPet> entry : PetFile.getPetsMap().entrySet()) {

            String key = entry.getKey();

            PetFile.getPetsMap().get(key).reloadParams();
        }

        for (Map.Entry<String, CustomEnchant> entry : EnchantmentFile.getEnchantsMap().entrySet()) {

            String key = entry.getKey();

            EnchantmentFile.getEnchantsMap().get(key).reloadParams();
        }

        for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {

            String key = entry.getKey();

            ItemFile.getItemsMap().get(key).reloadParams();
        }

        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMap().entrySet()) {

            String key = entry.getKey();

            CustomMobFile.getCustomMobsMap().get(key).reloadParams();
        }
    }

    public void createCommands() throws NoSuchFieldException, IllegalAccessException {
        CommandManager.createCommand(this, "pets", "Fun pets for Izzy! :D", "/pets", null, PetCreate.class, PetReload.class, PetMenu.class);
        CommandManager.createCommand(this, "enchants", "Custom enchantment creator", "/enchants", null, EnchantCreate.class, EnchantReload.class, EnchantMenu.class);
        CommandManager.createCommand(this, "items", "Custom item creator", "/items", null, ItemCreate.class, ItemReload.class, ItemMenu.class, ItemUpdate.class);
        CommandManager.createCommand(this, "mobs", "Custom mob creator", "/mobs", null, CustomMobMenu.class, CustomMobReload.class, CustomMobUpdate.class);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Successfully disabled AstroPets");
        EnchantmentUtils.deregisterEnchantments(this, this.getDataFolder().getAbsoluteFile() + File.separator + "Custom Enchantments");
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PetInteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new TreecapitatorEvent(), this);
        Bukkit.getPluginManager().registerEvents(new AutoSmeltListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemParticleListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestLinkListener(), this);
    }

    public static AstroPets getPlugin() {

        return ASTROPETS;
    }
}
