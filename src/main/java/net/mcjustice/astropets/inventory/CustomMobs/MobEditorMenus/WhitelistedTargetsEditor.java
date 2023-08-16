package net.mcjustice.astropets.inventory.CustomMobs.MobEditorMenus;

import net.mcjustice.astroapi.inventory.Menu;
import net.mcjustice.astroapi.inventory.PaginatedMenu;
import net.mcjustice.astroapi.utils.MobUtils;
import net.mcjustice.astroapi.utils.PlayerMenuUtility;
import net.mcjustice.astroapi.utils.TextUtils;
import net.mcjustice.astropets.inventory.CustomMobs.CustomMobsMainMenu;
import net.mcjustice.astropets.mobs.AstroMob;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WhitelistedTargetsEditor extends PaginatedMenu {

    private AstroMob selectedMob;

    public WhitelistedTargetsEditor(PlayerMenuUtility playerMenuUtility, AstroMob selectedMob, Menu previousMenu) {
        super(playerMenuUtility, previousMenu);

        this.selectedMob = selectedMob;

        setMainMenu(new CustomMobsMainMenu(playerMenuUtility));
    }

    @Override
    public List<?> getData() {
        return MobUtils.getEntityTypeListString();
    }

    @Override
    public void loopData(Object o) {

        if (o instanceof String s) {

            ItemStack eggItem = MobUtils.getMobDisplayEgg(s);

            ItemMeta eggMeta = eggItem.getItemMeta();

            List<String> lore = new ArrayList<>();
            eggMeta.setDisplayName(ChatColor.AQUA + TextUtils.capitalizeWordAndReplaceDelimiter(s, "_"));

            if (selectedMob.getWhitelistedMobs().getCurrentMemoryValue().contains(s)) {

                lore.add(ChatColor.GREEN + "Currently whitelisted");
                lore.add(ChatColor.YELLOW + "Click to remove this mob type from the whitelist");
                eggMeta.addEnchant(Enchantment.DURABILITY, 1, false);
                eggMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                lore.add(ChatColor.RED + "Not currently whitelisted");
                lore.add(ChatColor.YELLOW + "Click to prevent this custom mob from attacking this mob type");
            }
            eggMeta.setLore(lore);
            eggItem.setItemMeta(eggMeta);

            inventory.setItem(inventory.firstEmpty(), eggItem);
        }
    }

    @Override
    public int getMaxAmountOfDisplayedItems() {
        return getData().size();
    }

    @Override
    public String getMenuName() {
        return ChatColor.BLUE + "" + ChatColor.BOLD +  "Whitelisted Mob Types";
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getRawSlot() < 45) {

            if (e.getCurrentItem().hasItemMeta()) {

                if (!selectedMob.getWhitelistedMobs().getCurrentMemoryValue().contains(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).replace(" ", "_"))) {
                    selectedMob.getWhitelistedMobs().getCurrentMemoryValue().add(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).replace(" ", "_"));
                } else {
                    selectedMob.getWhitelistedMobs().getCurrentMemoryValue().remove(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).replace(" ", "_"));
                }
                open();
            }
        }
    }
}
