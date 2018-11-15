package de.secretcraft.simpleCommands.listener;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.listeners.List;

public class LiftSignListener implements Listener {
	/*@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
	
		if (e.getClickedBlock().getType().equals(Material.SIGN)
				|| e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Sign s = (Sign) e.getClickedBlock().getState();
				boolean found = false;
				int j = 0;
				if (s.getLine(1).equals("§2§l[Lift Up]")) {

					for (int i = e.getClickedBlock().getY(); i < 256; i++) {
						if ((p.getWorld().getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ())
								.getType().equals(Material.WALL_SIGN)
								|| p.getWorld().getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ())
										.getType().equals(Material.SIGN))) {
							Sign s2 = (Sign) p.getWorld()
									.getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ()).getState();
							int yc=s2.getY();
							if (s2.getLine(1).equals("§2§l[Lift Down]")) {
								found = true;
								found = true;
								if (p.getWorld().getBlockAt(p.getLocation().add(0, j + 1, 0)).getType()
										.equals(Material.AIR)
										|| p.getWorld().getBlockAt(p.getLocation().add(0, j + 1, 0)).getType()
												.equals(Material.WALL_SIGN)
										|| p.getWorld().getBlockAt(p.getLocation().add(0, j + 1, 0)).getType()
												.equals(Material.SIGN) &&!p.getWorld().getBlockAt(p.getLocation().add(0, j-1 , 0)).getType()
												.equals(Material.AIR) ) {
							
									p.teleport(p.getLocation().add(0, j, 0));
								} else {
									p.sendMessage(prefix + " §cKeine sichere Position gefunden!");
								}
								return;
							}
						}
						j++;
					}
					if (found != true) {
						p.sendMessage(prefix + " §cKein zugehöriges Lift-Schild gefunden!");
					}
				} else if (s.getLine(1).equals("§2§l[Lift Down]")) {
					for (int i = e.getClickedBlock().getY(); i > 0; i--) {
						if ((p.getWorld().getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ())
								.getType().equals(Material.WALL_SIGN)
								|| p.getWorld().getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ())
										.getType().equals(Material.SIGN))) {
							Sign s2 = (Sign) p.getWorld()
									.getBlockAt(e.getClickedBlock().getX(), i, e.getClickedBlock().getZ()).getState();
							if (s2.getLine(1).equals("§2§l[Lift Up]")) {
								found = true;
								if (p.getWorld().getBlockAt(p.getLocation().subtract(0, j, 0)).getType()
										.equals(Material.AIR) && !p.getWorld().getBlockAt(p.getLocation().subtract(0, j+1, 0)).getType()
										.equals(Material.AIR) ) {
									p.teleport(p.getLocation().subtract(0, j, 0));
								} else {
									p.sendMessage(prefix + " §cKeine sichere Position gefunden!");
								}
								return;
							}
						}
						j++;
					}
					if (found != true) {
						p.sendMessage(prefix + " §cKein zugehöriges Lift-Schild gefunden!");
					}
				}
				if (s.getLine(1).equals("[Lift Up]")) {
					s.setLine(1, "§2§l[Lift Up]");
				}
				if (s.getLine(1).equals("[Lift Down]")) {
					s.setLine(1, "§2§l[Lift Down]");
				}
			}
		}
	}

	@EventHandler
	public void onSignPlace(SignChangeEvent e) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = e.getPlayer();
			
			
			boolean found = false;
			int j = 0;
			if (e.getLine(1).toLowerCase().equals("[lift up]")) {
				e.setLine(1, "§2§l[Lift Up]");
				for (int i = e.getBlock().getY(); i < 256; i++) {
					if ((p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ()).getType()
							.equals(Material.WALL_SIGN)
							|| p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ()).getType()
									.equals(Material.SIGN))) {
						Sign s2 = (Sign) p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ())
								.getState();
						if (s2.getLine(1).equals("§2§l[Lift Down]")) {
							found = true;
							p.sendMessage(prefix + " §2§lAufzug erstellt!");
						}
					}
					j++;
				}
				if (found != true) {
					p.sendMessage(prefix + " §cKein zugehöriges Lift-Schild gefunden!");
				}
			}
			if (e.getLine(1).toLowerCase().equals("[lift down]")) {
				e.setLine(1, "§2§l[Lift Down]");

				for (int i = e.getBlock().getY(); i > 0; i--) {
					if ((p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ()).getType()
							.equals(Material.WALL_SIGN)
							|| p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ()).getType()
									.equals(Material.SIGN))) {
						Sign s2 = (Sign) p.getWorld().getBlockAt(e.getBlock().getX(), i, e.getBlock().getZ())
								.getState();
						if (s2.getLine(1).equals("§2§l[Lift Up]")) {
							found = true;
							p.sendMessage(prefix + " §2Aufzug erstellt!");
						}
					}
					j++;
				}
				if (found != true) {
					p.sendMessage(prefix + " §cKein zugehöriges Lift-Schild gefunden!");
				}
			
		}
	}*/
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Kopfsammlung");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	public static List<String> getPlayers() {
		List<String> l1 = new List<>();
		ConfigurationSection s = Config.getConfigurationSection("Spieler.");
		for (String k : s.getKeys(false)) {
			l1.append(k);
		}

		return l1;
	}
	public static int getToken(OfflinePlayer p) {
		return Config.getInt("Spieler." + p.getName());
	}
	@EventHandler
	public void onHead(PlayerInteractEvent e) throws IOException {
		String prefix=UtilitiesConfig.getPrefix();
		if(e.getClickedBlock()!=null) {
		if(e.getClickedBlock().getType().equals(Material.PLAYER_HEAD)) {
		ItemStack a=e.getPlayer().getItemInHand();
		
			Location l = e.getClickedBlock().getLocation();
			if(Bukkit.getWorld(l.getWorld().getName()).getBlockAt(l.subtract(0, 1, 0)).getType().equals(Material.BARRIER)) {
				
				if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					Config.set("Gefunden", Config.getInt("Gefunden")+1);
					Config.set("Spieler."+e.getPlayer().getName(),Config.getInt("Spieler."+e.getPlayer().getName())+1 );
					e.getPlayer().sendMessage(prefix+ " §6Kopf gefunden. Bereits von dir gefundene Köpfe: "+Config.getInt("Spieler."+e.getPlayer().getName()));
					save();
					Bukkit.getWorld(l.getWorld().getName()).getBlockAt(l.add(0, 1, 0)).setType(Material.AIR);
				}
			}}
		} 
	}
	
}
