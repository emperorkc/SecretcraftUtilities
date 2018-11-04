package de.secretcraft.voteStreaks.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;

public class setStreakCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) sender;
		if(p.hasPermission("scu.setstreak")) {
			
		
		try {
			Player z=Bukkit.getPlayer(args[0]);
			OfflinePlayer z1=Bukkit.getOfflinePlayer(args[0]);
			if(z!=null) {
			votedToday.setStreak(z, Integer.parseInt(args[1]));p.sendMessage(prefix+"§3 Serie von "+z.getName()+" auf "+Integer.parseInt(args[1])+" gesetzt.");} else {
				votedToday.setStreak1(z1, Integer.parseInt(args[1]));p.sendMessage(prefix+"§3 Serie von "+z1.getName()+" auf "+Integer.parseInt(args[1])+" gesetzt.");
			}
			
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}} else {
			p.sendMessage(prefix+" §cDu hast hierzu keine Rechte.");
		}
		return false;
	}

}
