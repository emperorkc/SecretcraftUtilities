package de.secretcraft.voteStreaks.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;

public class RemoveRewardCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) sender;
		if(p.hasPermission("scu.removereward")) {
			if(VotedPlayers.getRewardAttach(Integer.parseInt(args[0]))!=null) {
				try {
					VotedPlayers.setNull(Integer.parseInt(args[0]));
					p.sendMessage(prefix+" §6Belohnung von Tag §e"+Integer.parseInt(args[0])+"§6 gelöscht.");
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
