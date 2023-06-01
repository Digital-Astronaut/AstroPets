package net.mcjustice.astropets.mobs;

import net.mcjustice.astroapi.FileParameters.AstroString;
import net.mcjustice.astroapi.FileParameters.AstroStringList;
import net.mcjustice.astroapi.File.AstroFile;
import net.mcjustice.astroapi.Utils.MobUtils;

import java.util.Arrays;
import java.util.List;

public class AstroPet extends AstroFile {

    private AstroString fileName;
    private AstroString displayName;
    private AstroString petType;
    private AstroStringList owners;
    private AstroStringList petQuotes;


    public AstroPet(String filePath) {
        super(filePath);
    }

    @Override
    public void addParams() {

        fileName = new AstroString(this, "File Name", Arrays.asList(this.getName()), this.getName());
        displayName = new AstroString(this, "Display Name", Arrays.asList("&7&l" + getName().replace(".yml", "")), "&7&l" + getName().replace(".yml", ""));
        petType = new AstroString(this, "Pet Type", MobUtils.getEntityTypeListString(), "CAT");
        owners = new AstroStringList(this, "Owners", null, false, Arrays.asList(".Kittypittt", "Kittypittt"));
        petQuotes = new AstroStringList(this, "Pet Quotes", null, false, Arrays.asList("Meow meow", "Meow meow meow!", "MEOW", "MEOWWWWWWW", "MEWW :3"));
    }

    public String getFileName() {

        return fileName.getCurrentValue().toString();
    }

    public String getDisplayName() {

        return displayName.getCurrentValue().toString();
    }

    public String getPetType() {

        return petType.getCurrentValue().toString();
    }

    public List<String> getOwners() {

        return owners.getCurrentValueCast();
    }

    public List<String> getPetQuotes() {

        return petQuotes.getCurrentValueCast();
    }
}
