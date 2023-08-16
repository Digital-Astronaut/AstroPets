package net.mcjustice.astropets.commands.enchantments;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astropets.file.EnchantmentFile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EnchantReload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads enchants on the server with values from each file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "re", "refresh");
    }

    @Override
    public String getSyntax() {
        return "/enchants reload";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length == 1) {
                for (String key : EnchantmentFile.getEnchantsMap().keySet()) {
                    EnchantmentFile.getEnchantsMap().get(key).reloadParams();
                }

                if (EnchantmentFile.getEnchantsMap().size() == 1) {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + EnchantmentFile.getEnchantsMap().size() + "file!");
                } else {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + EnchantmentFile.getEnchantsMap().size() + " files!");
                }

            } else if (args.length == 2) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (EnchantmentFile.getEnchantsMap().containsKey(argFixed)) {
                    EnchantmentFile.getEnchantsMap().get(argFixed).reloadParams();

                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                }
            } else if (args.length >= 3) {
                String argFixed = (args[1] + ".yml").toUpperCase();

                if (EnchantmentFile.getEnchantsMap().containsKey(argFixed)) {
                    String[] pathArgs = new String[args.length - 2];

                    for (int i = 2; i < args.length; i++) {
                        pathArgs[i - 2] = args[i];
                    }

                    String concatenatedPath = String.join(" ", pathArgs);

                    if (EnchantmentFile.getEnchantsMap().get(argFixed).getParamByPath(concatenatedPath) != null) {
                        EnchantmentFile.getEnchantsMap().get(argFixed).reloadParam(EnchantmentFile.getEnchantsMap().get(argFixed).getParamByPath(concatenatedPath));

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
