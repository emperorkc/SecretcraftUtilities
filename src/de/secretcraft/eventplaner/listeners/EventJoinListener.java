package de.secretcraft.eventplaner.listeners;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import de.secretcraft.eventplaner.Commands.JMCommand;
import de.secretcraft.main.Main;
import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.simpleCommands.commands.SpawnersCommand;
import de.secretcraft.voteStreaks.commands.List;

public class EventJoinListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "EventJoin");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	public static void setDate(String date, String name) throws IOException {
		Config.set("Events." + name + ".date", date);
		save();
	}

	public static void setHour(int hour, String name) throws IOException {
		Config.set("Events." + name + ".hour", hour);
		save();

	}

	public static void setMin(int min, String name) throws IOException {
		Config.set("Events." + name + ".min", min);
		save();

	}

	public static void delE(String name) throws IOException {
		Config.set("Events." + name, null);
		save();
	}

	public static void setText(String text, String name) throws IOException {
		Config.set("Events." + name + ".text", text);
		save();
	}

	public static String getDate(String name) {
		return Config.getString("Events." + name + ".date");
	}

	public static int getHour(String name) {
		return Config.getInt("Events." + name + ".hour");
	}

	public static int getMin(String name) {

		return Config.getInt("Events." + name + ".min");
	}

	public static String getText(String name) {
		return Config.getString("Events." + name + ".text");
	}

	public static List<String> getTexts() {
		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Events");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}

		return l1;
	}

	

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String prefix = UtilitiesConfig.getPrefix();
		List<String> l1 = getTexts();
		Player p = e.getPlayer();
		if (JMCommand.Config.getInt("Spawn." + p.getName()) == 0) {
			if (!l1.isEmpty()) {

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH");
				SimpleDateFormat sdf1 = new SimpleDateFormat("mm");
				String y = cal.getTime().getDate() + "." + (cal.getTime().getMonth() + 1) + "."
						+ (cal.getTime().getYear() + 1900);

				String f = sdf.format(cal.getTime());
				String g = sdf1.format(cal.getTime());
				int fs = Integer.parseInt(f);
				int gs = Integer.parseInt(g);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					@Override
					public void run() {

						p.sendMessage("");

						p.sendMessage(prefix + " §4Die folgenden Events stehen an:");
						p.sendMessage("");
						l1.toFirst();
						while (l1.hasAccess()) {

							if (getDate(l1.getObject()).equals(y)) {

								if (fs >= getHour(l1.getObject())) {

									if (gs >= getMin(l1.getObject())) {
										try {
											delE(l1.getObject());
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
									if (fs > getHour(l1.getObject())) {
										try {
											delE(l1.getObject());
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}
							String b = "";
							if (getMin(l1.getObject()) < 10) {
								b = "0" + getMin(l1.getObject());
							} else {
								b = Integer.toString(getMin(l1.getObject()));
							}
							if (getDate(l1.getObject()) != null) {
								p.sendMessage(prefix + " §b" + l1.getObject() + " §2am §3" + getDate(l1.getObject())
										+ " §2um §3" + getHour(l1.getObject()) + ":" + b + " §2Uhr:");
								p.sendMessage(prefix + "§9 " + getText(l1.getObject()));
								p.sendMessage("");
							}
							l1.next();
						}

					}
				}, 20);

			}
		}
	}

	@EventHandler
	public void specialChestOpen(PlayerInteractEvent e) throws IOException {
		String prefix = UtilitiesConfig.getPrefix();

		Player p = e.getPlayer();

		if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getBlockData().getMaterial().equals(Material.CHEST)
					|| e.getClickedBlock().getBlockData().getMaterial().equals(Material.TRAPPED_CHEST)) {

				Chest c = (Chest) e.getClickedBlock().getState();

				Location l = e.getClickedBlock().getLocation();

				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				String coords = "§4§l X:" + x + " Y:" + y + " Z:" + z + "";
				for (int i = 0; i < 54; i++) {
					if (c.getInventory().getItem(i) != null) {
						if (c.getInventory().getItem(i).getType().equals(Material.SPAWNER)) {
							Collection<? extends Player> p1 = Bukkit.getOnlinePlayers();
							Iterator ip = p1.iterator();
							while (ip.hasNext()) {
								Player ps = (Player) ip.next();
								if (ps.hasPermission("scu.enteignen")) {
									if (SpawnersCommand.Config.getInt("Spawn." + p.getName()) == 0) {
										ps.sendMessage(
												prefix + " §4§lSpawner bei " + p.getName() + " gefunden! Koordinaten: "
														+ coords + " Menge:" + c.getInventory().getItem(i).getAmount());

									}
								}
							}
						}
					}
				}
			}
		}
	}
}
