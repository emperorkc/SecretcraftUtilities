package de.secretcraft.simpleCommands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.simpleCommands.listener.LiftSignListener;

public class HCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix=UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if(p.hasPermission("scu.basics")) {
			p.sendMessage(prefix+" §3Du hast insgesamt §6"+LiftSignListener.getToken(p)+ "§3 Köpfe am Spawn gesammelt!");
		}
		return false;
	}

}
