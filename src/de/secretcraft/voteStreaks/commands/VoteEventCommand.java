package de.secretcraft.voteStreaks.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class VoteEventCommand implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) sender;
		if(p.hasPermission("scu.voteevent")) {
		if(args.length>0) {
			switch(args[0]) {
			case "start":
				
				
					try {
						UtilitiesConfig.setMultiplier(10);
						Bukkit.broadcastMessage(
								"§8» §8§m-------------§r §7— §6§lVote-Event §7— §8§m-------------§r §8«");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage("§8x §7Die Belohnungen der 30-minütigen Vote-Auswertung werden erhöht!");
						Bukkit.broadcastMessage("§8x §7Gestartet von:§9 "+p.getName());
						Bukkit.broadcastMessage("");
						
						Bukkit.broadcastMessage(
								"§8x §2Du erhältst §3§l"+10+"§3x §2 soviele Token und SD!");
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(
								"§8» §8§m-------------§r §7— §6§lVote-Event §7— §8§m-------------§r §8«");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				break;
			case "stop":
				try {
					UtilitiesConfig.setMultiplier(1);
					Bukkit.broadcastMessage(
							"§8» §8§m-------------§r §7— §6§lVote-Event §7— §8§m-------------§r §8«");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("§8x §7Das Vote-Event wurde beendet!");
				
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(
							"§8» §8§m-------------§r §7— §6§lVote-Event §7— §8§m-------------§r §8«");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
		}
		} else {
			Output.Err(p, "perm");
		}
		return false;
		
	}
}
