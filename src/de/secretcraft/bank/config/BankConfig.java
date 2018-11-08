package de.secretcraft.bank.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BankConfig {
	static File ConfigFile = new File("plugins/SecretcraftUtilities/Konto/Config", "Config.yml");
	static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static void setLoss(double loss) throws IOException {
		Config.set("Verlust", loss);
		save();
	}

	public static double getLoss() {
		return (Config.getDouble("Verlust"));
	}

}
