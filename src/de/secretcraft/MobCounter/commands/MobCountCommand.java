package de.secretcraft.MobCounter.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class MobCountCommand implements CommandExecutor {
	static String prefix = UtilitiesConfig.getPrefix();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		WorldGuard wg = WorldGuard.getInstance();
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1 = BukkitAdapter.adapt(world);

		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);

		if (p.hasPermission("scu.tiere")) {
			if (args.length > 0) {

				switch (args[0]) {
				case "radius":
					if (args.length > 1) {
						if (Integer.parseInt(args[1]) <= 200) {
							ArrayList<Entity> eList = getNearbyEntities(p.getLocation(), Integer.parseInt(args[1]));
							List<Entity> l1 = new List<>();
							for (int i = 0; i < eList.size(); i++) {
								l1.append(eList.get(i));
							}
							l1.toFirst();
							int nChicken = 0;
							int nCow = 0;
							int nMoosh = 0;
							int nPig = 0;
							int nSheep = 0;
							int nHorse = 0;
							int nTurtle = 0;
							int nFish = 0;
							int numberofAnimals = 0;

							while (l1.hasAccess()) {
								if (l1.getObject().getType().name().equals("CHICKEN")) {
									nChicken++;
									numberofAnimals++;
								}

								if (l1.getObject().getType().name().equals("COW")) {
									nCow++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("MUSHROOM_COW")) {
									nMoosh++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("PIG")) {
									nPig++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("SHEEP")) {
									nSheep++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("HORSE")) {
									nHorse++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("TURTLE")) {
									nTurtle++;
									numberofAnimals++;
								}
								if (l1.getObject().getType().name().equals("COD_MOB")
										|| l1.getObject().getType().name().equals("SALMON_MOB")
										|| l1.getObject().getType().name().equals("PUFFERFISH")
										|| l1.getObject().getType().name().equals("TROPICAL_FISH")) {
									nFish++;
									numberofAnimals++;
								}
								l1.next();
							}
							if (numberofAnimals == 0) {
								p.sendMessage(prefix + " §2Im Radius von §3§l" + Integer.parseInt(args[1])
										+ " §2Blöcken wurden §3§lkeine §2Tiere gefunden:");
							} else {
								p.sendMessage(prefix + " §2Im Radius von §3§l" + Integer.parseInt(args[1])
										+ " §2Blöcken wurden §3§l" + numberofAnimals + " §2Tiere gefunden:");
								if (nChicken > 0) {
									p.sendMessage("§7[§e§lHühner§7]: §2" + nChicken);
								}
								if (nCow > 0) {
									p.sendMessage("§7[§f§lKühe§7]: §2" + nCow);
								}
								if (nMoosh > 0) {
									p.sendMessage("§7[§4§lPilzkühe§7]: §2" + nMoosh);
								}
								if (nPig > 0) {
									p.sendMessage("§7[§c§lSchweine§7]: §2" + nPig);
								}
								if (nSheep > 0) {
									p.sendMessage("§7[§f§lSchafe§7]: §2" + nSheep);
								}
								if (nHorse > 0) {
									p.sendMessage("§7[§6§lPferde§7]: §2" + nHorse);
								}
								if (nTurtle > 0) {
									p.sendMessage("§7[§2§lSchildkröten§7]: §2" + nTurtle);
								}
								if (nFish > 0) {
									p.sendMessage("§7[§b§lFische§7]: §b" + nFish);
								}
							}

						} else {
							Output.Err(p, "cUse17");
						}
					} else {
						Output.Err(p, "cUse16");
					}
					break;
				default:
					String regionname = args[0];
					ProtectedRegion rg = regions.getRegion(regionname);
					if (rg != null) {

						int r = 0;
						int y = 0;
						if (rg.getMaximumPoint().getBlockX() > rg.getMinimumPoint().getBlockX()) {
							r = rg.getMaximumPoint().getBlockX() - rg.getMinimumPoint().getBlockX();
						} else {
							r = rg.getMinimumPoint().getBlockX() - rg.getMaximumPoint().getBlockX();
						}
						if (rg.getMaximumPoint().getBlockZ() > rg.getMinimumPoint().getBlockZ()) {
							y = rg.getMaximumPoint().getBlockZ() - rg.getMinimumPoint().getBlockZ();
						} else {
							y = rg.getMinimumPoint().getBlockZ() - rg.getMaximumPoint().getBlockZ();
						}

						Location loc = new Location(p.getWorld(), rg.getMinimumPoint().getBlockX(),
								rg.getMinimumPoint().getBlockY(), rg.getMinimumPoint().getBlockZ());
						ArrayList<Entity> eList = getNearbyEntitiesRegion(loc, r, y);
						List<Entity> l1 = new List<>();
						for (int i = 0; i < eList.size(); i++) {
							l1.append(eList.get(i));
						}
						l1.toFirst();
						int nChicken = 0;
						int nCow = 0;
						int nMoosh = 0;
						int nPig = 0;
						int nSheep = 0;
						int nHorse = 0;
						int nTurtle = 0;
						int nFish = 0;
						int numberofAnimals = 0;

						while (l1.hasAccess()) {
							if (l1.getObject().getType().name().equals("CHICKEN")) {
								nChicken++;
								numberofAnimals++;
							}

							if (l1.getObject().getType().name().equals("COW")) {
								nCow++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("MUSHROOM_COW")) {
								nMoosh++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("PIG")) {
								nPig++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("SHEEP")) {
								nSheep++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("HORSE")) {
								nHorse++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("TURTLE")) {
								nTurtle++;
								numberofAnimals++;
							}
							if (l1.getObject().getType().name().equals("COD_MOB")
									|| l1.getObject().getType().name().equals("SALMON_MOB")
									|| l1.getObject().getType().name().equals("PUFFERFISH")
									|| l1.getObject().getType().name().equals("TROPICAL_FISH")) {
								nFish++;
								numberofAnimals++;
							}
							l1.next();
						}
						if (numberofAnimals == 0) {
							p.sendMessage(
									prefix + " §2In der Region §b§l" + regionname + "§2 wurden keine Tiere gefunden");
						} else {
							p.sendMessage(prefix + " §2In der Region §b§l" + regionname + " §2wurden §3§l"
									+ numberofAnimals + " §2Tiere gefunden:");

							if (nChicken > 0) {
								p.sendMessage("§7[§e§lHühner§7]: §2" + nChicken);
							}
							if (nCow > 0) {
								p.sendMessage("§7[§f§lKühe§7]: §2" + nCow);
							}
							if (nMoosh > 0) {
								p.sendMessage("§7[§4§lPilzkühe§7]: §2" + nMoosh);
							}
							if (nPig > 0) {
								p.sendMessage("§7[§c§lSchweine§7]: §2" + nPig);
							}
							if (nSheep > 0) {
								p.sendMessage("§7[§f§lSchafe§7]: §2" + nSheep);
							}
							if (nHorse > 0) {
								p.sendMessage("§7[§6§lPferde§7]: §2" + nHorse);
							}
							if (nTurtle > 0) {
								p.sendMessage("§7[§2§lSchildkröten§7]: §2" + nTurtle);
							}
							if (nFish > 0) {
								p.sendMessage("§7[§b§lFische§7]: §b" + nFish);
							}
						}
					} else {
						Output.Err(p, "uReg");
					}
					break;
				}

			} else {
				Output.Err(p, "cUse15");
			}
		} else {
			Output.Err(p, "perm");
		}
		return false;

	}

	public static boolean isInBorder(Location center, org.bukkit.Location location, int range) {
		int x = center.getBlockX(), z = center.getBlockZ();
		int x1 = location.getBlockX(), z1 = location.getBlockZ();

		if (x1 >= (x + range) || z1 >= (z + range) || x1 <= (x - range) || z1 <= (z - range)) {
			return false;
		}
		return true;
	}

	public static boolean isInRegion(Location corner, org.bukkit.Location location, int l, int b) {
		int x = corner.getBlockX(), z = corner.getBlockZ();
		int x1 = location.getBlockX(), z1 = location.getBlockZ();

		if (x1 > (x + l) || z1 > (z + b) || x1 < (x) || z1 < (z)) {
			return false;
		}
		return true;
	}

	public static ArrayList<Entity> getNearbyEntities(Location where, int range) {
		ArrayList<Entity> found = new ArrayList<Entity>();

		for (Entity entity : where.getWorld().getEntities()) {
			if (isInBorder(where, entity.getLocation(), range)) {
				found.add(entity);
			}
		}
		return found;
	}

	public static ArrayList<Entity> getNearbyEntitiesRegion(Location where, int l, int b) {
		ArrayList<Entity> found = new ArrayList<Entity>();

		for (Entity entity : where.getWorld().getEntities()) {
			if (isInRegion(where, entity.getLocation(), l, b)) {
				found.add(entity);
			}
		}
		return found;
	}

}
