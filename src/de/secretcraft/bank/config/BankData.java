package de.secretcraft.bank.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BankData {
	public static void addMoney(OfflinePlayer p, double money ) throws IOException {
		
	
	File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
	FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	Config.set("Kontostand", Config.getDouble("Kontostand")+money);
	
		Config.save(ConfigFile);
	}
	
	public static void Einrichten(OfflinePlayer p) throws IOException {
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Eingerichtet", 1);
		
			Config.save(ConfigFile);
	}
	public static boolean isEingerichtet(OfflinePlayer p) {
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		if(Config.getInt("Eingerichtet")==1) {
		return true;
	}
	return false;
		
			
	}
	public static void subMoney(OfflinePlayer p, double money ) throws IOException {
		
		
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Kontostand", Config.getDouble("Kontostand")-money);
		
			Config.save(ConfigFile);
		}
	public static double getMoney(OfflinePlayer p) {

		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		return Config.getDouble("Kontostand");	
	}
	public static void deaktivieren(OfflinePlayer p) throws IOException {
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Deaktiviert", 1);
		Config.save(ConfigFile);
	}
	public static void aktivieren(OfflinePlayer p) throws IOException {
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Deaktiviert", 0);
		Config.save(ConfigFile);
	}
	public static boolean isDeaktiviert(OfflinePlayer p) {
		File ConfigFile = new File("plugins/Konto/Spieler", p.getUniqueId().toString()+".yml");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		if(Config.getInt("Deaktiviert")==1) {
			return true;
		}
		return false;
	}
	
}
