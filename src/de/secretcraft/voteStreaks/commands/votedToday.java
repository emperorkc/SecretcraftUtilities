package de.secretcraft.voteStreaks.commands;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class votedToday {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Voters.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static int getStreak(Player p) {

		return (Config.getInt("Spieler." + p.getUniqueId() + ".Streak"));
	}

	public static void setStreak(Player p, int nr) throws IOException {
		Config.set("Spieler." + p.getUniqueId() + ".Streak", nr);
		save();
	}

	public static int getDay(Player p) {
		return (Config.getInt("Spieler." + p.getUniqueId() + ".Tag"));
	}

	public static void addVote(Player p) throws IOException {

		LocalDate localDate = LocalDate.now();

		String date = DateTimeFormatter.ofPattern("MMdd").format(localDate);
		String Months = null;
		String days = null;
		int Month = 0;
		int day = 0;
		if (date.charAt(0) != 0) {
			Months = Character.toString(date.charAt(0)) + Character.toString(date.charAt(1));
			Month = Integer.parseInt(Months);
		} else {
			Months = Character.toString(date.charAt(1));
			Month = Integer.parseInt(Months);
		}
		if (date.charAt(2) != 0) {
			days = Character.toString(date.charAt(2)) + Character.toString(date.charAt(3));
			day = Integer.parseInt(days);
		} else {
			days = Character.toString(date.charAt(3));
			day = Integer.parseInt(days);
		}

		if (Config.getString("Spieler." + p.getUniqueId()) == null) {

			Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
			Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
			Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
			if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null
					&& !votedToday.isPickedUp(p, votedToday.getStreak(p))) {

				Bukkit.broadcastMessage("§3§o" + p.getName() + "§e hat §3§l" + votedToday.getStreak(p)
						+ " §eTage hintereinander gevotet!");
			}
		} else {
			if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") != Month
					|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") != day) {
				if (day != 1) {
					if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month
							&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == day - 1) {

						Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
						Config.set("Spieler." + p.getUniqueId() + ".Tag", day);

						Config.set("Spieler." + p.getUniqueId() + ".Streak",
								Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);

					} else if (Config.getInt("Spieler." + p.getUniqueId() + ".Tag") <= day - 2
							|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") >= day) {

						Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
						Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
						} else {
							Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
						}

					}

				} else {
					if (Month == 2 || Month == 4 || Month == 6 || Month == 9 || Month == 11) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 31) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}
					} else if (Month == 3) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& (Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 28
										|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 29)) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}
					} else if (Month == 5 || Month == 7 || Month == 10) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 30) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}

					} else if (Month == 1) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == 12
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 31) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getName() + ".Streak", 1);
							}
						}
					}
				}
				if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null && !isPickedUp(p, day)) {

					Bukkit.broadcastMessage("§3§o" + p.getName() + "§e hat §3§l" + votedToday.getStreak(p)
							+ " §eTage hintereinander gevotet!");
				}
			}
		}
		save();
	}

	public static void doThis(Player p) throws IOException {

		Config.set("Message." + p.getName(), 1);
		save();
	}

	public static int getPlayersCount(OfflinePlayer p) {
		return (Config.getInt("Message." + p.getName()));
	}

	public static int getPlayersCount(Player p) {

		return (Config.getInt("Message." + p.getName()));
	}

	public static void addVotesCount(OfflinePlayer p) throws IOException {

		Config.set("Message." + p.getName(), Config.getInt("Message." + p.getName()) + 1);
		save();
	}

	public static void addVotesCount(Player p) throws IOException {

		Config.set("Message." + p.getName(), Config.getInt("Message." + p.getName()) + 1);
		save();
	}

	public static void doThis(OfflinePlayer p) throws IOException {

		Config.set("Message." + p.getName(), 1);
		save();
	}

	public static void pickUp(Player p, int Day) throws IOException {
		Config.set("Pickups." + p.getUniqueId() + ".Day." + Day, 1);
		save();
	}

	public static boolean isPickedUp(Player p, int Day) throws IOException {
		return (Config.getInt("Pickups." + p.getUniqueId() + ".Day." + Day) == 1);
	}

	public static void setDay(Player p) throws IOException {
		Config.set("Spieler." + p.getUniqueId() + ".Tag", Config.getInt("Spieler." + p.getUniqueId() + ".Tag") - 1);
		save();
	}

	public static void addSim(Player p, int Month, int day) throws IOException {

		Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
		Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
		Config.set("Spieler." + p.getUniqueId() + ".Streak", 7);
		save();
	}

	public static List<String> getPickUps() {
		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Pickups.");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}

		return l1;
	}

	public static List<String> getPlayers() {
		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Spieler.");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}

		return l1;
	}

	public static List<String> getVoters() throws IOException {
		List<String> l1 = new List<>();
		if (Config.getString("Message") != null) {
			ConfigurationSection s = Config.getConfigurationSection("Message.");
			for (String k : s.getKeys(false)) {
				l1.append(k);
			}
		}
		return l1;
	}

	public static void removeVoters() throws IOException {
		Config.set("Message", null);
		save();
	}

	public static void setPlayer(String old, String nw) throws IOException {

		Config.set("Spieler." + nw + ".Monat", Config.get("Spieler." + old + ".Monat"));
		Config.set("Spieler." + nw + ".Tag", Config.get("Spieler." + old + ".Tag"));
		Config.set("Spieler." + nw + ".Streak", Config.get("Spieler." + old + ".Streak"));
		Config.set("Spieler." + old, null);
		save();
	}

	public static void setNull() throws IOException {
		Config.set("Pickups", null);
		save();
	}

	public static int getDay(OfflinePlayer c) {
		return (Config.getInt("Spieler." + c.getUniqueId() + ".Tag"));

	}

	public static void addVote(OfflinePlayer p) throws IOException {

		LocalDate localDate = LocalDate.now();

		String date = DateTimeFormatter.ofPattern("MMdd").format(localDate);
		String Months = null;
		String days = null;
		int Month = 0;
		int day = 0;
		if (date.charAt(0) != 0) {
			Months = Character.toString(date.charAt(0)) + Character.toString(date.charAt(1));
			Month = Integer.parseInt(Months);
		} else {
			Months = Character.toString(date.charAt(1));
			Month = Integer.parseInt(Months);
		}
		if (date.charAt(2) != 0) {
			days = Character.toString(date.charAt(2)) + Character.toString(date.charAt(3));
			day = Integer.parseInt(days);
		} else {
			days = Character.toString(date.charAt(3));
			day = Integer.parseInt(days);
		}

		if (Config.getString("Spieler." + p.getUniqueId()) == null) {

			Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
			Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
			Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
			if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null
					&& !votedToday.isPickedUp(p, votedToday.getStreak(p))) {

				Bukkit.broadcastMessage("§3§o" + p.getName() + "§e hat §3§l" + votedToday.getStreak(p)
						+ " §eTage hintereinander gevotet!");
			}
		} else {
			if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") != Month
					|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") != day) {
				if (day != 1) {
					if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month
							&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == day - 1) {

						Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
						Config.set("Spieler." + p.getUniqueId() + ".Tag", day);

						Config.set("Spieler." + p.getUniqueId() + ".Streak",
								Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);

					} else if (Config.getInt("Spieler." + p.getUniqueId() + ".Tag") <= day - 2
							|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") >= day) {

						Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
						Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
						} else {
							Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
						}

					}

				} else {
					if (Month == 2 || Month == 4 || Month == 6 || Month == 9 || Month == 11) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 31) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}
					} else if (Month == 3) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& (Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 28
										|| Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 29)) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}
					} else if (Month == 5 || Month == 7 || Month == 10) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == Month - 1
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 30) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}

					} else if (Month == 1) {
						if (Config.getInt("Spieler." + p.getUniqueId() + ".Monat") == 12
								&& Config.getInt("Spieler." + p.getUniqueId() + ".Tag") == 31) {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							Config.set("Spieler." + p.getUniqueId() + ".Streak",
									Config.getInt("Spieler." + p.getUniqueId() + ".Streak") + 1);
						} else {

							Config.set("Spieler." + p.getUniqueId() + ".Monat", Month);
							Config.set("Spieler." + p.getUniqueId() + ".Tag", day);
							if (Config.getInt("Spieler." + p.getUniqueId() + ".Streak") > 6) {
								Config.set("Spieler." + p.getUniqueId() + ".Streak",
										Config.getInt("Spieler." + p.getUniqueId() + ".Streak") - 6);
							} else {
								Config.set("Spieler." + p.getUniqueId() + ".Streak", 1);
							}
						}
					}
				}
				if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null && !isPickedUp(p, day)) {

					Bukkit.broadcastMessage("§3§o" + p.getName() + "§e hat §3§l" + votedToday.getStreak(p)
							+ " §eTage hintereinander gevotet!");
				}
			}
		}

	}

	static boolean isPickedUp(OfflinePlayer c, int Day) {
		return (Config.getInt("Pickups." + c.getUniqueId() + ".Day." + Day) == 1);
	}

	static int getStreak(OfflinePlayer c) {

		return (Config.getInt("Spieler." + c.getUniqueId() + ".Streak"));
	}

	public static void setStreak1(OfflinePlayer p, int nr) throws IOException {
		Config.set("Spieler." + p.getUniqueId() + ".Streak", nr);
		save();
	}
}
