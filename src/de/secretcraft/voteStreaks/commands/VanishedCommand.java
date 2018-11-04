package de.secretcraft.voteStreaks.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.eventplaner.listeners.ItemListener;
import de.secretcraft.main.UtilitiesConfig;

public class VanishedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix=UtilitiesConfig.getPrefix();
	
		Player p=(Player) arg0;
		if(p.hasPermission("scu.*")) {
			if(arg3.length>0) {
			ItemListener.Config.set("Globalcount", 0);
			ItemListener.Config.set("Spieler",null);
			try {
				ItemListener.Config.save(ItemListener.ConfigFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.sendMessage(prefix+"§6 Kürbissecounter resettet!");
		}else{p.sendMessage(prefix+" §4Itemcounter wirklich zurücksetzen? /preset Confirm");}}
		return false;
	}

}
