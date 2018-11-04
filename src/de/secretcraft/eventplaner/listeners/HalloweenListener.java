package de.secretcraft.eventplaner.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditListener;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.commands.List;

public class HalloweenListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities/Events", "Chests.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config.save(ConfigFile);
	}

	@EventHandler
	public void specialChestOpen(PlayerInteractEvent e) throws IOException {
		String prefix = UtilitiesConfig.getPrefix();

		Player p = e.getPlayer();

		if (e.getClickedBlock() != null) {
			if (e.getClickedBlock().getBlockData().getMaterial().equals(Material.CHEST)) {

				Chest c = (Chest) e.getClickedBlock().getState();

				Location l = e.getClickedBlock().getLocation();

				int x = l.getBlockX();
				int y = l.getBlockY();
				int z = l.getBlockZ();
				if (isReg(l)) {

					if (Config.getInt(x + "." + y + "." + z + "." + p.getName()) == 1) {
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
						p.playEffect(l, Effect.SMOKE, 0);
						e.setCancelled(true);
						p.sendMessage(prefix + " Du hast diese Kiste bereits geöffnet!");

					} else {
						if (!p.hasPermission("scu.event")) {
							addC(p, x, y, z);
							String cs = getChest(l);
							MenuListenerCommand m = new MenuListenerCommand("§6§lEventkiste", 3);
							p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
							p.playEffect(l, Effect.POTION_BREAK, 0);
							e.setCancelled(true);
							int gg = 0;
							for (int i = 0; i < 3; i++) {

								for (int j = 0; j < 9; j++) {
									if (i == 0) {
										gg = j;
									}
									if (i == 1) {
										gg = j + 9;
									}

									if (i == 2) {
										gg = j + 18;
									}

									if (c.getInventory().getItem(gg) != null) {
										m.addButton(m.getRow(i), j, new ItemStack(c.getInventory().getItem(gg)));
									}
								}

							}
							m.open(p);
						}

					}
				} else {

					if (Config.getInt("adding") == 1) {

						e.setCancelled(true);
						if (Config.get("Chests." + Config.getString("Name")) == null) {
							addChest(p, l, Config.getString("Name"));
							p.sendMessage(prefix + " §6Kiste erfolgreich hinzugefügt!");
						} else {
							p.sendMessage(prefix + " §cKiste schon vorhanden!");
						}
						Config.set("adding", 0);
						save();
					}

				}

			}
		}
	}

	public static void add(String name) throws IOException {
		Config.set("adding", 1);
		Config.set("Name", name);
		save();
	}

	public static void addChest(Player p, Location l, String name) throws IOException {
		String prefix = UtilitiesConfig.getPrefix();
		if (Config.get("Chests." + name) == null) {
			Config.set("Chests." + name + ".x", l.getBlockX());
			Config.set("Chests." + name + ".y", l.getBlockY());
			Config.set("Chests." + name + ".z", l.getBlockZ());
		} else {
			p.sendMessage(prefix + " §6Kiste existiert bereits!");
		}
		save();
	}

	public static void delChest(String name, Player p) throws IOException {

		String prefix = UtilitiesConfig.getPrefix();
		if (Config.get("Chests." + name) != null) {
			Config.set("Chests." + name, null);
			save();
			p.sendMessage(prefix + " §6Kiste erfolgreich gelöscht!");
		} else {
			p.sendMessage(prefix + " §6Kiste war nicht gespeichert!");
		}

	}

	public static int getX(int i) {
		return (Config.getInt("Chests." + i + ".x"));
	}

	public static int getY(int i) {
		return (Config.getInt("Chests." + i + ".y"));
	}

	public static int getZ(int i) {
		return (Config.getInt("Chests." + i + ".z"));
	}

	public static boolean isTrue(Player p, String i) {
		if (Config.getInt(Config.getInt("Chests." + i + ".x") + "." + Config.getInt("Chests." + i + ".y") + "."
				+ Config.getInt("Chests." + i + ".z") + "." + p.getName()) != 1) {
			return true;
		}
		return false;
	}

	public static boolean isTrue1(String i) {
		if (Config.get("Chests." + i) != null) {
			return true;
		}
		return false;
	}

	public static boolean isReg(Location l) {

		List<String> l1 = getChests();
		l1.toFirst();
		if (!l1.isEmpty()) {

			while (l1.hasAccess()) {
				String name = l1.getObject();

				if (Config.getInt("Chests." + name + ".x") == l.getBlockX()) {
					if (Config.getInt("Chests." + name + ".y") == l.getBlockY()) {
						if (Config.getInt("Chests." + name + ".z") == l.getBlockZ()) {
							return true;
						}
					}
				}
				l1.next();
			}
		}
		return false;
	}

	public void addC(Player p, int x, int y, int z) throws IOException {

		Config.set(x + "." + y + "." + z + "." + p.getName(), 1);
		save();
	}

	public static List<String> getChests() {
		List<String> l1 = new List<>();
		if (Config.getString("Chests") != null) {
			ConfigurationSection s = Config.getConfigurationSection("Chests");

			for (String k : s.getKeys(false)) {

				l1.append(k);
			}
		}

		return l1;
	}

	public static String getChest(Location l) {
		List<String> l1 = getChests();
		l1.toFirst();
		if (!l1.isEmpty()) {
			String name = l1.getObject();
			while (l1.hasAccess()) {
				if (Config.getInt("Chests." + name + ".x") == l.getBlockX()) {
					if (Config.getInt("Chests." + name + ".y") == l.getBlockY()) {
						if (Config.getInt("Chests." + name + ".z") == l.getBlockZ()) {
							return name;
						}
					}
				}
				l1.next();
			}
		}
		return null;
	}
	/*@EventHandler
	public void onSelect(PlayerInteractEvent e) throws IncompleteRegionException {
		Player p = e.getPlayer();
		com.sk89q.worldedit.world.World world1 = BukkitAdapter.adapt(p.getWorld());
		BukkitPlayer k = BukkitAdapter.adapt(p);
		if(p.getItemInHand().getType().equals(Material.WOODEN_AXE)) {
			if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) ||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			LocalSession sel = WorldEdit.getInstance().getSessionManager().get(k);
			if(sel.getSelection(world1)!=null) {
				for(int i=sel.getSelection(world1).getMinimumPoint().getBlockX(); i<=sel.getSelection(world1).getMaximumPoint().getBlockX(); i++) {
					Bukkit.getWorld(p.getWorld().getName()).playEffect(new Location(p.getWorld(),sel.getSelection(world1).getMinimumPoint().getX()+i,sel.getSelection(world1).getMinimumPoint().getY(),sel.getSelection(world1).getMinimumPoint().getZ()), Effect.CHORUS_FLOWER_DEATH, 100);
				}
			}
		}}
	}*/
}
