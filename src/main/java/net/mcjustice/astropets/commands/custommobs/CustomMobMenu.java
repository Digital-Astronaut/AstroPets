package net.mcjustice.astropets.commands.custommobs;

import net.mcjustice.astroapi.Commands.SubCommand;
import net.mcjustice.astroapi.Inventory.MenuManager;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CustomMobMenu extends SubCommand {

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public String getDescription() {
        return "Displays the mob editor menu";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("m", "me", "gui");
    }

    @Override
    public String getSyntax() {
        return "/mobs menu";
    }

    @Override
    public void perform(CommandSender commandSender, String[] strings) {

        if (commandSender instanceof Player p) {
            new CustomMobsMainMenu(MenuManager.getPlayerMenuUtility(p)).open();
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
