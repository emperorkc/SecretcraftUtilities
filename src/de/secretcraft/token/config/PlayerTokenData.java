package de.secretcraft.token.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.secretcraft.voteStreaks.commands.List;


public class PlayerTokenData {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "PlayerTokenData.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	
	public static void addToken(OfflinePlayer p, double number) throws IOException {
		
		Config.set("Spieler."+p.getUniqueId().toString()+".Token", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Token")+number);
		
	save();
	}
public static void addToken(Player p, double number) throws IOException {
		
		Config.set("Spieler."+p.getUniqueId().toString()+".Token", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Token")+number);
		
	save();
	}

	
	public static double getToken(OfflinePlayer p) {
		return(Config.getDouble("Spieler."+p.getUniqueId().toString()+".Token"));
	}
	public static void subToken(OfflinePlayer p, double number) throws IOException {
		Config.set("Spieler."+p.getUniqueId().toString()+".Token", Config.getDouble("Spieler."+p.getUniqueId().toString()+".Token")-number);
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
