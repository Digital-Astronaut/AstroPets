package net.mcjustice.astropets.commands.enchantments;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astropets.file.EnchantmentFile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EnchantCreate extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new Enchantment with the specified name";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("c", "new", "make");
    }

    @Override
    public String getSyntax() {
        return "/enchants create <Enchantment Name>";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length > 1) {
                for (String s : EnchantmentFile.getEnchantsMap().keySet()) {
                    if (args[1].equalsIgnoreCase(s)) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "An enchant with that name already exists!");
                        return;
                    }
                }

                FileUtils.createNewFile("Custom Enchantments", args[1]);

                EnchantmentFile.createNewEnchantment(FileUtils.getFilePath("Custom Enchantments", args[1]));

                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Custom Enchantment " + args[1] + " created successfully!");
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
