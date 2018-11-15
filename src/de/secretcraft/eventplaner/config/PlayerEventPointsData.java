package de.secretcraft.eventplaner.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.secretcraft.voteStreaks.commands.List;


public class PlayerEventPointsData {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "PlayerPointData.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	
	public static void addPoints(OfflinePlayer p, double number) throws IOException {
		
		Config.set("Spieler."+p.getUniqueId().toString()+".Points", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Points")+number);
		
	save();
	}
public static void addPoints(Player p, double number) throws IOException {
		
		Config.set("Spieler."+p.getUniqueId().toString()+".Points", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Points")+number);
		
	save();
	}

	
	public static double getPoints(OfflinePlayer p) {
		return(Config.getDouble("Spieler."+p.getUniqueId().toString()+".Points"));
	}
	public static void subPoints(OfflinePlayer p, double number) throws IOException {
		Config.set("Spieler."+p.getUniqueId().toString()+".Points", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Points")-number);
	save();
	}
	public static List<String> getPlayers() {
		List<String> l1 = new List<>();
		ConfigurationSection s=Config.getConfigurationSection("Spieler.");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}
		
		return l1;
	}
	
	
}
