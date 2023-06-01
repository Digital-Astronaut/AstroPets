package net.mcjustice.astropets.commands.pets;

import net.mcjustice.astroapi.Commands.SubCommand;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.file.PetFile;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PetCreate extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new AstroPet with the specified name";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("c", "new", "make");
    }

    @Override
    public String getSyntax() {
        return "/pets create <Pet Name>";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length > 1) {
                for (String s : PetFile.getPetsMap().keySet()) {
                    if (FileUtils.checkForYML(args[1]).equalsIgnoreCase(s)) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "A pet with that name already exists!");
                        return;
                    }
                }

                FileUtils.createNewFile("Pets", args[1]);

                PetFile.createNewPet(FileUtils.getFilePath("Pets", args[1]));

                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Pet " + args[1] + " created successfully!");
            }
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] strings) {
        return null;
    }

    @Nullable
    @Override
    public TextComponent getSubCommandHelpLayout() {
        return null;
    }

    @Nullable
    @Override
    public HoverEvent getSubCommandHoverEvent() {
        return null;
    }

    @Nullable
    @Override
    public ClickEvent getSubCommandClickEvent() {
        return null;
    }
}
