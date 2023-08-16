package net.mcjustice.astropets.commands.items;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astroapi.inventory.MenuManager;
import net.mcjustice.astropets.inventory.Items.ItemsMainMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class ItemMenu extends SubCommand {

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Retrieves a list of all items";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("m", "me", "gui");
    }

    @Override
    public String getSyntax() {
        return "/items menu";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {

        if (commandSender instanceof Player p) {
            new ItemsMainMenu(MenuManager.getPlayerMenuUtility(p)).open();
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
