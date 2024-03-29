package net.mcjustice.astropets.commands.custommobs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.mcjustice.astroapi.commands.SubCommand;
import net.mcjustice.astroapi.utils.FileUtils;
import net.mcjustice.astroapi.utils.MobUtils;
import net.mcjustice.astropets.file.CustomMobFile;
import net.mcjustice.astropets.file.ItemFile;
import net.mcjustice.astropets.mobs.AstroMob;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomMobReload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads custom mobs on the server with values from each file";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("r", "re", "refresh");
    }

    @Override
    public String getSyntax() {
        return "/mobs reload [File_Name] [Mob_Type] [Path]";
    }


    @Override
    public void perform(CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player p) {
            if (!p.isOp()) {
                p.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
                return;
            }

            // /mobs reload <-- first arg
            if (args.length == 1) {

                CustomMobFile.populateCustomMobsMap();

                for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                    entry.getValue().getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                    entry.getValue().getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                    entry.getValue().getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                    entry.getValue().getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                    CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getKey()).reloadParams();
                }

                if (CustomMobFile.getCustomMobsMapMappedToFolders().size() == 1) {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " file!");
                } else {
                    p.sendMessage(ChatColor.GREEN + "Successfully reloaded " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " files!");
                }

            } else if (args.length == 2) {

                String argFixed = FileUtils.checkForYML(args[1]).toUpperCase();

                if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(argFixed)) {

                    if (CustomMobFile.customMobsMapMappedToFolderContainsMultiple(argFixed)) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There are multiple mobs with that name.");
                        p.sendMessage(ChatColor.YELLOW + "Select from the list below by clicking the proper mob or by typing in the mob type and then the name");
                        p.sendMessage(ChatColor.YELLOW + "/mobs reload <Mob_Type> {Mob_Name}");
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            if (entry.getKey().contains(argFixed)) {

                                TextComponent.Builder component = Component.text("(" + entry.getValue().getParentFile().getName() + ") - ", NamedTextColor.LIGHT_PURPLE)
                                        .append(Component.text(entry.getValue().getName().replace(".yml", ""), NamedTextColor.AQUA))
                                        .clickEvent(ClickEvent.runCommand("/mobs reload " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", "")))
                                        .hoverEvent(HoverEvent.showText(Component.text("/mobs reload " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", ""), NamedTextColor.AQUA))).toBuilder();
//                                TextComponent component = new TextComponent(ChatColor.LIGHT_PURPLE + "(" + entry.getValue().getParentFile().getName() + ") - " + ChatColor.AQUA + entry.getValue().getName().replace(".yml", ""));
//                                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mobs reload " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", "")));
//                                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.AQUA + "/mobs reload " + entry.getValue().getParentFile().getName().toUpperCase() + " " + entry.getValue().getName().replace(".yml", ""))));
//                                p.spigot().sendMessage(component);

                                p.sendMessage(component);
                            }
                        }
                    } else {

                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).reloadParams();
                        }
                        p.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                }
            } else if (args.length == 3) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                // Contains the mob type
                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobType)) {

                        if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobName)) {

                            for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).reloadParams();
                            }
                            p.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + mobName + " in folder " + mobType);
                        } else {
                            p.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "That mob type does not exist.");
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "No such mob type exists.");
                }

                // /mobs reload <Mob_Type> {Mob_Name} [Parameter]
                // We will also need to set it up like this
                // /mobs reload {Mob_Name} [Parameter] for the mobs that don't have multiple same-name values.

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
                            mob.getParamByPath(concatenatedPath).onReload();
                            p.sendMessage(ChatColor.GREEN + "Successfully reloaded parameter at path " + concatenatedPath + " in " + mobName);
                        } else {
                            p.sendMessage(ChatColor.RED + "Parameter at path " + concatenatedPath + " does not exist!");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "A mob with that name and type does not exist.");
                    }
                }
            }


        } else if (commandSender instanceof
                ConsoleCommandSender ccs) {

            if (args.length == 1) {

                for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                    entry.getValue().getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                    entry.getValue().getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                    entry.getValue().getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                    entry.getValue().getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                    CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getKey()).reloadParams();
                }

                if (CustomMobFile.getCustomMobsMapMappedToFolders().size() == 1) {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " file!");
                } else {
                    ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded " + CustomMobFile.getCustomMobsMapMappedToFolders().size() + " files!");
                }

            } else if (args.length == 2) {

                String argFixed = (args[1] + ".yml").toUpperCase();

                if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(argFixed)) {

                    if (CustomMobFile.customMobsMapMappedToFolderContainsMultiple(argFixed)) {
                        ccs.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "There are multiple mobs with that name.");
                        ccs.sendMessage(ChatColor.YELLOW + "Select from the list below by typing in the mob type and then the name");
                        ccs.sendMessage(ChatColor.YELLOW + "/mobs reload <Mob_Type> {Mob_Name}");
                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            if (entry.getKey().contains(argFixed)) {

                                ccs.sendMessage(ChatColor.LIGHT_PURPLE + "(" + entry.getValue().getParentFile().getName() + ") - " + ChatColor.AQUA + entry.getValue().getName().replace(".yml", ""));
                            }
                        }
                    } else {

                        for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                            CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).reloadParams();
                        }
                        ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + argFixed);
                    }
                } else {
                    ccs.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                }
            } else if (args.length == 3) {

                String mobType = args[1].toUpperCase();
                String mobName = (args[2] + ".yml").toUpperCase();

                // Contains the mob type
                if (MobUtils.getEntityTypeListString().contains(mobType)) {

                    if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobType)) {

                        if (CustomMobFile.customMobsMapMappedToFoldersContainsPartialKey(mobName)) {

                            for (Map.Entry<String, AstroMob> entry : CustomMobFile.getCustomMobsMapMappedToFolders().entrySet()) {

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobHelmetItem().overrideValidValues(ItemFile.getAllValidHelmetsAndCustomHelmets());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobChestplateItem().overrideValidValues(ItemFile.getAllValidChestplatesAndCustomChestplates());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobLeggingsItem().overrideValidValues(ItemFile.getAllValidLeggingsAndCustomLeggings());
                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).getMobBootsItem().overrideValidValues(ItemFile.getAllValidBootsAndCustomBoots());

                                CustomMobFile.getCustomMobsMapMappedToFolders().get(entry.getValue().getAbsolutePath().toUpperCase()).reloadParams();
                            }
                            ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded file " + mobName + " in folder " + mobType);
                        } else {
                            ccs.sendMessage(ChatColor.RED + "A custom mob with that name does not exist.");
                        }
                    } else {
                        ccs.sendMessage(ChatColor.RED + "That mob type does not exist.");
                    }
                } else {
                    ccs.sendMessage(ChatColor.RED + "No such mob type exists.");
                }

                // /mobs reload <Mob_Type> {Mob_Name} [Parameter]
                // We will also need to set it up like this
                // /mobs reload {Mob_Name} [Parameter] for the mobs that don't have multiple same-name values.

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
                            mob.getParamByPath(concatenatedPath).onReload();
                            ccs.sendMessage(ChatColor.GREEN + "Successfully reloaded parameter at path " + concatenatedPath + " in " + mobName);
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
