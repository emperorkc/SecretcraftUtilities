package de.secretcraft.voteStreaks.listeners;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class VoteSaves {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities/VoteData", "VoteList.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	static LocalDate localDate = LocalDate.now();

	static String date = DateTimeFormatter.ofPattern("ddMM").format(localDate);
	public static  void addVote(OfflinePlayer p) throws IOException {
		String u = p.getUniqueId().toString();
		if(Config.getInt(date+"."+u)==0) {
			Config.set(date+"."+u, 1);
		}else {
		Config.set(date+"."+u, Config.getInt(date+"."+u)+1);
	}save();}
	public static int getVotes(OfflinePlayer p, String date) {
		if(Config.getInt(date+"."+p.getUniqueId().toString())==0) {
			return 0;
		} else {
			return Config.getInt(date+"."+p.getUniqueId().toString());
		}
	}
}
