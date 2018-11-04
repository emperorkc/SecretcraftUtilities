package de.secretcraft.flagBuy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class FlagsCommand implements CommandExecutor {
	static String prefix = UtilitiesConfig.getPrefix();
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		if (p.hasPermission("scu.flags")) {
			if (args.length >= 1) {
				Output.Err(p, "cUse12");
			} else {
				String flags = Config.getData();
				p.sendMessage(prefix+" §bDie folgenden Flags sind kaufbar:§5" + flags + ".");
			}
		} else {
			Output.Err(p, "perm");
		}

		return false;
	}
}
