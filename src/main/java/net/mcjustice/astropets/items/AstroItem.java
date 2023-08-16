package net.mcjustice.astropets.items;

import net.mcjustice.astroapi.file.ItemsAstroFile;
import net.mcjustice.astroapi.fileparameters.*;
import net.mcjustice.astroapi.utils.ItemUtils;
import net.mcjustice.astroapi.utils.ParticleUtils;
import net.mcjustice.astroapi.utils.TextUtils;
import net.mcjustice.astropets.file.ItemFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AstroItem extends ItemsAstroFile {

    public AstroItem(String filePath) {
        super(filePath);
    }

    @Override
    public void setIsEnabled() {

        isEnabled = new AstroBoolean(this, "Enabled", true);
    }

    @Override
    public void setItemName() {

        itemName = new AstroString(this, "File Name", List.of(this.getName()), this.getName());
    }

    @Override
    public void setDisplayName() {

        displayName = new AstroString(this, "Display Name", null, this.getName().replace(".yml", ""));
    }

    @Override
    public void setEnchantments() {

        enchantments = new AstroHashMap<>(this, "Enchantments", ItemUtils.getValidEnchantments(), ItemUtils.validEnchantmentLevels(), null);
    }

    @Override
    public void setItemFlags() {

        itemFlags = new AstroStringList(this, "Item Flags", ItemUtils.validItemFlagStringList(), false, null);
    }

    @Override
    public void setItemLore() {

        itemLore = new AstroStringList(this, "Item Lore", null, true, List.of("Hello!"));
    }


    @Override
    public void setItemResultMaterial() {

        itemResultMaterial = new AstroString(this, "Result Item", ItemUtils.itemMaterialsList(), "DIAMOND_SWORD");
    }

    @Override
    public void setItemResultAmount() {

        itemResultAmount = new AstroInteger(this, "Result Amount", 1, 64, 1);
    }

    @Override
    public void setIsRecipeEnabled() {
        isRecipeEnabled = new AstroBoolean(this, "Recipe.Recipe Enabled", true);
    }

    @Override
    public void setRecipeFirstRow() {

        recipeFirstRow = new AstroString(this, "Recipe.First Row", null, "aaa");
    }

    @Override
    public void setRecipeSecondRow() {

        recipeSecondRow = new AstroString(this, "Recipe.Second Row", null, "bbb");
    }

    @Override
    public void setRecipeThirdRow() {

        recipeThirdRow = new AstroString(this, "Recipe.Third Row", null, "xax");
    }

    @Override
    public void setIngredientMaterials() {

        // TODO: We will need to figure out a way to check if the custom item used in a recipe is enabled or not. If it isn't enabled, it shouldn't be allowed to be passed as a parameter.
        // TODO: The issue right now is that a copy of the list is passed when the instance is created, but there is no foreseeable way to update it once it is passed in. We can update it
        // TODO: by creating a setter for valid values or a way to add a valid value to the list, but we can't do it within the API since the API depends on plugin-specific file access.

        ingredientMaterials = new ArrayList<>();
        for (char c : getIngredientChars()) {

            ingredientMaterials.add(new AstroString(this, "Recipe.Ingredients." + c + ".Material", ItemFile.getAllValidMatsAndEnabledItems(), "STONE"));
        }
    }

    @Override
    public void setIngredientAmounts() {

        ingredientAmounts = new ArrayList<>();
        for (char c : getIngredientChars()) {

            ingredientAmounts.add(new AstroInteger(this, "Recipe.Ingredients." + c + ".Amount", 1, 64, 1));
        }

    }

    @Override
    public void setParticleType() {

        particleType = new AstroString(
                this,
                "Particles.Particle Type",
                Arrays.asList("NORMAL", "DIRECTIONAL", "REDSTONE", "MATERIAL", "TRANSITIONAL", "VIBRATIONAL", "DISABLED"),
                "NORMAL");
    }

    @Override
    public void setParticleEffect() {

        particleEffect = new AstroString(this, "Particles.Particle Effect", particleType, ParticleUtils.getParticleTypeMapString(), ParticleUtils.getParticleEffectDefault());
    }

    @Override
    public void setParticleBlock() {

        particleBlock = new AstroString(this, "Particles.Particle Block", ParticleUtils.getValidParticleBlocks(), "STONE");
    }

    @Override
    public void setParticlePeriod() {

        particlePeriod = new AstroInteger(this, "Particles.Particle Period", 1, 30, 3);
    }

    @Override
    public void setParticleSize() {

        particleSize = new AstroDouble(this, "Particles.Particle Size", 1.0D, 50.0D, 3.0D);
    }

    @Override
    public void setParticleVibrationDuration() {

        particleVibrationDuration = new AstroInteger(this, "Particles.Vibration Duration", 1, 200, 20);
    }

    @Override
    public void setParticleCount() {

        particleCount = new AstroInteger(this, "Particles.Particle Count", 1, 100, 5);
    }

    @Override
    public void setOffsetX() {

        offsetX = new AstroInteger(this, "Particles.X Offset", 0, 50, 0);
    }

    @Override
    public void setOffsetY() {

        offsetY = new AstroInteger(this, "Particles.Y Offset", 0, 50, 0);
    }

    @Override
    public void setOffsetZ() {

        offsetZ = new AstroInteger(this, "Particles.Z Offset", 0, 50, 0);
    }

    @Override
    public void setParticleColorRed() {

        particleColorRed = new AstroInteger(this, "Particles.Particle Color.Red Amount", 0, 255, 0);
    }

    @Override
    public void setParticleColorGreen() {

        particleColorGreen = new AstroInteger(this, "Particles.Particle Color.Green Amount", 0, 255, 0);
    }

    @Override
    public void setParticleColorBlue() {

        particleColorBlue = new AstroInteger(this, "Particles.Particle Color.Blue Amount", 0, 255, 0);
    }

    @Override
    public void setParticleTransitionRed() {

        particleTransitionRed = new AstroInteger(this, "Particles.Particle Color.Red Transition Amount", 0, 255, 0);
    }

    @Override
    public void setParticleTransitionGreen() {

        particleTransitionGreen = new AstroInteger(this, "Particles.Particle Color.Green Transition Amount", 0, 255, 0);
    }

    @Override
    public void setParticleTransitionBlue() {

        particleTransitionBlue = new AstroInteger(this, "Particles.Particle Color.Blue Transition Amount", 0, 255, 0);
    }

    @Override
    public void setIngredientChars() {

        ingredientChars = TextUtils.getUniqueChars(getRecipeFirstRowValue() + getRecipeSecondRowValue() + getRecipeThirdRowValue());
    }
}
