package de.secretcraft.voteStreaks.commands;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public class VotedPlayers {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Voterewards.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static List<Integer> getListDays() {
		List<Integer> l1 = new List<>();
		for (int i = 0; i < 1000; i++) {
			if (Config.getString("Rewards.Day." + i + ".attach") != null) {
				l1.append(i);
			}

		}
		return l1;
	}

	public static void setNull(int Day) throws IOException {
		Config.set("Rewards.Day." + Day, null);
		Config.set("Enchants." + Day, null);
		save();
	}

	public static void addReward(Player p, String ItemID, Map<Enchantment, Integer> Enchantments, int amount,
			String name, int Day) throws IOException {
		Config.set("Rewards.Day." + Day, null);
		Config.set("Enchants." + Day, null);
		for (Entry<Enchantment, Integer> entry : Enchantments.entrySet()) {

			String key = entry.getKey().getName();
			int value = entry.getValue();

			Config.set("Enchants." + Day + ".enchantment." + key, "1");
			Config.set("Enchants." + Day + ".enchantment." + key + ".val", value);
			save();
		}

		Config.set("Rewards.Day." + Day + ".ID", ItemID);
		Config.set("Rewards.Day." + Day + ".amount", amount);
		Config.set("Rewards.Day." + Day + ".name", name);
		Config.set("Rewards.Day." + Day + ".attach", "§3§l" + Day + "§2 Tage hintereinander gevotet");

		save();
	}

	public static String getPermission(Player p, int Day) {
		return (Config.getString("Rewards.Day." + Day + ".Permission"));
	}

	public static void addPermission(Player p, int Day, String permission) throws IOException {
		Config.set("Rewards.Day." + Day + ".Permission", permission);
		Config.set("Rewards.Day." + Day + ".attach", "§2" + Day + " Tage hintereinander gevotet");
		Config.set("Rewards.Day." + Day + ".name", "§2" + permission + "-Rechte");
		save();
	}

	public static void addHead(Player p, int Day) throws IOException {
		Config.set("Rewards.Day." + Day + ".ID", "LEGACY_SKULL_ITEM");
		Config.set("Rewards.Day." + Day + ".attach", "§3" + Day + "§2 Tage hintereinander gevotet");
		save();
	}

	public static String getRewardName(Player p, int Day) {

		return (Config.getString("Rewards.Day." + Day + ".name"));
	}

	public static int getRewardAmount(Player p, int Day) {

		return (Config.getInt("Rewards.Day." + Day + ".amount"));
	}

	public static String getRewardAttach(int Day) {

		return (Config.getString("Rewards.Day." + Day + ".attach"));
	}

	public static String getRewardID(Player p, int Day) {
		return (Config.getString("Rewards.Day." + Day + ".ID"));
	}

	public static List<String> getEnchants(int Day) {
		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Enchants." + Day + ".enchantment.");
		if (Config.getString("Enchants." + Day + ".enchantment") != null) {
			for (String k : s.getKeys(false)) {
				l1.append(k);
			}
		}
		return l1;
	}

	public static List<Integer> getEnchantmentLevel(Player p, int Day) {
		int i = 0;

		List<Integer> l2 = new List<>();
		List<String> l1 = getEnchants(Day);
		l1.toFirst();
		while (l1.hasAccess()) {

			if (Config.getInt("Enchants." + Day + ".enchantment." + l1.getObject() + ".val") != 0) {

				int f = Config.getInt("Enchants." + Day + ".enchantment." + l1.getObject() + ".val");

				l2.append(f);
			}
			l1.next();
		}

		return l2;

	}
}
