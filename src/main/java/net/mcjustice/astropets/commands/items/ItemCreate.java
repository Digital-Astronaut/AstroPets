package net.mcjustice.astropets.commands.items;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astropets.file.ItemFile;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ItemCreate extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new Item with the specified name";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("c", "new", "make");
    }

    @Override
    public String getSyntax() {
        return "/items create <Item Name>";
    }

    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length > 1) {
                for (String s : ItemFile.getItemsMap().keySet()) {
                    if (FileUtils.checkForYML(args[1]).equalsIgnoreCase(s)) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "An item with that name already exists!");
                        return;
                    }
                }

                FileUtils.createNewFile("Items", args[1]);

                ItemFile.createNewItem(FileUtils.getFilePath("Items", args[1]));

                p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Item " + args[1] + " created successfully!");

                // TODO: Open item editor after in order to set all the values.
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
