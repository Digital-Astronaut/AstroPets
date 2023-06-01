package net.mcjustice.astropets.commands.pets;

import net.mcjustice.astroapi.Commands.SubCommand;
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

public class PetReload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads pets on the server with values from each file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "re", "refresh");
    }

    @Override
    public String getSyntax() {
        return "/pets reload";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length == 1) {
                for (String key : PetFile.getPetsMap().keySet()) {
                    PetFile.getPetsMap().get(key).reloadParams();
                }

                p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + PetFile.getPetsMap().size() + " files");
            } else if (args.length == 2) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (PetFile.getPetsMap().containsKey(argFixed)) {
                    PetFile.getPetsMap().get(argFixed).reloadParams();

                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                }
            } else if (args.length >= 3) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (PetFile.getPetsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (PetFile.getPetsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        PetFile.getPetsMap().get(argFixed).reloadParam(PetFile.getPetsMap().get(argFixed).getParamByPath(concatenatedPath));

                        p.sendMessage(ChatColor.GREEN + "Successfully reloaded parameter at path " + concatenatedPath + " in " + argFixed);
                    } else {
                        p.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                    }
                }
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
