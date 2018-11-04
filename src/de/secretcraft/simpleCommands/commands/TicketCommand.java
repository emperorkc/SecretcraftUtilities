package de.secretcraft.simpleCommands.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.secretcraft.main.UtilitiesConfig;

public class TicketCommand implements CommandExecutor {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Tickets");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if(p.hasPermission("scu.*")) {
		if (arg3.length > 0) {
			switch (arg3[0]) {
			case "erstellen":
				if (p.hasPermission("scu.basics")) {
					if (arg3.length > 1) {
						String text = "";
						for (int i = 1; i < arg3.length; i++) {
							text = text + arg3[i]+" ";
						}
						Location l = p.getLocation();
						int i = 1;
						while (Config.get("Ticket." + i) != null) {
							i++;
						}
						Config.set("Ticket." + i + ".name", p.getName());
						Config.set("Ticket." + i + ".text", text);
						Config.set("Ticket." + i + ".x", l.getBlockX());
						Config.set("Ticket." + i + ".y", l.getBlockY());
						Config.set("Ticket." + i + ".z", l.getBlockZ());
						Config.set("Ticket." + i + ".w", l.getWorld().getName());
						try {
							save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						p.sendMessage(prefix + " §aTicket erfolgreich erstellt. §3Text: " + text);
						Collection<? extends Player> m = Bukkit.getOnlinePlayers();
						Iterator<? extends Player> iterator = m.iterator();
						while(iterator.hasNext()) {
							if(iterator.next().hasPermission("scu.ticket")) {
								iterator.next().sendMessage(prefix+" §2Ein neues Ticket wurde verfasst. Siehe aktive Tickets mit §3/ticket liste");
							
							}
						}
					} else {
						p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket erstellen <text>");
					}
				} else {
					p.sendMessage(prefix + " §cDu hast hierzu keine Rechte");
				}
				break;
			case "abrufen":
				if (p.hasPermission("scu.ticket")) {
					if (arg3.length > 1) {
						int zahl = Integer.parseInt(arg3[1]);
						String text = Config.getString("Ticket." + zahl + ".text");
						p.sendMessage("§aTicket Nr.§2 " + zahl + " §avon §c"
								+ Config.getString("Ticket." + zahl + ".name") + "§e:");
						p.sendMessage("");
						p.sendMessage( "§5" + text);
						p.sendMessage("");
						p.sendMessage("§aZum Ticket-Erstellpunkt teleportieren? §3/ticket tp <nummer>");
						p.sendMessage("§aUm das Ticket als abgearbeitet zu markieren: §3/ticket gelesen <nummer>");
					} else {
						p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket abrufen <nummer>");
					}
				} else {
					if (arg3.length > 1) {
						int zahl = Integer.parseInt(arg3[1]);
						String text = Config.getString("Ticket." + zahl + ".antwort");
						if(Config.getString("Ticket."+zahl+".name")==p.getName()) {
						p.sendMessage("§aTicket Nr.§2 " + zahl + " §avon §c"
								+ Config.getString("Ticket." + zahl + ".gelesen") + " §2gelesen.§e Antwort:");
						p.sendMessage("");
						p.sendMessage(text);
						} else {
							p.sendMessage(prefix+" §3Diese Ticketnummer gehört zu keinem deiner Tickets.");
						}
					}
					
				}
				break;
			case "antworten":
				if (p.hasPermission("scu.ticket")) {
					if (arg3.length > 2) {
						int zahl = Integer.parseInt(arg3[1]);
						String text = "";
						for (int i = 2; i < arg3.length; i++) {
							text = text + arg3[i]+" ";
						}
						Config.set("Ticket." + zahl + ".gelesen", p.getName());
						Config.set("Ticket."+zahl+".antwort", text);
						try {
							save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						p.sendMessage(prefix + " §aTicket erfolgreich beantwortet.");
						Bukkit.getPlayer(Config.getString("Ticket." + zahl + ".name")).sendMessage(prefix+ " §3Dein Ticket Nr. §2"+zahl);;
					} else {
						p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket gelesen <nummer> <antwort>");
					}
				} else {
					p.sendMessage(prefix + " §cDu hast hierzu keine Rechte");
				}
				break;
			case "tp":
				if (arg3.length > 1) {
					if (p.hasPermission("scu.ticket")) {
						int zahl = Integer.parseInt(arg3[1]);
						Location l = new Location(Bukkit.getWorld(Config.getString("Ticket." + zahl + ".w")),
								Config.getInt("Ticket." + zahl + ".x"), Config.getInt("Ticket." + zahl + ".y"),
								Config.getInt("Ticket." + zahl + ".z"));
						p.teleport(l.add(0, 1, 0));
						p.sendMessage(prefix + " §aErfolgreich zur Ticketposition von §c"
								+ Config.getString("Ticket." + zahl + ".name") + " §ateleportiert!");
					} else {
						p.sendMessage(prefix + " §cDu hast hierzu keine Rechte");
					}
				} else {
					p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket tp <nummer>");
				}
				break;
			case "liste":
				if(p.hasPermission("scu.ticket")) {
					int i = 1;
					while (Config.get("Ticket." + i) != null) {
						if(Config.get("Ticket."+i+".gelesen")==null) {
						p.sendMessage(prefix+" §aTicket Nr.§2 "+i+" §avon §c"+Config.getString("Ticket."+i+".name")+"§a§a. §a/ticket abrufen "+i);
						
						}
						i++;
					}
				} else {
					int i = 1;
					p.sendMessage(prefix+" §3Deine Tickets:");
					boolean b=true;
					while (Config.get("Ticket." + i) != null) {
						if(Config.getString("Ticket."+i+".name")==p.getName()) {
						p.sendMessage(prefix+" §aTicket Nr.§2 "+i+" §avon §c"+Config.getString("Ticket."+i+".gelesen")+" §abearbeitet§a. §a/ticket abrufen "+i);
						b=false;
						
						i++;
					}}
					if(b==true)  {
						p.sendMessage(prefix+" §cKeine Tickets vorhanden / beantwortet!");
					}
				}
				break;
			}
		} else {
			if (p.hasPermission("scu.ticket")) {
				p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket erstellen/abrufen/tp/liste/antworten"
						+ "");
			} else {
				p.sendMessage(prefix + " §cKorrekte Nutzung: /ticket erstellen/abrufen/liste"
						+ "");
			}
		}}
		return false;
	}

}
