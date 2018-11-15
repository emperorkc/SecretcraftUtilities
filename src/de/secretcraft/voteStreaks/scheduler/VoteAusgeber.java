package de.secretcraft.voteStreaks.scheduler;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import de.secretcraft.main.Main;
import de.secretcraft.voteStreaks.commands.List;
import de.secretcraft.voteStreaks.commands.votedToday;

public class VoteAusgeber {
	private final JavaPlugin plugin;

	public VoteAusgeber(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void run() throws IOException {
		List<String> l1 = votedToday.getVoters();
		List<String> l2 = votedToday.getVoters();
		String voters = "";
		l1.toFirst();
		l2.toFirst();
		while (l1.hasAccess()) {
			Player p = Bukkit.getPlayer(UUID.fromString(l1.getObject()));
			OfflinePlayer p1 = Bukkit.getOfflinePlayer(UUID.fromString(l1.getObject()));
			l2.next();

			if (p == null) {
				if (l2.getObject() != null) {
					voters = voters + p1.getName() + ", ";
				} else {
					voters = voters + p1.getName() + "";
				}
			} else {
				if (l2.getObject() != null) {
					voters = voters + p.getName() + ", ";
				} else {
					voters = voters + p.getName() + "";
				}
			}
			l1.next();
			l2.next();
		}
		Bukkit.broadcastMessage("§6Die folgenden Spieler haben gevotet und tolle Belohnungen erhalten:§e " + voters
				+ "§6! Vote auch du mit /vote");
	}

	public BukkitTask runTaskTimer(Main main, int i, int j) {

		return null;
	}
}