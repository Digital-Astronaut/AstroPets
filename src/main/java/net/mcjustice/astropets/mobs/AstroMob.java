package net.mcjustice.astropets.mobs;

import net.mcjustice.astroapi.File.CustomMobAstroFile;
import net.mcjustice.astroapi.FileParameters.*;
import net.mcjustice.astroapi.Utils.MaterialUtils;
import net.mcjustice.astropets.file.ItemFile;

import java.util.Arrays;

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

        spawnChance = new AstroDouble(this, "Spawn Chance", 0D, 100D, 5.0D);
    }

    @Override
    public void setMobDrops() {

//        List<String> validMatsAndRegisteredItems = new ArrayList<>();
//
//        for (String file : ItemFile.getFileNames()) {
//            validMatsAndRegisteredItems.add(file.toUpperCase().replace(".YML", ""));
//        }
//
//        validMatsAndRegisteredItems.addAll(ItemUtils.itemMaterialsList());
//
//        mobDrops = new AstroHashMap<>(this, "Mob Drops", validMatsAndRegisteredItems, , )
//    }
    }

    @Override
    public void setMobHelmet() {

        // Was MaterialUtils.getAllHelmetMaterials()

        mobHelmet = new AstroString(this, "Inventory Contents.Helmet", ItemFile.getAllValidHelmetsAndCustomHelmets(), "LEATHER_HELMET");
    }

    @Override
    public void setMobChestplate() {

        // Was MaterialUtils.getAllChestplateMaterials()
        mobChestplate = new AstroString(this, "Inventory Contents.Chestplate", ItemFile.getAllValidChestplatesAndCustomChestplates(), "LEATHER_CHESTPLATE");
    }

    @Override
    public void setMobLeggings() {

        // Was MaterialUtils.getAllLeggingMaterials()

        mobLeggings = new AstroString(this, "Inventory Contents.Leggings", ItemFile.getAllValidLeggingsAndCustomLeggings(), "LEATHER_LEGGINGS");
    }

    @Override
    public void setMobBoots() {

        // Was MaterialUtils.getAllBootMaterials()

        mobBoots = new AstroString(this, "Inventory Contents.Boots", ItemFile.getAllValidBootsAndCustomBoots(), "LEATHER_BOOTS");
    }

    @Override
    public void setMobMainHand() {

        mobMainHand = new AstroString(this, "Inventory Contents.Main Hand", MaterialUtils.allValidMaterialsStringList(), "STONE_SWORD");
    }

    @Override
    public void setMobOffHand() {

        mobOffHand = new AstroString(this, "Inventory Contents.Off Hand", MaterialUtils.allValidMaterialsStringList(), "CROSSBOW");
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
