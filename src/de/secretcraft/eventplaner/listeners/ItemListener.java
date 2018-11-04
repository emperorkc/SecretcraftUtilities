package de.secretcraft.eventplaner.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.token.config.PlayerTokenData;
import de.secretcraft.voteStreaks.commands.List;

public class ItemListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "SammelEvent");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent e) throws IOException {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = e.getPlayer();

		if(e.getClickedBlock()!=null) {
		if (e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
			if (e.getAction().equals((Action.LEFT_CLICK_BLOCK))) {
				Sign s = (Sign) e.getClickedBlock().getState();
				boolean found = false;
				int j = 0;
				if (s.getLine(1).equals("§e§l[Kürbisse]")) {
					int amount = 0;
					for (int i = 0; i < 36; i++) {
						if(p.getInventory().getItem(i)!=null) {
						if (p.getInventory().getItem(i).getType().equals(Material.CARVED_PUMPKIN)||p.getInventory().getItem(i).getType().equals(Material.PUMPKIN)) {
							Config.set("Spieler." + p.getName(),
									Config.getInt("Spieler." + p.getName()) + p.getInventory().getItem(i).getAmount());
							Config.set("Globalcount",
									Config.getInt("Globalcount") + p.getInventory().getItem(i).getAmount());
							amount += p.getInventory().getItem(i).getAmount();
							save();
							p.getInventory().setItem(i, null);
						}
					}}
					if (amount != 0) {
						p.sendMessage(prefix + " §2Du hast erfolgreich " + amount + " §6Kürbisse §2gespendet!");
					} else {
						p.sendMessage(prefix + " §cDu hast keine §6Kürbisse §cim Inventar!");
					}
					s.setLine(2, "§6§l" +Config.getInt("Globalcount")+"");
					s.update();
				}
			} else {

				if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					Sign s = (Sign) e.getClickedBlock().getState();
					if (s.getLine(1).equals("§e§l[Kürbisse]")) {
					List<String> l1 = getPlayers();
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
									if (getToken(u) > largest) {
										largest = getToken(u);
										largestP = l1.getObject();

									}
								} else {

									if (getToken(u1) > largest) {
										largest = getToken(u1);
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

					p.sendMessage("§3---§6 Die spendabelsten Spieler: §3---");
					l2.toFirst();
					for (int i = 1; i <= 20; i++) {
						if (!l2.isEmpty()) {
							Player u = Bukkit.getPlayer(l2.getObject());
							OfflinePlayer u1 = Bukkit.getOfflinePlayer(l2.getObject());
							l2.toFirst();

							if (u != null) {
								p.sendMessage(
										"         §6" + i + ". §3" + u.getName() + ": §6" + getToken(u) + "§6 Kürbisse");
							} else {
								if (u1.getName() != null) {
									p.sendMessage("         §6" + i + ". §3" + u1.getName() + ": §6" + getToken(u1)
											+ "§6 Kürbisse");
								}
							}
							l2.remove();
						}
					}}
				}
			}
		}	}
	}

	public int getToken(OfflinePlayer p) {
		return Config.getInt("Spieler." + p.getName());
	}

	public List<String> getPlayers() {

		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Spieler.");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}

		return l1;

	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = e.getPlayer();

		boolean found = false;
		int j = 0;
		if (e.getLine(1).toLowerCase().equals("[kürbisse]") ||e.getLine(1).equals("§e§l[Kürbisse]")) {
		if (p.hasPermission("scu.event")) {
			
				e.setLine(1, "§e§l[Kürbisse]");
			}else {
				Bukkit.getWorld(e.getBlock().getLocation().getWorld().getName()).getBlockAt(e.getBlock().getLocation())
				.setType(Material.AIR);
		p.sendMessage("§4Nein!");
	}
		} 
	}
}
