package net.mcjustice.astropets.commands.custommobs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astroapi.fileparameters.AstroParam;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.MobUtils;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomMobUpdate extends SubCommand {

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "Updates custom mobs file(s) with values set in-game";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("u", "up");
    }

    @Override
    public String getSyntax() {
        return "/mobs update";
    }

    // Add ability to update all mob files within a certain directory
    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {

            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            if (args.length == 1) {

                for (AstroMob mob : CustomMobFile.getCustomMobsMapMappedToFolders().values()) {
                    mob.getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                }
                if (CustomMobFile.getCustomMobsMapMappedToFolders().size() == 1) {
                    p.sendMessage(ChatColor.GREEN + "Successfully updated " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " file!");
                } else {
                    p.sendMessage(ChatColor.GREEN + "Successfully updated " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " files!");
                }

            } else if (args.length == 2) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(argFixed)) {

                    if (CustomMobFile.customMobsMapMappedToFolderContainsMultiple(argFixed)) {

                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There are multiple mobs with that name.");
                        p.sendMessage(ChatColor.YELLOW + "Select from the list below by clicking the proper mob or by typing in the mob type and then the name");
                        p.sendMessage(ChatColor.YELLOW + "/mobs update <Mob_Type> {Mob_Name}");
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            if (entry.getKey().contains(argFixed)) {

                                TextComponent.Builder component = Component.text("(" + entry.getValue().getParentFile().getName() + ") - ", NamedTextColor.LIGHT_PURPLE)
                                        .append(Component.text(entry.getValue().getName().replace(".yml", ""), NamedTextColor.AQUA))
                                        .clickEvent(ClickEvent.runCommand("/mobs update " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", "")))
                                        .hoverEvent(HoverEvent.showText(
                                                Component.text("/mobs update " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", ""), NamedTextColor.AQUA)))
                                        .toBuilder();

                                p.sendMessage(component);


//                                TextComponent component = new TextComponent(ChatColor.LIGHT_PURPLE + "(" + entry.getValue().getParentFile().getName() + ") - " + ChatColor.AQUA + entry.getValue().getName().replace(".yml", ""));
//                                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mobs update " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", "")));
//                                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.AQUA + "/mobs update " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", ""))));
//                                p.spigot().sendMessage(component);
                            }
                        }
                    } else {
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                        }
                        p.sendMessage(ChatColor.GREEN + "Successfully updated file " + argFixed + " with new values!");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                }

            } else if (args.length == 3) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobType)) {

                        if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobName)) {

                            for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                            }
                            p.sendMessage(ChatColor.GREEN + "Successfully updated file " + mobName + " in folder " + mobType);
                        } else {
                            p.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That mob type does not exist.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "No such mob type exists.");
                }

            } else if (args.length >= 4) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKeys(mobType, mobName)) {

                        String[] pathArgs = new String[args.length - 3];

                        for (int i = 3; i < args.length; i++) {
                            pathArgs[i - 3] = args[i];
                        }

                        String concatenatedPath = String.join(" ", pathArgs);

                        AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(mobType, mobName);

                        if (mob.getParamByPath(concatenatedPath) != null) {
                            mob.getParamByPath(concatenatedPath).overrideFileValueWithMemoryValue();

                            p.sendMessage(ChatColor.GREEN + "Successfully updated parameter at path " + concatenatedPath + " in " + mobName);
                        } else {
                            p.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "A mob with that name and type does not exist.");
                    }
                }
            }

        } else if (commandSender instanceof ConsoleCommandSender ccs) {

            if (args.length == 1) {

                for (AstroMob mob : CustomMobFile.getCustomMobsMapMappedToFolders().values()) {
                    mob.getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                }
                if (CustomMobFile.getCustomMobsMapMappedToFolders().size() == 1) {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully updated " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " file!");
                } else {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully updated " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " files!");
                }

            } else if (args.length == 2) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(argFixed)) {

                    if (CustomMobFile.customMobsMapMappedToFolderContainsMultiple(argFixed)) {

                        ccs.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There are multiple mobs with that name.");
                        ccs.sendMessage(ChatColor.YELLOW + "Select from the list below by clicking the proper mob or by typing in the mob type and then the name");
                        ccs.sendMessage(ChatColor.YELLOW + "/mobs update <Mob_Type> {Mob_Name}");
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            if (entry.getKey().contains(argFixed)) {

                                ccs.sendMessage(ChatColor.LIGHT_PURPLE + "(" + entry.getValue().getParentFile().getName() + ") - " + ChatColor.AQUA + entry.getValue().getName().replace(".yml", ""));
                            }
                        }
                    } else {
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                        }
                        ccs.sendMessage(ChatColor.GREEN + "Successfully updated file " + argFixed + " with new values!");
                    }
                } else {
                    ccs.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                }

            } else if (args.length == 3) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobType)) {

                        if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobName)) {

                            for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getParams().forEach(AstroParam::overrideFileValueWithMemoryValue);
                            }
                            ccs.sendMessage(ChatColor.GREEN + "Successfully updated file " + mobName + " in folder " + mobType);
                        } else {
                            ccs.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                        }
                    } else {
                        ccs.sendMessage(ChatColor.RED + "That mob type does not exist.");
                    }
                } else {
                    ccs.sendMessage(ChatColor.RED + "No such mob type exists.");
                }

            } else if (args.length >= 4) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKeys(mobType, mobName)) {

                        String[] pathArgs = new String[args.length - 3];

                        for (int i = 3; i < args.length; i++) {
                            pathArgs[i - 3] = args[i];
                        }

                        String concatenatedPath = String.join(" ", pathArgs);

                        AstroMob mob = CustomMobFile.getCustomMobMappedToFolder(mobType, mobName);

                        if (mob.getParamByPath(concatenatedPath) != null) {
                            mob.getParamByPath(concatenatedPath).overrideFileValueWithMemoryValue();
                            ccs.sendMessage(ChatColor.GREEN + "Successfully updated parameter at path " + concatenatedPath + " in " + mobName);
                        } else {
                            ccs.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                        }
                    } else {
                        ccs.sendMessage(ChatColor.RED + "A mob with that name and type does not exist.");
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
