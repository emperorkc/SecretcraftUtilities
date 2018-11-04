package de.secretcraft.voteStreaks.commands;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.secretcraft.token.config.PlayerTokenData;

public class convertuuidCommand implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p2 = (Player) sender;
		
		List<String> l1= votedToday.getPlayers();
		l1.toFirst();

		while(l1.hasAccess()) {
		
			Player p=Bukkit.getPlayer(l1.getObject());
			OfflinePlayer p1=Bukkit.getOfflinePlayer(l1.getObject());
			if(p!=null) {
			try {
				votedToday.setPlayer(l1.getObject(), p.getUniqueId().toString());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
				try {
					votedToday.setPlayer(l1.getObject(), p1.getUniqueId().toString());
			
							
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			l1.next();
		}
		List<String> l5=new List<>();
		OfflinePlayer[] allplayers = Bukkit.getServer().getOfflinePlayers();
		for(int i=0;allplayers.length-1>=i;i++){
		l5.append(allplayers[i].getUniqueId().toString());
		}
		l5.toFirst();
		
		while(l5.hasAccess()) {
		
		File ConfigFile = new File("plugins/Skript/data/token/accounts", l5.getObject()+".yml");
		
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		
		try {
			PlayerTokenData.addToken(Bukkit.getOfflinePlayer(UUID.fromString(l5.getObject())), Config.getInt("balance"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} l5.next();
		}
		try {
			votedToday.setNull();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
