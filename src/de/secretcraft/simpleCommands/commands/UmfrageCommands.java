package de.secretcraft.simpleCommands.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.secretcraft.main.Main;
import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.extern.mkremins.fanciful.FancyMessage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class UmfrageCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Plugin plugin = Main.getPlugin();

		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		switch (arg1.getName()) {
		case "umfrage":

			if (arg3.length > 1 && Integer.parseInt(arg3[0]) != 0) {
				if (p.hasPermission("scu.umfrage")) {
					int dauer = Integer.parseInt(arg3[0]);
					if (dauer >= 30) {
						String text = "";
						for (int i = 1; i < arg3.length; i++) {
							text = text + " " + arg3[i];
						}
						if (Config.getInt("Umfrage") != 1) {
							Config.set("Ja", null);
							Config.set("Nein", null);
							Config.set("Spieler", null);
							Config.set("Umfrage", 1);
							try {
								save();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						
				                
							 
							Collection<? extends Player> s= Bukkit.getOnlinePlayers();
							


							
													
							Bukkit.broadcastMessage(
									"§8» §8§m-------------§r §7— §6§lUMFRAGE §7— §8§m-------------§r §8«");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage(
									"§8x §7Eine §6UMFRAGE §7wurde von §6" + p.getName() + "§8 gestartet!");
							new FancyMessage("§8x §7Dafür: §a[/ja]").tooltip("§7Klick um mit §aja §7abzustimmen!")
				               
				               .command("/ja").send(s);;
						new FancyMessage("§8x §7Dagegen: §4[/nein]").tooltip("§7Klick um mit §4"
								+ ""
								+ "nein §7abzustimmen!")
					               
					               .command("/nein").send(s);	
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§8x §7Umfrage§8: §e" + text + "§7");
							Bukkit.broadcastMessage("§8x §7Ihr habt §6" + dauer + " §7Sekunden Zeit um abzustimmen.");
							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage(
									"§8» §8§m-------------§r §7— §6§lUMFRAGE §7— §8§m-------------§r §8«");

							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								public void run() {

									p.sendMessage(prefix + " §7In §620 Sekunden §7wird das Ergebnis bekannt!");

								}
							}, (dauer - 20) * 20);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								public void run() {

									p.sendMessage(prefix + " §7In §610 Sekunden §7wird das Ergebnis bekannt!");

								}
							}, (dauer - 10) * 20);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

								public void run() {

									p.sendMessage(prefix + " §7In §65 Sekunden §7wird das Ergebnis bekannt!");

								}
							}, (dauer - 5) * 20);
							Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {

									Bukkit.broadcastMessage(
											"§8» §8§m-------------§r §7— §6§lUMFRAGE §7— §8§m-------------§r §8«");
									Bukkit.broadcastMessage("");
									Bukkit.broadcastMessage("§8x §7Die §6UMFRAGE §7wurde beendet!");
									Bukkit.broadcastMessage("§8x §7Ergebnis§8:");
									Bukkit.broadcastMessage("");
									Bukkit.broadcastMessage("§8x §a§lJa §8- §a§l" + Config.getInt("Ja") + " Stimmen");
									Bukkit.broadcastMessage(
											"§8x §4§lNein §8- §4§l" + Config.getInt("Nein") + " Stimmen");
									Bukkit.broadcastMessage("");
									Bukkit.broadcastMessage(
											"§8» §8§m-------------§r §7— §6§lUMFRAGE §7— §8§m-------------§r §8«");

									Config.set("Umfrage", 0);
									try {
										save();
									} catch (IOException e) {

										e.printStackTrace();
									}

								}
							}, (dauer) * 20);
						} else {
							p.sendMessage(prefix + "§cEs läuft bereits eine Umfrage!");
						}
					} else {
						p.sendMessage(prefix + " §cDie Umfrage muss mindestens 30 Sekunden dauern.");
					}
				} else {
					Output.Err(p, "perm");
				}
			} else {
				p.sendMessage(prefix + "§cKorrekte Nutzung: /umfrage <dauer> <text>");
			}
			break;
		case "ja":
			if (Config.getInt("Umfrage") == 1) {
				if (Config.getString("Spieler." + p.getName()) == null) {
					Config.set("Ja", Config.getInt("Ja") + 1);
					Config.set("Spieler." + p.getName(), "ja");
					p.sendMessage(prefix + "§7Du hast für §aJa §7abgestimmt!");
					try {
						save();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					p.sendMessage(prefix + " §cDu hast bereits abgestimmt!");
				}
			} else {
				p.sendMessage(prefix + " §cDerzeit läuft keine Umfrage!");
			}
			break;
		case "nein":
			if (Config.getInt("Umfrage") == 1) {
				if (Config.getString("Spieler." + p.getName()) == null) {
					Config.set("Nein", Config.getInt("Nein") + 1);
					Config.set("Spieler." + p.getName(), "nein");
					p.sendMessage(prefix + "§7Du hast für §4Nein §7abgestimmt!");
					try {
						save();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					p.sendMessage(prefix + " §cDu hast bereits abgestimmt!");
				}
			} else {
				p.sendMessage(prefix + " §cDerzeit läuft keine Umfrage!");
			}
			break;

		}
		return false;
	}

	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Umfrage.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

}
