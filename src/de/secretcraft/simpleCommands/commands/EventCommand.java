package de.secretcraft.simpleCommands.commands;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.Output;

public class EventCommand implements CommandExecutor {
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p=(Player) arg0;
	
		if(p.hasPermission("scu.event")) {
			if(arg3.length>0) {
				String message="§5§l[§9Event§5§l]§r";
				for(int i=0 ; i<arg3.length; i++) {
					
					message=message+" "+ChatColor.translateAlternateColorCodes('&', arg3[i]);
				}
				Bukkit.broadcastMessage(message);
				Collection<? extends Player> m = Bukkit.getOnlinePlayers();
				Iterator<? extends Player> iterator = m.iterator();
				while(iterator.hasNext()) {
					Player o = iterator.next();
					o.playSound(o.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 100, 1);
				}
			}
			} else {
				Output.Err(p, "perm");
			}
		return false;
		}
}
