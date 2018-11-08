package de.secretcraft.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class UtilitiesConfig {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Nachrichten");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static void setPrefix(String Prefix) throws IOException {
		Config.set("Prefix", Prefix);
		save();
	}

	public static void setMax(int Prefix) throws IOException {
		Config.set("MaxEntitiesPerChunk", Prefix);
		save();
	}

	public static void setMoney(double Prefix) throws IOException {
		Config.set("Money", Prefix);
		save();
	}

	public static void setToken(double Prefix) throws IOException {
		Config.set("Token", Prefix);
		save();
	}

	public static void setFactor(double Prefix) throws IOException {
		Config.set("Factor", Prefix);
		save();
	}

	public static void setMultiplier(double multiplier) throws IOException {
		Config.set("Multiplier", multiplier);
		save();
	}

	public static double getMoney() {
		return (Config.getDouble("Money"));
	}

	public static int getMax() {
		return (Config.getInt("MaxEntitiesPerChunk"));
	}

	public static double getFactor() {
		return (Config.getDouble("Factor"));
	}

	public static double getToken() {
		return (Config.getDouble("Token"));
	}

	public static double getMultiplier() {
		return (Config.getDouble("Multiplier"));
	}

	public static String getPrefix() {
		return (Config.getString("Prefix"));
	}

}
