package net.mcjustice.astropets.commands.items;

import net.mcjustice.astroapi.Commands.SubCommand;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.items.AstroItem;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemReload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads items on the server with values from each file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "re", "refresh");
    }

    @Override
    public String getSyntax() {
        return "/items reload";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length == 1) {

                for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {

                    entry.getValue().getIngredientMaterials().forEach(as -> as.overrideValidValues(ItemFile.getAllValidMatsAndEnabledItems()));

                    ItemFile.getItemsMap().get(entry.getKey()).reloadParams();
                }

                if (ItemFile.getItemsMap().size() == 1) {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + ItemFile.getItemsMap().size() + " file!");
                } else {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + ItemFile.getItemsMap().size() + " files!");
                }

            } else if (args.length == 2) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {

                    ItemFile.getItemsMap().get(argFixed).getIngredientMaterials().forEach(as -> as.overrideValidValues(ItemFile.getAllValidMatsAndEnabledItems()));

                    ItemFile.getItemsMap().get(argFixed).reloadParams();

                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                } else {

                    p.sendMessage(ChatColor.RED + "An item with that name does not exist.");
                }

            } else if (args.length >= 3) {

                String argFixed = (args[1] + ".yml").toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        ItemFile.getItemsMap().get(argFixed).reloadParam(ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath));

                        p.sendMessage(ChatColor.GREEN + "Successfully reloaded parameter at path " + concatenatedPath + " in " + argFixed);
                    } else {
                        p.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                    }
                }
            }
        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length == 1) {

                for (Map.Entry<String, AstroItem> entry : ItemFile.getItemsMap().entrySet()) {

                    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "Overriding list now");

                    entry.getValue().getIngredientMaterials().forEach(as -> as.overrideValidValues(ItemFile.getAllValidMatsAndEnabledItems()));

                    ItemFile.getItemsMap().get(entry.getKey()).reloadParams();
                }
                if (ItemFile.getItemsMap().size() == 1) {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded " + ItemFile.getItemsMap().size() + " file!");
                } else {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded " + ItemFile.getItemsMap().size() + " files!");
                }

            } else if (args.length == 2) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {

                    ItemFile.getItemsMap().get(argFixed).getIngredientMaterials().forEach(as -> as.overrideValidValues(ItemFile.getAllValidMatsAndEnabledItems()));

                    ItemFile.getItemsMap().get(argFixed).reloadParams();

                    ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                } else {

                    ccs.sendMessage(ChatColor.RED + "An item with that name does not exist.");
                }

                // /items reload test Particles.Particle Effect
            } else if (args.length >= 3) {

                String argFixed = (args[1] + ".yml").toUpperCase();

                if (ItemFile.getItemsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        ItemFile.getItemsMap().get(argFixed).reloadParam(ItemFile.getItemsMap().get(argFixed).getParamByPath(concatenatedPath));

                        ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded parameter at path " + concatenatedPath + " in " + argFixed);
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