package net.mcjustice.astropets.listeners;

import net.mcjustice.astroapi.Utils.FileUtils;
import net.mcjustice.astropets.file.PetFile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;
import java.util.Random;

public class PetInteractEvent implements Listener {

    private String previousText;

    @EventHandler
    public void onPetInteract(PlayerInteractEntityEvent e) {

        Player p = e.getPlayer();

//        AstroStringList t = (AstroStringList) PetFile.getPetsMap().get(FileUtils.checkForYML("Ophelia").toUpperCase()).getParamByPath("Pet Quotes");
//        AstroStringList x = (AstroStringList) PetFile.getPetsMap().get(FileUtils.checkForYML("Ophelia").toUpperCase()).getParamByPath("Owners");
//        AstroString test = (AstroString) PetFile.getPetsMap().get(FileUtils.checkForYML("Ophelia").toUpperCase()).getParamByPath("Display Name");

//        if (e.getHand() == EquipmentSlot.OFF_HAND) {
//
//            p.sendMessage(ChatColor.translateAlternateColorCodes('&', test.getCurrentValue().toString()));
//        }

//        for (String s : x.getCurrentValueCast()) {
//            p.sendMessage(s);
//        }
//        if (t instanceof AstroStringList l) {
////            p.sendMessage(t + " is an AstroStringList");
//
////            p.sendMessage("Current value: " + List.of(l.getCurrentValue()));
//
//            for (String s : ((AstroStringList) t).getCurrentValueCast()) {
//                p.sendMessage(s);
//            }
//        } else {
//            p.sendMessage("" + t.getClass().getName());
//        }

//        if (e.getRightClicked().getCustomName() != null) {

//            String nameNoColor = ChatColor.stripColor(e.getRightClicked().getCustomName()).replace("", "");

        if (e.getHand() == EquipmentSlot.OFF_HAND) {

            Random r = new Random();

            String output;

            List<String> test = PetFile.getPetsMap().get(FileUtils.checkForYML("Ophelia").toUpperCase()).getPetQuotes();

            int i = (int) (Math.random() * test.size());

            output = test.get(i);

            if (previousText != null) {

                if (output.equalsIgnoreCase(previousText)) {

                    output = test.get(i);

                    System.out.println(output + " next?");
                }
            }
            if (output.contains("<Player>")) {
                output = output.replace("<Player>", p.getName());
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', output));

            previousText = output;
        }
    }
}
