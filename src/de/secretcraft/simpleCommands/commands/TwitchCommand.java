package de.secretcraft.simpleCommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.Output;


public class TwitchCommand implements CommandExecutor {
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p=(Player) arg0;
	
		if(p.hasPermission("scu.basics")) {
			Output.suc(p, "twitch");
			} else {
				Output.Err(p, "perm");
			}
		return false;
		}
}
