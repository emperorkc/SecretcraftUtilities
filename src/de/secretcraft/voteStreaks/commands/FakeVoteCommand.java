package de.secretcraft.voteStreaks.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;

public class FakeVoteCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player k = (Player) sender;

		String prefix = UtilitiesConfig.getPrefix();
		if (k.hasPermission("scu.fakevote")) {
			if (args.length > 0) {

				LocalDate localDate = LocalDate.now();

				String date = DateTimeFormatter.ofPattern("MMdd").format(localDate);

				String days = null;

				int day = 0;

				if (date.charAt(2) != 0) {
					days = Character.toString(date.charAt(2)) + Character.toString(date.charAt(3));
					day = Integer.parseInt(days);
				} else {
					days = Character.toString(date.charAt(3));
					day = Integer.parseInt(days);
				}
				Player p = null;
				OfflinePlayer p1 = null;

				if (Bukkit.getServer().getPlayer(args[0]) != null) {
					p = Bukkit.getServer().getPlayer(args[0]);

					int i = votedToday.getDay(p) + 1;
					try {
						votedToday.addVote(p);
						Bukkit.broadcastMessage("§6§o" + p.getName()
								+ " §ehat geFAKEvotet und tolle Belohnungen erhalten! Vote auch du mit §6§o/vote§e!");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null
								&& !votedToday.isPickedUp(p, votedToday.getStreak(p))) {
							p.sendMessage(prefix
									+ " §eDu hast eine neue Belohnung freigeschaltet. Hole sie dir bei §6§l/serie§e ab.");
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (votedToday.getPlayersCount(p) == 0) {
						try {
							votedToday.doThis(p);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try {
							votedToday.addVotesCount(p);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				} else {
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

							if (player1.toLowerCase().equals(args[0].toLowerCase())) {
								tz = true;
								uuid = l3.getObject();
								id = UUID.fromString(uuid);
							}
						}
						l3.next();
					}
					if (tz = true) {
						if (p == null) {
							p1 = Bukkit.getServer().getOfflinePlayer(id);
						}
						int i = votedToday.getDay(p1) + 1;
						try {
							votedToday.addVote(p1);
							Bukkit.broadcastMessage("§6§o" + p1.getName()
									+ " §ehat geFAKEvotet und tolle Belohnungen erhalten! Vote auch du mit §6§o/vote§e!");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (votedToday.getPlayersCount(p1) == 0) {
							try {
								votedToday.doThis(p1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							try {
								votedToday.addVotesCount(p1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

			}
		} else {
			k.sendMessage(prefix + "§c Du hast hierzu keine Rechte.");
		}
		return false;

	}

}
