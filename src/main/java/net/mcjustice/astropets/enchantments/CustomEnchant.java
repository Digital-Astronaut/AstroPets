package net.mcjustice.astropets.enchantments;

import net.mcjustice.astroapi.File.EnchantmentsAstroFile;
import net.mcjustice.astroapi.FileParameters.AstroBoolean;
import net.mcjustice.astroapi.FileParameters.AstroInteger;
import net.mcjustice.astroapi.FileParameters.AstroString;
import net.mcjustice.astroapi.Utils.EnchantmentUtils;
import org.bukkit.NamespacedKey;

import java.util.List;

public class CustomEnchant extends EnchantmentsAstroFile {


    /** Use this to create new custom enchantments **/
    public CustomEnchant(String filePath) {
        super(filePath);
    }

    public NamespacedKey getNamespacedKey() {

        NamespacedKey key = null;

        for (NamespacedKey key1 : EnchantmentUtils.getKeys()) {
            if (key1.getKey().equalsIgnoreCase(getFileNameValue())) {
                key = key1;
                System.out.println("Found " + getFileNameValue());
                break;
            } else {
                System.out.println("Keys list doesn't contain " + getFileNameValue());
            }
        }
        return key;
    }

    @Override
    public void setIsEnabled() {

        isEnabled = new AstroBoolean(this, "Enabled", true);
    }

    @Override
    public void setFileName() {

        fileName = new AstroString(this, "File Name", List.of(this.getName()), this.getName());
    }

    @Override
    public void setDisplayName() {
        displayName = new AstroString(this, "Display Name", null, this.getName().replace(".yml", "").toUpperCase());
    }

    @Override
    public void setMaxLevel() {
        maxLevel = new AstroInteger(this, "Max Level", 1, Integer.MAX_VALUE, 5);
    }

    @Override
    public void setStartLevel() {
        startLevel = new AstroInteger(this, "Start Level", 1, Integer.MAX_VALUE, 1);
    }

    @Override
    public void setItemTarget() {
        itemTarget = new AstroString(this, "Item Target", EnchantmentUtils.getAllTargets(), "TOOL");
    }

    @Override
    public void setIsTreasure() {
        isTreasure = new AstroBoolean(this, "Treasure", true);
    }

    @Override
    public void setIsCursed() {
        isCursed = new AstroBoolean(this, "Cursed", false);
    }

    @Override
    public void setIsDiscoverable() {
        isDiscoverable = new AstroBoolean(this, "Discoverable", true);
    }

    @Override
    public void setIsTradeable() {
        isTradeable = new AstroBoolean(this, "Tradeable", true);
    }
}
