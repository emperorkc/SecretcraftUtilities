package de.secretcraft.voteStreaks.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.listeners.VoteSaves;

public class VotedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if(p.hasPermission("scu.dev")) {
		if(arg3.length>0) {
			
			
			OfflinePlayer p1 = Bukkit.getOfflinePlayer(arg3[0]);
			if(arg3.length>1)
			if(Integer.parseInt(arg3[1])!=0) {
				String day= arg3[1].substring(0, 1);
				String month= arg3[1].substring(2, 3);
				p.sendMessage(prefix+" §6"+p.getName()+" hat am §e"+day+ "."+month+".§4 "+ VoteSaves.getVotes(p1, arg3[1])+ "§6 mal gevotet");
			} else {
				p.sendMessage(prefix+ " §cKorrekte Nutzung: /voted <Spielername> <datum> (Datum: TagMonat z.b. 0911 = 09.11)");
			}
		} else {
			p.sendMessage(prefix+ " §cKorrekte Nutzung: /voted <Spielername> <datum> (Datum: TagMonat z.b. 0911 = 09.11)");
		}}
		return false;
	}

}
