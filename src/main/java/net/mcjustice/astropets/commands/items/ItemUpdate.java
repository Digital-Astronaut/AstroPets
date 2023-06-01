package net.mcjustice.astropets.commands.items;

import net.mcjustice.astroapi.Commands.SubCommand;
import net.mcjustice.astroapi.FileParameters.AstroParam;
import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.items.AstroItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ItemUpdate extends SubCommand {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Updates file(s) with values set in-game";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("u", "up");
    }

    @Override
    public String getSyntax() {
        return "/items update";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {

            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length == 1) {
                for (AstroItem item : ItemFile.getItemsMap().values()) {
                    item.getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                }

                p.sendMessage(ChatColor.GREEN + "Successfully updated all Item files with new values!");

            } else if (args.length == 2) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    ItemFile.getItemsMap().get(argFixed).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);

                    p.sendMessage(ChatColor.GREEN + "Successfully updated file " + argFixed + " with new values!");
                } else {
                    p.sendMessage(ChatColor.RED + "An item with that name does not exist.");
                }

            } else if (args.length >=3) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath).overrideFileValueWithMemoryValue();

                        p.sendMessage(ChatColor.GREEN + "Successfully updated parameter at path " + concatenatedPath + " in " + argFixed);
                    } else {
                        p.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Items folder does not contain " + argFixed);
                }
            }

        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length == 1) {
                for (AstroItem item : ItemFile.getItemsMap().values()) {
                    item.getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                }
                ccs.sendMessage(ChatColor.GREEN + "Successfully updated all Item files with new values!");
            } else if (args.length == 2) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    ItemFile.getItemsMap().get(argFixed).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);

                    ccs.sendMessage(ChatColor.GREEN + "Successfully updated file " + argFixed + " with new values!");
                } else {
                    ccs.sendMessage(ChatColor.RED + "An item with that name does not exist.");
                }

            } else if (args.length >=3) {
                String argFixed = FileUtils.checkForYML(args[1].toUpperCase());

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath).overrideFileValueWithMemoryValue();

                        ccs.sendMessage(ChatColor.GREEN + "Successfully updated parameter at path " + concatenatedPath + " in " + argFixed);
                    } else {
                        ccs.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
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
