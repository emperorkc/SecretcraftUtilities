package de.secretcraft.shilfe.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Config {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "SHilfe.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	
	public static void addPlayer(Player p, int Zahl) throws IOException {
		Config.set("Players."+p.getName()+".Zahl", Zahl);
		save();
	}
	public static void addP(Player p) throws IOException {
		Config.set("Players."+p.getName(), null);
		save();
	}
	public static void addDown(Player p) throws IOException {
		Config.set("Players."+p.getName()+".down",1);
		save();
	}
	public static void addUp(Player p) throws IOException {
		Config.set("Players."+p.getName()+".up", 1);
		save();
	}
	public static void addVert(Player p) throws IOException {
		Config.set("Players."+p.getName()+".vert", 1);
		save();
	}
	public static void addMin(Player p, int Zahl) throws IOException {
		Config.set("Players."+p.getName()+".Minimum", Zahl);
		save();
	}
	public static void addMax(Player p, int Zahl) throws IOException {
		Config.set("Players."+p.getName()+".Maximum", Zahl);
		save();
	}
	
	public static int getPlayer(Player p) {
		return (Config.getInt("Players."+p.getName()+".Zahl"));
		
	}
	public static int getMax(Player p) {
		return (Config.getInt("Players."+p.getName()+".Maximum"));
		
	}
	public static int getMin(Player p) {
		return (Config.getInt("Players."+p.getName()+".Minimum"));
		
	}
	public static void addRegion(Player p, String name) throws IOException {
		Config.set("Players."+p.getName()+".Region", name);
		save();
	}
	public static String getRegion(Player p) {
		return (Config.getString("Players."+p.getName()+".Region"));
		
	}
	public static int getUp(Player p) {
		return (Config.getInt("Players."+p.getName()+".up"));
		
	}
	public static int getDown(Player p) {
		return (Config.getInt("Players."+p.getName()+".down"));
		
	}
	public static int getVert(Player p) {
		return (Config.getInt("Players."+p.getName()+".vert"));
		
	}

	
}
