package de.secretcraft.voteStreaks.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import net.milkbowl.vault.economy.Economy;

public class WerbungCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix = UtilitiesConfig.getPrefix();
		MessengerCommand m2 = new MessengerCommand();
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		Economy eco = rsp.getProvider();
		Player p = (Player) arg0;
		if (arg3.length > 0) {
			if (p.hasPermission("scu.werbung")) {
				switch (arg3[0]) {

				case "kaufen":
					if (m2.getMessage(p.getName()) == null) {
						if (arg3.length > 1) {
							String text = "";
							for (int i = 1; i < arg3.length; i++) {
								text = text + arg3[i] + " ";
							}
							if (text.length() <= 46) {
								try {
									m2.addMessage(text, p);
									p.sendMessage(prefix + " §3Werbung erfolgreich gekauft!");
									eco.withdrawPlayer(p, 20000);
									m2.start();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								p.sendMessage(prefix + " §cDeine Werbung darf maximal 46 Zeichen beinhalten!");
							}
						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /werbung kaufen <Werbetext>");
						}
					} else {
						p.sendMessage(prefix
								+ " §cDu verfügst bereits über eine Werbeanzeige! Editiere sie mit /werbung edit <neuertext>");
					}
					break;
				case "edit":
					if (m2.getMessage(p.getName()) != null) {
						if (arg3.length > 1) {
							String text = "";
							for (int i = 1; i < arg3.length; i++) {
								text = text + arg3[i] + " ";
							}
							if (text.length() <= 46) {
								try {
									m2.addMessage(text, p);
									p.sendMessage(prefix + " §3Werbung erfolgreich editiert!");
									eco.withdrawPlayer(p, 1000);
									m2.start();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								p.sendMessage(prefix + " §cDeine Werbung darf maximal 46 Zeichen beinhalten!");
							}
						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /werbung edit <Werbetext>");
						}
					} else {
						p.sendMessage(prefix + " §cDu verfügst über keine Werbung zum editieren.");
					}
					break;
				default:
					p.sendMessage(prefix + " §cKorrekte Nutzung: /werbung kaufen/edit");
					break;
				}
			} else {
				Output.Err(p, "perm");
			}
		} else {
			p.sendMessage(prefix + "§3Werbung kaufst du mit /werbung kaufen <Dein Werbetext>");
			p.sendMessage(prefix + "§3Deine Werbung kannst du mit /werbung edit <Neuer Werbetext> editieren");
			p.sendMessage(prefix + "§3Eine Werbeanzeige kostet 2500 SD und wird nach etwa 7 Tagen gelöscht.");
			p.sendMessage(prefix + "§3Das editieren einer Werbeanzeige kostet 1000 SD.");

		}
		return false;
	}

}
