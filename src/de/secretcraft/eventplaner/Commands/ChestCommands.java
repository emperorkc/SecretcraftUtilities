package de.secretcraft.eventplaner.Commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.secretcraft.eventplaner.listeners.HalloweenListener;
import de.secretcraft.simpleCommands.commands.MenuListenerCommands;

public class ChestCommands implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
	
		if (arg1.getLabel().equals("hc")) {
			MenuListenerCommands m = new MenuListenerCommands("§6§lBelohnung", 6);
de.secretcraft.voteStreaks.commands.List<String> l1=HalloweenListener.getChests();
			int i = 1;
			int j=0;
			int k=0;
			l1.toFirst();
			while (l1.hasAccess()) {
				String ls=l1.getObject();
				if (HalloweenListener.isTrue(p, ls)) {
					ItemStack c = new ItemStack(Material.CHEST);
					ItemMeta cm=c.getItemMeta();
					cm.setDisplayName("§3§l"+ls);
					cm.setLore(Arrays.asList("§4Kiste noch nicht gefunden"));
					c.setItemMeta(cm);
					m.addButton(m.getRow(j), k, c);
					
				} else {
					ItemStack c=new ItemStack(Material.ENDER_CHEST);
					ItemMeta cm=c.getItemMeta();
					cm.setDisplayName("§3§l"+ls);
					cm.setLore(Arrays.asList("§2Kiste gefunden"));
					c.setItemMeta(cm);
					m.addButton(m.getRow(j), k, c);
				}
				l1.next();
				if(k!=8) {
					k++;
				} else {
					k=0;
					j++;
				}
				i++;
			}
			m.open(p);
		}
		if (arg1.getLabel().equals("hcadd")) {
			
			p.sendMessage(" §6Speichere die Kiste mit einem Rechtsklick auf die Kiste.");
			try {
				HalloweenListener.add(arg3[0]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		}
		if (arg1.getLabel().equals("hcdel")) {
			try {
				HalloweenListener.delChest(arg3[0], p);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		
		}
		return false;
	}




	
}
