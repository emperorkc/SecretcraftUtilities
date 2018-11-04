package de.secretcraft.flagBuy.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Config {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "FlagBuy.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static void addStart() throws IOException {
		Config.set("Flags.greeting.Name", "greeting");
		Config.set("Flags.greeting.Preis", 25000.0);

		save();
	}

	public static void addFlag(String flagname, double preis) throws IOException {
		Config.set("Flags." + flagname + ".Name", flagname);
		Config.set("Flags." + flagname + ".Preis", preis);

		save();
	}

	public static void setForbid(String flagname, String regionname) throws IOException {
		Config.set("Denials." + flagname + "." + regionname, "denied");
		save();
	}

	public static void removeForbid(String flagname, String regionname) throws IOException {

		Config.set("Denials." + flagname + "." + regionname, null);

		save();
	}

	public static void removeFlag(String flagname) throws IOException {
		Config.set("Flags." + flagname + "", null);
		save();

	}

	public static String getData() {
		String data = "";
		if (isInConfig("snow-fall")) {
			data = data + " snow-fall,";
		}
		if (isInConfig("pvp")) {
			data = data + " pvp,";
		}
		if (isInConfig("hostilemobspawn")) {
			data = data + " hostilemobspawn,";
		}
		if (isInConfig("tnt")) {
			data = data + " tnt,";
		}
		if (isInConfig("lighter")) {
			data = data + " lighter,";
		}
		if (isInConfig("creeper-explosion")) {
			data = data + " creeper-explosion,";
		}
		if (isInConfig("other-explosion")) {
			data = data + " other-explosion,";
		}
		if (isInConfig("snow-melt")) {
			data = data + " snow-melt,";
		}
		if (isInConfig("ice-melt")) {
			data = data + " ice-melt,";
		}
		if (isInConfig("build")) {
			data = data + " build,";
		}
		if (isInConfig("mob-spawning")) {
			data = data + " mob-spawning,";
		}
		if (isInConfig("enderpearl")) {
			data = data + " enderpearl,";
		}
		if (isInConfig("ice-form")) {
			data = data + " ice-form,";
		}
		if (isInConfig("greeting")) {
			data = data + " greeting,";
		}
		if (isInConfig("farewell")) {
			data = data + " farewell,";
		}

		return data;
	}

	public static double getPreis(String flagname) {
		return Config.getDouble("Flags." + flagname + ".Preis");
	}

	public static boolean isInConfig(String flagname) {
		if (Config.getString("Flags." + flagname + "") != null) {
			return true;
		}
		return false;
	}

	public static boolean isDenied(String flagname, String regionname) {
		if (Config.getString("Denials." + flagname + "." + regionname) != null) {
			return true;
		}
		return false;
	}

	public static void addRegion(String flagname, String regionname, String einaus) throws IOException {
		if (einaus.equals("ein")) {
			Config.set("Regions." + regionname + "." + flagname + "", "ein");
		} else if (einaus.equals("aus")) {
			Config.set("Regions." + regionname + "." + flagname + "", "aus");
		} else {
			Config.set("Regions." + regionname + "." + flagname + "", einaus);
		}
		save();
	}

	public static void removeRegion(String flagname, String regionname, String einaus) throws IOException {

		Config.set("Regions." + regionname + "." + flagname + "", null);

		save();
	}

	public static boolean hasSameFlag(String flagname, String regionname) {
		boolean wert = false;
		if (Config.getString("Regions." + regionname + "." + flagname + "") != null) {
			wert = true;
		}
		return wert;
	}

	public static void set(String string, double preisdouble) {
		Config.set(string, preisdouble);

	}

	public static boolean getForbidden(String flagname) {

		return (Config.getString("Denials." + flagname + "") != null);

	}

	public static boolean getForbiddenRegion(String flagname, String regionname) {

		return (Config.getString("Denials." + flagname + "." + regionname) != null);

	}
}
