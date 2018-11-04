package de.secretcraft.simpleCommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.simpleCommands.listener.LiftSignListener;
import de.secretcraft.voteStreaks.commands.List;

public class HTopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		if(p.hasPermission("scu.basics")) {
			de.secretcraft.voteStreaks.listeners.List<String> l1 = LiftSignListener.getPlayers();
			l1.toFirst();

			List<String> l2 = new List<>();

			for (int i = 1; i <= 10; i++) {
				if (!l1.isEmpty()) {
					double largest = 0;
					String largestP = null;

					l1.toFirst();
					while (l1.hasAccess()) {
						Player u = Bukkit.getPlayer(l1.getObject());
						OfflinePlayer u1 = Bukkit.getOfflinePlayer(l1.getObject());
						if (u != null) {
							if (LiftSignListener.getToken(u) > largest) {
								largest = LiftSignListener.getToken(u);
								largestP = l1.getObject();

							}
						} else {

							if (LiftSignListener.getToken(u1) > largest) {
								largest = LiftSignListener.getToken(u1);
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

			p.sendMessage("§3---§6 Die meisten Eventköpfe: §3---");
			l2.toFirst();
			for (int i = 1; i <= 20; i++) {
				if (!l2.isEmpty()) {
					Player u = Bukkit.getPlayer(l2.getObject());
					OfflinePlayer u1 = Bukkit.getOfflinePlayer(l2.getObject());
					l2.toFirst();

					if (u != null) {
						p.sendMessage(
								"         §6" + i + ". §3" + u.getName() + ": §6" + LiftSignListener.getToken(u) + "§6 Köpfe");
					} else {
						if (u1.getName() != null) {
							p.sendMessage("         §6" + i + ". §3" + u1.getName() + ": §6" + LiftSignListener.getToken(u1)
									+ "§6 Köpfe");
						}
					}
					l2.remove();
				}
			}
		
		}
		return false;
	}
	
}
