package de.secretcraft.voteStreaks.listeners;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.plugin.RegisteredServiceProvider;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.token.config.PlayerTokenData;
import de.secretcraft.voteStreaks.commands.List;
import de.secretcraft.voteStreaks.commands.VotedPlayers;
import de.secretcraft.voteStreaks.commands.votedToday;

import net.milkbowl.vault.economy.Economy;

public class votifierListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Voteauswertung NICHT BEARBEITEN");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static void ded() throws IOException {

		Collection<? extends Player> m = Bukkit.getOnlinePlayers();
		Iterator<? extends Player> iterator = m.iterator();
		while (iterator.hasNext()) {
			Config.set("Checked." + iterator.next().getName(), 1);
			save();
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) throws IOException {
		if (Config.get("Checked." + e.getPlayer().getName()) != null) {

			Config.set("Checked." + e.getPlayer().getName(), 0);
			save();
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) throws IOException {
		if (Config.get("Checked." + e.getPlayer().getName()) != null) {

			Config.set("Checked." + e.getPlayer().getName(), 0);
			save();
		}
	}

	@EventHandler
	public void onComm(PlayerFishEvent e) throws IOException {
		if (Config.get("Checked." + e.getPlayer().getName()) != null) {

			Config.set("Checked." + e.getPlayer().getName(), 0);
			save();
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws IOException {
		if (Config.get("Checked." + e.getPlayer().getName()) != null) {

			Config.set("Checked." + e.getPlayer().getName(), 0);
			save();
		}
	}

	@EventHandler
	public void vEvent(PlayerCommandPreprocessEvent e) throws IOException {
		if (Config.get("Checked." + e.getPlayer().getName()) != null) {

			Config.set("Checked." + e.getPlayer().getName(), 0);
			save();
		}
	}

	public static void dod() throws IOException {

		List<String> l1 = null;

		try {
			l1 = votedToday.getVoters();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> l2 = null;
		try {
			l2 = votedToday.getVoters();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String voters = "";
		l1.toFirst();

		l2.toFirst();

		l2.next();

		l1.toFirst();
		int count = 0;
		while (l1.hasAccess()) {
			if (Bukkit.getPlayer(l1.getObject()) != null) {
				count = count + votedToday.getPlayersCount(Bukkit.getPlayer(l1.getObject()));
			} else {
				count = count + votedToday.getPlayersCount(Bukkit.getOfflinePlayer(l1.getObject()));
			}
			l1.next();
		}

		l1.toFirst();
		if (l1.getObject() != null) {

			l1.next();
			Collection<? extends Player> m = Bukkit.getOnlinePlayers();
			Iterator<? extends Player> iterator = m.iterator();

			int j = 0;
			while (iterator.hasNext()) {

				Player p = iterator.next();

				p.playSound(p.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 10, 1);
				if (Config.getInt("Checked." + p.getName()) != 1) {
					j++;
				}
			}

			if (j > 10) {

				j = j / 3;
			} else {
				j = 15;
			}

			double prizepool = Math.pow(count, UtilitiesConfig.getFactor());
			double multipool = UtilitiesConfig.getMoney() * prizepool;
			double tokenpool = UtilitiesConfig.getToken() * prizepool;
			double perplayertoken = (tokenpool / j) * 100;
			double perplayerround1 = ((double) Math.round(perplayertoken) / 100) * UtilitiesConfig.getMultiplier();
			double perplayer = (multipool / j) * 100;
			double perplayerround = ((double) Math.round(perplayer) / 100) * UtilitiesConfig.getMultiplier();

			int l = 1;
			l1.toFirst();
			l2.toFirst();
			l2.next();
			while (l1.hasAccess()) {

				if (l2.getObject() != null) {
					voters = voters + "" + l1.getObject() + ", ";
				} else {
					voters = voters + "" + l1.getObject() + "";
				}

				l1.next();
				l2.next();
			}
			if (perplayerround1 > 100) {
				perplayerround1 = 100;
			}
			if (perplayerround > 30000) {
				perplayerround = 30000;
			}

			Iterator<? extends Player> iterator2 = m.iterator();

			Bukkit.broadcastMessage("§8» §8§m-------------§r §7— §6§lVote-Auswertung §7— §8§m-------------§r §8«");
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§8x §7Die §6Votes §7der letzten 30 Minuten wurden ausgewertet!");
			Bukkit.broadcastMessage("§8x §7Voter:§9 " + voters);
			Bukkit.broadcastMessage("§8x §7Anzahl Votes:§9 " + count);
			Bukkit.broadcastMessage("");
			while (iterator2.hasNext()) {

				Player p = iterator2.next();
				if (Config.getInt("Checked." + p.getName()) != 1) {
					p.sendMessage("§8x §2Du erhältst: §8- §3§l" + perplayerround + "§2 SD");
					p.sendMessage("§8x §2Du erhältst: §8- §3§l" + perplayerround1 + "§2 Token");
				} else {
					p.sendMessage("§8x §2Du erhälst §ckeine Belohnung, §2weil du Afk warst!");
				}
			}
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§8» §8§m-------------§r §7— §6§lVote-Auswertung §7— §8§m-------------§r §8«");

			Iterator<? extends Player> iterator1 = m.iterator();
			RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
					.getRegistration(Economy.class);
			Economy eco = rsp.getProvider();
			while (iterator1.hasNext()) {

				Player p = iterator1.next();
				if (Config.getInt("Checked." + p.getName()) != 1) {
					eco.depositPlayer(p, perplayerround);
					try {
						PlayerTokenData.addToken(p, perplayerround1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		try {
			votedToday.removeVoters();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Config.set("Checked", null);
		save();
	}

	@EventHandler
	public void onPlayerVote(VotifierEvent e) throws IOException {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		Economy eco = rsp.getProvider();
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
		String prefix = UtilitiesConfig.getPrefix();
		Vote v = e.getVote();
		Player p = Bukkit.getServer().getPlayer(v.getUsername());
		OfflinePlayer p1 = null;
		if (p != null) {

			int i = votedToday.getDay(p) + 1;
			try {
				votedToday.addVote(p);

			} catch (IOException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}
			if (day == i) {
				try {
					if (VotedPlayers.getRewardAttach(votedToday.getStreak(p)) != null
							&& !votedToday.isPickedUp(p, votedToday.getStreak(p))) {
						p.sendMessage(prefix
								+ " §eDu hast eine neue Belohnung freigeschaltet. Hole sie dir mit §6§o/serie§e ab.");
						p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
					}
				} catch (IOException n) {
					// TODO Auto-generated catch block
					n.printStackTrace();
				}

			}
			if (votedToday.getPlayersCount(p) == 0) {
				try {
					votedToday.doThis(p);
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			} else {
				try {
					votedToday.addVotesCount(p);
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}

			}

			VoteSaves.addVote(p);
			PlayerTokenData.addToken(p, 1);
			p.sendMessage(prefix + " §3Du hast §71§3 Token erhalten");
			p.sendMessage(prefix + " §3Du §7" + 100 * UtilitiesConfig.getMultiplier() + " §3SD von SC_Staat erhalten");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"cr give to " + p.getName() + " VoteKey " + ((int) UtilitiesConfig.getMultiplier()));
			eco.depositPlayer(p, 100 * UtilitiesConfig.getMultiplier());
			eco.withdrawPlayer(Bukkit.getOfflinePlayer("SC_Staat"), 100 * UtilitiesConfig.getMultiplier());
		} else {

			p1 = Bukkit.getServer().getOfflinePlayer(e.getVote().getUsername());

			try {
				votedToday.addVote(p1);

			} catch (IOException n) {
				// TODO Auto-generated catch block
				n.printStackTrace();
			}

			if (votedToday.getPlayersCount(p1) == 0) {
				try {
					votedToday.doThis(p1);
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}
			} else {
				try {
					votedToday.addVotesCount(p1);
				} catch (IOException f) {
					// TODO Auto-generated catch block
					f.printStackTrace();
				}

			}

			VoteSaves.addVote(p1);
			PlayerTokenData.addToken(p1, 1);

			Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
					"cr give to " + p1.getName() + " VoteKey " + ((int) UtilitiesConfig.getMultiplier()));
			eco.depositPlayer(p1, 100 * UtilitiesConfig.getMultiplier());
			eco.withdrawPlayer(Bukkit.getOfflinePlayer("SC_Staat"), 100 * UtilitiesConfig.getMultiplier());
		}

	}

	public static boolean isInList(de.secretcraft.voteStreaks.commands.List<Integer> l1, int Streak) {
		while (l1.hasAccess()) {
			if (l1.getObject() == Streak) {
				return true;
			}
			l1.next();
		}
		return false;
	}

}
