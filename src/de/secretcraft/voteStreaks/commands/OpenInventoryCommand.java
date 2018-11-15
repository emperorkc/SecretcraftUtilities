package de.secretcraft.voteStreaks.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.listeners.MenuListener;

public class OpenInventoryCommand implements CommandExecutor {
	static String prefix = UtilitiesConfig.getPrefix();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		if (p.hasPermission("scu.serie")) {
			if (args.length == 0) {
				int Streak = votedToday.getStreak(p);
				List<Integer> l1 = VotedPlayers.getListDays();
				// if(votifierListener.isInList(l1,votedToday.getStreak(p))) {
				l1.toFirst();
				ItemStack item = null;

				MenuListener menu = new MenuListener("§3§lBelohnungen        §2Serie:§4§l " + votedToday.getStreak(p),
						6);
				int j = 1;
				int k = 1;

				while (l1.hasAccess()) {

					int i = l1.getObject();

					if (VotedPlayers.getPermission(p, i) == null) {
						if (!VotedPlayers.getRewardID(p, i).equals("LEGACY_SKULL_ITEM")) {

							if (i <= votedToday.getStreak(p)) {
								item = addRewardCommand.getItem(VotedPlayers.getRewardName(p, i),
										VotedPlayers.getRewardID(p, i), i, p, VotedPlayers.getRewardAmount(p, i));
								item.setAmount(VotedPlayers.getRewardAmount(p, i));
								ItemMeta h = item.getItemMeta();
								if (VotedPlayers.getRewardName(p, i).equals("Kleine Belohnung")) {
									Material m = Material.getMaterial(VotedPlayers.getRewardID(p, i));
									ItemStack n = new ItemStack(m);

									h.setLore(Arrays.asList(n.getType().name()));
									item.setItemMeta(h);
								}
							} else {
								item = new ItemStack(Material.REDSTONE_BLOCK);
								ItemMeta g = item.getItemMeta();
								g.setDisplayName(VotedPlayers.getRewardAttach(i));
								g.setLore(Arrays.asList(VotedPlayers.getRewardName(p, i)));
								item.setItemMeta(g);
							}

							menu.addButton(menu.getRow(j), k, new ItemStack(item));

							if (k == 7) {
								k = 1;

								j++;
							} else {
								k++;
							}
						} else {

							if (i <= votedToday.getStreak(p)) {
								item = addRewardCommand.giveItemHead(p, p.getName(), i);
								ItemMeta g = item.getItemMeta();
								g.setDisplayName(VotedPlayers.getRewardAttach(i));
								g.setLore(Arrays.asList("§2Kopf von §3§l" + p.getName()));
								item.setItemMeta(g);

							} else {
								item = new ItemStack(Material.REDSTONE_BLOCK);
								ItemMeta g = item.getItemMeta();
								g.setDisplayName(VotedPlayers.getRewardAttach(i));
								g.setLore(Arrays.asList("§2Kopf von §3§l" + p.getName()));
								item.setItemMeta(g);
							}

							menu.addButton(menu.getRow(j), k, new ItemStack(item));

							if (k == 7) {
								j++;
								k = 1;
							} else {
								k++;
							}
						}
						l1.next();
					} else {

						if (i <= votedToday.getStreak(p)) {
							item = new ItemStack(Material.PAPER);
							ItemMeta g = item.getItemMeta();
							g.setDisplayName(VotedPlayers.getRewardAttach(i));
							g.setLore(Arrays.asList(VotedPlayers.getRewardName(p, i)));
							item.setItemMeta(g);
							menu.addButton(menu.getRow(j), k, new ItemStack(item));
						} else {
							item = new ItemStack(Material.REDSTONE_BLOCK);
							ItemMeta g = item.getItemMeta();
							g.setDisplayName(VotedPlayers.getRewardAttach(i));
							g.setLore(Arrays.asList(VotedPlayers.getRewardName(p, i)));
							item.setItemMeta(g);
							menu.addButton(menu.getRow(j), k, new ItemStack(item));
						}
						if (k == 7) {
							k = 1;

							j++;
						} else {
							k++;
						}
						l1.next();
					}
				}
				for (int z = j; z <= 4; z++) {
					for (int y = 1; y <= 7; y++) {
						if (k > 1) {
							y = k;
							k = 1;
						}
						ItemStack s = new ItemStack(Material.REDSTONE_BLOCK);
						ItemMeta o = s.getItemMeta();
						o.setDisplayName("§4§l§4§lNoch nicht hinzugefügt.");
						s.setItemMeta(o);
						menu.addButton(menu.getRow(z), y, new ItemStack(s));
					}
				}

				menu.open(p.getPlayer());

			} else {
				if (args.length == 1) {
					switch (args[0]) {
					case "top":
						List<String> l1 = votedToday.getPlayers();
						l1.toFirst();

						List<String> l2 = new List<>();
						List<String> l4 = votedToday.getPlayers();
						String uuid1 = "";
						UUID id1 = null;
						for (int i = 1; i <= 10; i++) {
							if (!l1.isEmpty()) {
								int largest = 0;
								String largestP = null;

								l1.toFirst();
								while (l1.hasAccess()) {
									Player u = Bukkit.getPlayer(UUID.fromString(l1.getObject()));
									OfflinePlayer u1 = Bukkit.getOfflinePlayer(UUID.fromString(l1.getObject()));
									if (u != null) {
										if (votedToday.getStreak(u) > largest) {
											largest = votedToday.getStreak(u);
											largestP = l1.getObject();

										}
									} else {

										if (votedToday.getStreak(u1) > largest) {
											largest = votedToday.getStreak(u1);
											largestP = l1.getObject();

										}
									}
									l1.next();

								}
								l1.toFirst();
								while (l1.hasAccess()) {
									if (l1.getObject().equals(largestP)) {
										l1.remove();
									}
									l1.next();
								}

								l2.append(largestP);
							}
						}

						p.sendMessage("§3----§2 Die 10 höchsten Vote-Serien §3----");
						l2.toFirst();
						for (int i = 1; i <= 10; i++) {
							if (!l2.isEmpty()) {
								Player u = Bukkit.getPlayer(UUID.fromString(l2.getObject()));
								OfflinePlayer u1 = Bukkit.getOfflinePlayer(UUID.fromString(l2.getObject()));
								l2.toFirst();
								String player1 = null;

								if (u != null) {
									p.sendMessage("         §2" + i + ". §3" + u.getName() + ": §4"
											+ votedToday.getStreak(u) + "§2 Tage");
								} else {
									if (u1.getName() != null) {
										p.sendMessage("         §2" + i + ". §3" + u1.getName() + ": §4"
												+ votedToday.getStreak(u1) + "§2 Tage");
									}
								}
								l2.remove();
							}
						}

						break;

					default:
						String playername = args[0];
						Player u = Bukkit.getPlayer(playername);
						List<String> l3 = votedToday.getPlayers();
						boolean tz = false;
						String uuid = "";
						UUID id = null;

						l3.toFirst();
						while (l3.hasAccess()) {
							String player1 = null;

							String uuidvar = l3.getObject();
							OfflinePlayer[] allplayers = Bukkit.getServer().getOfflinePlayers();
							for (int i = 0; allplayers.length - 2 >= i; i++) {
								String uui1d = allplayers[i].getUniqueId().toString();

								if (uuidvar.equals(uui1d)) {
									player1 = allplayers[i].getName();
								}
							}
							if (player1 != null) {

								if (player1.toLowerCase().equals(playername.toLowerCase())) {
									tz = true;
									uuid = l3.getObject();
									id = UUID.fromString(uuid);
								}
							}
							l3.next();
						}

						if ((Bukkit.getPlayer(playername) != null) || tz == true) {
							Player h = Bukkit.getPlayer(playername);
							if (h == null) {
								OfflinePlayer h1 = Bukkit.getOfflinePlayer(id);
								p.sendMessage(prefix + "§3" + h1.getName() + " §2hat eine Vote-Serie von§4§l "
										+ votedToday.getStreak(h1) + "§2!");
							} else {
								p.sendMessage(prefix + "§3" + h.getName() + " §2hat eine Vote-Serie von§4§l "
										+ votedToday.getStreak(h) + "§2!");
							}

						}
						break;
					}
				} else {
					p.sendMessage(prefix + " §cKorrekte Nutzung: /serie oder /serie top oder /serie <spielername>");
				}
			}
		} else {
			p.sendMessage(prefix + "§c Du hast hierzu keine Rechte.");
		}
		return false;

	}

	public String getName(String uuid) {
		try {
			URL api = new URL("https://mcapi.ca/name/uuid/" + uuid);
			BufferedReader reader = new BufferedReader(new InputStreamReader(api.openStream()));

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(reader);
			JSONObject jsonobj = (JSONObject) obj;
			String name = (String) jsonobj.get("name");

			reader.close();
			return name;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isAllowed(ItemStack item, Player p) {
		ItemMeta g = item.getItemMeta();
		Arrays List = (Arrays) g.getLore();
		p.sendMessage(List.toString());
		return true;
	}

}
