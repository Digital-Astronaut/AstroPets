package net.mcjustice.astropets.mobs;

import net.mcjustice.astroapi.File.CustomMobAstroFile;
import net.mcjustice.astroapi.FileParameters.*;
import net.mcjustice.astroapi.Utils.MaterialUtils;
import net.mcjustice.astroapi.Utils.MobUtils;
import net.mcjustice.astropets.file.ItemFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AstroMob extends CustomMobAstroFile {

    public AstroMob(String filePath) {
        super(filePath);
    }



    @Override
    public void setMobDisplayName() {

        mobDisplayName = new AstroString(this, "Mob Display Name", null, getName().replace(".yml", ""));
    }

    @Override
    public void setCreatureClassType() {

        // pass valid values dependent on files in the creature types folder.
//        creatureClassType = new AstroString(this, "Creature Class", )
    }

    @Override
    public void setMobHealth() {

        mobHealth = new AstroDouble(this, "Mob Health", 1.0D, Double.MAX_VALUE, 20.0D);
    }
    @Override
    public void setWhitelistedMobs() {

        whitelistedMobs = new AstroStringList(this, "Whitelisted Targets.Mobs", MobUtils.getEntityTypeListString(), false, List.of("PILLAGER"));
    }

    @Override
    public void setWhitelistedCreatureTypes() {

        whitelistedCreatureTypes = new AstroStringList(this, "Whitelisted Targets.Creature Types", null, false, List.of("VAMPIRE"));
    }

    @Override
    public void setParticleEffect() {


    }

    @Override
    public void setIsGlowing() {

        isGlowing = new AstroBoolean(this, "Attributes.Glowing", false);
    }

    @Override
    public void setHasGravity() {

        hasGravity = new AstroBoolean(this, "Attributes.Gravity", false);
    }

    @Override
    public void setHealthbarIcon() {

    }

    @Override
    public void setIsInvulnerable() {

        isInvulnerable = new AstroBoolean(this, "Attributes.Invulnerable", false);
    }

    @Override
    public void setCanPickupItems() {

        canPickupItems = new AstroBoolean(this, "Attributes.Can Pickup Items", true);
    }

    @Override
    public void setHasAI() {

        hasAI = new AstroBoolean(this, "Attributes.Has AI", false);
    }

    @Override
    public void setSpawnChance() {

        spawnChance = new AstroInteger(this, "Spawn Chance", 1,  100, 10);
    }

    @Override
    public void setMobDrops() {

        List<String> validMatsAndRegisteredItems = new ArrayList<>();

        List<Integer> validInts = new ArrayList<>();

        for (String file : ItemFile.getFileNames()) {
            validMatsAndRegisteredItems.add(file.toUpperCase().replace(".YML", ""));
        }

        for (int i = 0; i <= 64; i++) {
            validInts.add(i);
        }

        validMatsAndRegisteredItems.addAll(MaterialUtils.allValidMaterialsStringList());

//        mobDrops = new AstroHashMap<>(this, "Inventory Contents.Mob Drops", validMatsAndRegisteredItems, validInts, null);
        mobDrops = new AstroHashMap<>(this, "Inventory Contents.Mob Drops", null, null, null);
//    }
    }

    @Override
    public void setSectionTest() {

        sectionTest = new AstroSection(this, "Inventory Contents.Mob Drops", MaterialUtils.allValidMaterialsStringList(), null);
    }

    @Override
    public void setMobHelmetEnabled() {

        mobHelmetEnabled = new AstroBoolean(this, "Inventory Contents.Helmet.Enabled", true);
    }

    @Override
    public void setMobHelmetItem() {

        mobHelmetItem = new AstroString(this, "Inventory Contents.Helmet.Item", ItemFile.getAllValidHelmetsAndCustomHelmets(), "IRON_HELMET");
    }

    @Override
    public void setMobHelmetChance() {

        mobHelmetChance = new AstroInteger(this, "Inventory Contents.Helmet.Chance", 1, 100, 10);
    }

    @Override
    public void setMobChestplateEnabled() {

        mobChestplateEnabled = new AstroBoolean(this, "Inventory Contents.Chestplate.Enabled", true);
    }

    @Override
    public void setMobChestplateItem() {

        mobChestplateItem = new AstroString(this, "Inventory Contents.Chestplate.Item", ItemFile.getAllValidChestplatesAndCustomChestplates(), "IRON_CHESTPLATE");
    }

    @Override
    public void setMobChestplateChance() {

        mobChestplateChance = new AstroInteger(this, "Inventory Contents.Chestplate.Chance", 1, 100, 10);
    }

    @Override
    public void setMobLeggingsEnabled() {

        mobLeggingsEnabled = new AstroBoolean(this, "Inventory Contents.Leggings.Enabled", true);
    }

    @Override
    public void setMobLeggingsItem() {

        mobLeggingsItem = new AstroString(this, "Inventory Contents.Leggings.Item", ItemFile.getAllValidLeggingsAndCustomLeggings(), "IRON_LEGGINGS");
    }

    @Override
    public void setMobLeggingsChance() {

        mobLeggingsChance = new AstroInteger(this, "Inventory Contents.Leggings.Chance", 1, 100, 10);
    }

    @Override
    public void setMobBootsEnabled() {

        mobBootsEnabled = new AstroBoolean(this, "Inventory Contents.Boots.Enabled", true);
    }

    @Override
    public void setMobBootsItem() {

        mobBootsItem = new AstroString(this, "Inventory Contents.Boots.Item", ItemFile.getAllValidBootsAndCustomBoots(), "IRON_BOOTS");
    }

    @Override
    public void setMobBootsChance() {

        mobBootsChance = new AstroInteger(this, "Inventory Contents.Boots.Chance", 1, 100, 10);
    }

    @Override
    public void setMobMainHandEnabled() {

        mobMainHandEnabled = new AstroBoolean(this, "Inventory Contents.Main Hand.Enabled", true);
    }

    @Override
    public void setMobMainHandItem() {

        mobMainHandItem = new AstroString(this, "Inventory Contents.Main Hand.Item", ItemFile.getAllValidMatsAndEnabledItems(), "IRON_SWORD");
    }

    @Override
    public void setMobMainHandChance() {

        mobMainHandChance = new AstroInteger(this, "Inventory Contents.Main Hand.Chance", 1, 100, 10);
    }

    @Override
    public void setMobOffHandEnabled() {

        mobOffHandEnabled = new AstroBoolean(this, "Inventory Contents.Off Hand.Enabled", true);
    }

    @Override
    public void setMobOffHandItem() {

        mobOffHandItem = new AstroString(this, "Inventory Contents.Off Hand.Item", ItemFile.getAllValidMatsAndEnabledItems(), "STONE_SWORD");
    }

    @Override
    public void setMobOffHandChance() {

        mobOffHandChance = new AstroInteger(this, "Inventory Contents.Off Hand.Chance", 1, 100, 10);
    }

    @Override
    public void setQuotesOnAttack() {

        quotesOnAttack = new AstroStringList(this, "Quotes.On Attack", null, true, Arrays.asList("AHHH"));
    }

    @Override
    public void setQuotesOnFall() {

        quotesOnFall = new AstroStringList(this, "Quotes.On Fall", null, true, Arrays.asList("Ouchy, that was quite the fall"));
    }

    @Override
    public void setQuotesOnControl() {

        quotesOnControl = new AstroStringList(this, "Quotes.On Control", null, true, Arrays.asList("Your wish is my command"));
    }

    @Override
    public void setQuotesOnTarget() {

        quotesOnTarget = new AstroStringList(this, "Quotes.On Target", null, true, Arrays.asList("I've spotted you!"));
    }

    @Override
    public void setQuotesOnRightClick() {

        quotesOnRightClick = new AstroStringList(this, "Quotes.On Right Click", null, true, Arrays.asList("What you want bruv"));
    }
}
