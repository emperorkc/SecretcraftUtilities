package de.secretcraft.flagBuy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.Output;

public class AvaibleFlagsCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		if (p.hasPermission("scu.avaibleflags")) {
			if (args.length >= 1) {
				Output.Err(p, "cUse1");
			} else {

				Output.suc(p, "supPl");
			}
		} else {
			Output.Err(p, "perm");
		}

		return false;
	}
}
