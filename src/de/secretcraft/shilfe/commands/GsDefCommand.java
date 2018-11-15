package de.secretcraft.shilfe.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.domains.PlayerDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.commands.List;

public class GsDefCommand implements CommandExecutor {

	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Enteignet.yml");
	public static FileConfiguration Config1 = YamlConfiguration.loadConfiguration(ConfigFile);

	public static void save() throws IOException {
		Config1.save(ConfigFile);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = UtilitiesConfig.getPrefix();
		Player k = (Player) sender;
		BukkitPlayer p = BukkitAdapter.adapt(k);
		Plugin worldguard = k.getServer().getPluginManager().getPlugin("WorldGuard");
		Plugin worldedit = k.getServer().getPluginManager().getPlugin("WorldEdit");

		WorldGuard wg = WorldGuard.getInstance();
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1 = BukkitAdapter.adapt(world);
		String command1 = null;

		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);
		LocalSession sel = WorldEdit.getInstance().getSessionManager().get(p);

		Vector maximum = null;
		Vector minimum = null;

		int number = 0;
		if (args.length > 1 && !args[0].equals("del") && !args[0].equals("info") && !args[0].equals("help")
				&& !args[0].equals("enteignen")) {
			number = Integer.parseInt(args[1]);
		}
		if (args.length != 0) {
			command1 = args[0];

			switch (command1) {
			case "enteignen":
				if (k.hasPermission("scu.enteignen")) {
					if (args.length > 1) {

						if (args.length < 3) {

							ProtectedRegion rg = regions.getRegion(args[1]);
							if (rg != null && rg.getPriority() >= 0) {

								int maxx = rg.getMaximumPoint().getBlockX();
								int minx = rg.getMinimumPoint().getBlockX();
								int maxz = rg.getMaximumPoint().getBlockZ();
								int minz = rg.getMinimumPoint().getBlockZ();
								int abstandx = maxx - minx;
								int abstandz = maxz - minz;

								int blocks = 0;
								for (int i = 1; i < 256; i++) {
									for (int j = 0; j <= abstandx; j++) {
										for (int l = 0; l <= abstandz; l++) {
											Location temp = new Location(Bukkit.getWorld(p.getWorld().getName()),
													minx + j, i, minz + l);
											if (temp.getBlock().getType().equals(Material.BEACON)
													|| temp.getBlock().getType().equals(Material.CHEST)
													|| temp.getBlock().getType().equals(Material.DIAMOND_BLOCK)
													|| temp.getBlock().getType().equals(Material.GOLD_BLOCK)
													|| temp.getBlock().getType().equals(Material.TRAPPED_CHEST)

													
													|| temp.getBlock().getType().equals(Material.BLACK_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.BLUE_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.BROWN_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.CYAN_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.GRAY_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.GREEN_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.LIME_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.LIGHT_GRAY_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.MAGENTA_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.ORANGE_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.PINK_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.PURPLE_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.RED_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.WHITE_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.YELLOW_SHULKER_BOX)
													|| temp.getBlock().getType().equals(Material.ITEM_FRAME)) {

												blocks++;
											}
										}
									}
								}

								Location loc = new Location(k.getWorld(), rg.getMinimumPoint().getBlockX(),
										rg.getMinimumPoint().getBlockY(), rg.getMinimumPoint().getBlockZ());
								ArrayList<Entity> eList = getNearbyEntitiesRegion(loc, abstandx, abstandz);

								int ent = 0;
								for (int i = 0; i < eList.size(); i++) {
									ent++;

								}

								k.sendMessage("§aBlöcke und Truhen: §6" + blocks + "§a.");
								
								k.sendMessage("§aDie Enteignung der Region §6" + rg.getId()
										+ " §akann §4nicht §arückgängig gemacht werden!");

								k.sendMessage("§aZum Enteignen §6/gs enteignen " + rg.getId()
										+ " CONFIRM §aeingeben.");

							} else {
								k.sendMessage(prefix + " §cRegion muss existieren und darf keine Stadtregion sein!");
							}
						} else {
							if (args[2].equals("confirm") || args[2].equals("CONFIRM")) {
								ProtectedRegion rg = regions.getRegion(args[1]);
								if (rg != null && rg.getPriority() >= 0) {

									int maxx = rg.getMaximumPoint().getBlockX();
									int minx = rg.getMinimumPoint().getBlockX();
									int maxz = rg.getMaximumPoint().getBlockZ();
									int minz = rg.getMinimumPoint().getBlockZ();
									int abstandx = maxx - minx;
									int abstandz = maxz - minz;

									int blocks = 0;
									for (int i =rg.getMinimumPoint().getBlockY(); i < rg.getMaximumPoint().getBlockY(); i++) {
										for (int j = 0; j <= abstandx; j++) {
											for (int l = 0; l <= abstandz; l++) {
												Location temp = new Location(Bukkit.getWorld(p.getWorld().getName()),
														minx + j, i, minz + l);
												if (temp.getBlock().getType().equals(Material.BEACON)
														|| temp.getBlock().getType().equals(Material.CHEST)
														|| temp.getBlock().getType().equals(Material.DIAMOND_BLOCK)
														|| temp.getBlock().getType().equals(Material.GOLD_BLOCK)
														|| temp.getBlock().getType().equals(Material.TRAPPED_CHEST)

														
														|| temp.getBlock().getType().equals(Material.BLACK_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.BLUE_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.BROWN_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.CYAN_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.GRAY_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.GREEN_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.LIME_SHULKER_BOX)
														|| temp.getBlock().getType()
																.equals(Material.LIGHT_BLUE_SHULKER_BOX)
														|| temp.getBlock().getType()
																.equals(Material.LIGHT_GRAY_SHULKER_BOX)
														|| temp.getBlock().getType()
																.equals(Material.MAGENTA_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.ORANGE_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.PINK_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.PURPLE_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.RED_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.WHITE_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.YELLOW_SHULKER_BOX)
														|| temp.getBlock().getType().equals(Material.ITEM_FRAME)
														|| temp.getBlock().getType().equals(Material.WALL_SIGN)
														|| temp.getBlock().getType().equals(Material.SIGN)) {
													temp.getBlock().setType(Material.AIR);
													blocks++;
												}
											}
										}
									}
									Location loc = new Location(k.getWorld(), rg.getMinimumPoint().getBlockX(),
											rg.getMinimumPoint().getBlockY(), rg.getMinimumPoint().getBlockZ());
									ArrayList<Entity> eList = getNearbyEntitiesRegion(loc, abstandx, abstandz);

									int ent = 0;
									for (int i = 0; i < eList.size(); i++) {
										ent++;
										Entity e = eList.get(i);
										if (!(e instanceof Player)) {
											e.remove();
										}
									}

									k.sendMessage(
											"§aRegion §6" + rg.getId() + " §aerfolgreich enteignet und gelöscht.");
									k.sendMessage("§6Area-Shop §aRegion gelöscht.");
									k.sendMessage("§aEnteignete Region in Config eingetragen.");
									Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "as del " + rg.getId());
									
									/*	Config1.set("Enteignet." + rg.getId() + ".enteigner", p.getName());
										try {
											save();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									*/
									k.sendMessage("§6§l" + ent + " Entitys §agelöscht, §6§l" + blocks
											+ " Blöcke§a gelöscht.");
									k.sendMessage("§aEnteignen der Region §6" + rg.getId() + " §aerfolgreich!");
									regions.removeRegion(rg.getId());
								} else {
									k.sendMessage(
											prefix + " §cRegion muss existieren und darf keine Stadtregion sein!");
								}
							} else {
								k.sendMessage(prefix + " §cKorrekte Nutzung: /gs enteignen <region> CONFIRM");
							}
						}
					} else {
						k.sendMessage(prefix + "§cKorrekte Nutzung: /gs enteignen <region>");
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			case "vert":
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) > 0) {
							Output.suc(k, "sVert");
							try {
								Config.addVert(k);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							if (minimum.getBlockY() > maximum.getBlockY()) {
								try {
									Config.addMax(k, 255 - minimum.getBlockY());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								try {
									Config.addMin(k, maximum.getBlockY());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								try {
									Config.addMax(k, 255 - maximum.getBlockY());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								try {
									Config.addMin(k, maximum.getBlockY());
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							try {
								Config.addPlayer(k, Config.getPlayer(k) + 1);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Output.Err(k, "nName");
						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					Output.Err(k, "perm");
				}
				break;
			case "up":
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) >= 1) {
							if (number > 0) {
								if (Config.getVert(k) == 0) {
									k.sendMessage(prefix + " §2Erweiterung um §3" + number
											+ "§2 Blöcke nach oben erfolgreich. Bestätige deine Auswahl mit §4/gs confirm"); // nicht
																																// in
																																// Error
																																// Datei,
																																// wegen
																																// Variable
									try {
										Config.addUp(k);
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									if (minimum.getBlockY() < maximum.getBlockY()) {
										try {
											Config.addMax(k, number);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else {
										try {
											Config.addMax(k, number);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
									try {
										Config.addPlayer(k, Config.getPlayer(k) + 1);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									Output.Err(k, "aExp");
								}
							} else {
								Output.Err(k, "cUse19");
							}
						} else {

							Output.Err(k, "nName");
						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			case "down":
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) >= 1) {
							if (number > 0) {
								if (Config.getVert(k) == 0) {
									k.sendMessage(prefix + " §2Erweiterung um §3" + number
											+ "§2 Blöcke nach unten erfolgreich. Bestätige deine Auswahl mit §4/gs confirm"); // nicht
																																// in
																																// Error
																																// Datei,
																																// wegen
																																// Variable
									try {
										Config.addDown(k);
									} catch (IOException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}

									if (minimum.getBlockY() > maximum.getBlockY()) {
										try {
											Config.addMin(k, number);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									} else {
										try {
											Config.addMin(k, number);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
									try {
										Config.addPlayer(k, Config.getPlayer(k) + 1);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									Output.Err(k, "aExp");

								}
							} else {
								Output.Err(k, "cUse20");
							}
						} else {

							Output.Err(k, "nName");
						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			case "help":
				if (k.hasPermission("scu.gsdef")) {
					Output.suc(k, "info");
				} else {
					Output.Err(k, "perm");
				}
				break;
			case "confirm":
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) >= 1) {
							String name = Config.getRegion(k);

							ProtectedRegion rg = regions.getRegion("name");

							if (maximum.getY() < minimum.getBlockY()) {
								if (Config.getVert(k) != 0) {
									maximum = maximum.subtract(0, Config.getMin(k), 0);
									minimum = minimum.add(0, Config.getMax(k), 0);
								} else if (Config.getDown(k) != 0) {
									if (Config.getUp(k) != 0) {
										maximum = maximum.subtract(0, Config.getMin(k), 0);
										minimum = minimum.add(0, Config.getMax(k), 0);
									} else {
										maximum = maximum.subtract(0, Config.getMin(k), 0);
									}
								} else if (Config.getUp(k) != 0) {
									if (Config.getDown(k) != 0) {
										minimum = minimum.add(0, Config.getMax(k), 0);
										maximum = maximum.subtract(0, Config.getMin(k), 0);
									} else {
										minimum = minimum.add(0, Config.getMax(k), 0);
									}

								}
							} else {
								if (Config.getVert(k) != 0) {
									minimum = minimum.subtract(0, Config.getMin(k), 0);
									maximum = maximum.add(0, Config.getMax(k), 0);
								} else if (Config.getDown(k) != 0) {
									if (Config.getUp(k) != 0) {
										minimum = minimum.subtract(0, Config.getMin(k), 0);
										maximum = maximum.add(0, Config.getMax(k), 0);
									} else {
										minimum = minimum.subtract(0, Config.getMin(k), 0);
									}
								} else if (Config.getUp(k) != 0) {
									if (Config.getDown(k) != 0) {
										maximum = maximum.add(0, Config.getMax(k), 0);
										minimum = minimum.subtract(0, Config.getMin(k), 0);
									} else {
										maximum = maximum.add(0, Config.getMax(k), 0);
									}

								}
							}

							if (rg == null) {

								ProtectedCuboidRegion reg = new ProtectedCuboidRegion(name, new BlockVector(maximum),
										new BlockVector(minimum));
								DefaultDomain owners = new DefaultDomain();
								owners.addPlayer(((WorldGuardPlugin) worldguard).wrapPlayer(k));
								regions.addRegion(reg);
								reg.setOwners(owners);
								reg.setPriority(0);
								k.sendMessage(prefix + " §2Grundstück: §3§l" + name + "§2 erfolgreich erstellt. ");
								try {
									Config.addP(k);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								Output.Err(k, "aExist");
							}

						} else {
							Output.Err(k, "nName");

						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;

			case "redo":
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) >= 0) {
							try {
								Config.addP(k);
								Output.suc(k, "sRedo");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Output.Err(k, "eRedo");
						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			case "del":

				LocalPlayer localPlayer = ((WorldGuardPlugin) worldguard).wrapPlayer(k);
				if (k.hasPermission("scu.gsdel")) {
					if (args.length != 0) {
						ProtectedRegion rg = regions.getRegion(args[1]);
						if (rg == null) {
							Output.Err(k, "nExist");
						} else {
							if (!rg.isOwner(localPlayer)) {
								Output.Err(k, "nOwner");
							} else {
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
										"rg remove -w " + worldname + " " + args[1]);
								Output.suc(k, "sDel");
							}
						}
					} else {
						Output.Err(k, "cUse21");
					}
				}
				break;
			case "info":
				if (k.hasPermission("scu.gsinfo")) {
					if (args.length == 1) {
						Vector v = new Vector(p.getLocation().getBlockX(), p.getLocation().getBlockY(),
								p.getLocation().getBlockZ());
						java.util.List<String> l1 = regions.getApplicableRegionsIDs(v);
						ProtectedRegion largest = null;
						ProtectedRegion rg = regions.getRegion(l1.get(0));
						largest = rg;
						for (int i = 0; i < l1.size(); i++) {
							rg = regions.getRegion(l1.get(i));
							ProtectedRegion rg1 = null;

							if (i < l1.size() - 1) {
								rg1 = regions.getRegion(l1.get(i + 1));

							}

							if (rg1 != null) {
								if (rg.getPriority() >= rg1.getPriority() && largest.getPriority() < rg.getPriority()) {
									largest = rg;
								}

							}
						}
						List<ProtectedRegion> lr = new List<>();
						lr.toFirst();
						for (int i = 0; i < l1.size(); i++) {
							rg = regions.getRegion(l1.get(i));
							if (rg != null) {
								if (rg.getPriority() == largest.getPriority()) {
									lr.append(rg);
								}
							}
						}
						lr.toFirst();
						while (lr.hasAccess()) {
							DefaultDomain d = lr.getObject().getOwners();
							DefaultDomain h = lr.getObject().getMembers();
							PlayerDomain g = d.getPlayerDomain();
							PlayerDomain m = h.getPlayerDomain();
							Set<UUID> s1 = g.getUniqueIds();
							Set<UUID> s2 = m.getUniqueIds();
							Iterator i1 = s1.iterator();
							Iterator i2 = s2.iterator();
							List<String> l6 = new List<>();
							List<String> l7 = new List<>();

							while (i1.hasNext()) {

								OfflinePlayer p5 = Bukkit.getOfflinePlayer(UUID.fromString(i1.next().toString()));
								l6.append(p5.getName());

							}
							while (i2.hasNext()) {
								OfflinePlayer p5 = Bukkit.getOfflinePlayer(UUID.fromString(i2.next().toString()));
								l7.append(p5.getName());

							}

							k.sendMessage(
									"§8» §8§m------------§r §7— §6§lGrundstücks-Info §7— §8§m------------§r §8«");
							
							k.sendMessage("§8x §bRegion:    §3§l" + lr.getObject().getId());
							k.sendMessage("§8x §bPriorität: §3§l" + lr.getObject().getPriority());
							
							k.sendMessage("");

							
							l6.toFirst();
							
							while (l6.hasAccess()) {

								OfflinePlayer p2 = Bukkit.getOfflinePlayer(l6.getObject());
								long secondssince = (System.currentTimeMillis() - p2.getLastPlayed()) / 1000;

								int tage = (int) Math.floor((secondssince / (24 * 60 * 60)));
								String tage1;
								if(tage>=14) {
									tage1="§4"+tage;
									
								} else {
									tage1="§2"+tage;
								}
								if (Bukkit.getPlayer(l6.getObject()) == null) {

									k.sendMessage("§8x §cBesitzer: " + l6.getObject() + " §c[§c Offline seit: " + tage1
											+ "§c Tagen§c ]");
								} else {
									k.sendMessage("§8x §cBesitzer: " + l6.getObject() + " §c[ §aOnline!§c ]");
								}
								l6.next();

							}
							l7.toFirst();

							if (!l7.isEmpty()) {

								while (l7.hasAccess()) {

									OfflinePlayer p2 = Bukkit.getOfflinePlayer(l7.getObject());
									long secondssince = (System.currentTimeMillis() - p2.getLastPlayed()) / 1000;

									int tage = (int) Math.floor((secondssince / (24 * 60 * 60)));
									String tage1;
									if(tage>=14) {
										tage1="§4"+tage;
										
									} else {
										tage1="§2"+tage;
									}
									if (Bukkit.getPlayer(l7.getObject()) == null) {
										k.sendMessage("§8x §aMitglied:   " + l7.getObject() + " §a[ §cOffline seit: "
												+ tage1 + "§c Tagen §a]");
									} else {
										k.sendMessage("§8x §aMitglied:   " + l7.getObject() + " §a[ §aOnline!§a ]");
									}
									l7.next();

								}
							}
							int minx=(int) lr.getObject().getMinimumPoint().getX();
							int miny=(int) lr.getObject().getMinimumPoint().getY();
							int minz=(int) lr.getObject().getMinimumPoint().getZ();
							int maxx=(int) lr.getObject().getMaximumPoint().getX();
							int maxy=(int) lr.getObject().getMaximumPoint().getY();
							int maxz=(int) lr.getObject().getMaximumPoint().getZ();

							k.sendMessage("");
							k.sendMessage("§8x §bKoordinaten: §a("+minx+" | "+miny+" | "+minz+")  §e--->  §a("+maxx+" | "+maxy+" | "+maxz+") ");
							k.sendMessage(
									"§8» §8§m------------§r §7— §6§lGrundstücks-Info §7— §8§m------------§r §8«");
							lr.next();
						}
					} else {

						ProtectedRegion region = regions.getRegion(args[1]);
						if (region != null) {
							DefaultDomain d = region.getOwners();
							DefaultDomain h = region.getMembers();
							PlayerDomain g = d.getPlayerDomain();
							PlayerDomain m = h.getPlayerDomain();
							Set<UUID> s1 = g.getUniqueIds();
							Set<UUID> s2 = m.getUniqueIds();
							Iterator i1 = s1.iterator();
							Iterator i2 = s2.iterator();
							List<String> l6 = new List<>();
							List<String> l7 = new List<>();

							while (i1.hasNext()) {

								OfflinePlayer p5 = Bukkit.getOfflinePlayer(UUID.fromString(i1.next().toString()));
								l6.append(p5.getName());

							}
							while (i2.hasNext()) {
								OfflinePlayer p5 = Bukkit.getOfflinePlayer(UUID.fromString(i2.next().toString()));
								l7.append(p5.getName());

							}

							k.sendMessage("§c§l===============§3§lGrundstücks§4-§3§lInfo§c§l===============");
							k.sendMessage("");
							k.sendMessage("§3Region: §6§l" + region.getId());
							k.sendMessage("§3Priorität: §6§l" + region.getPriority());
							l6.toFirst();

							while (l6.hasAccess()) {

								OfflinePlayer p2 = Bukkit.getOfflinePlayer(l6.getObject());
								long secondssince = (System.currentTimeMillis() - p2.getLastPlayed()) / 1000;

								int tage = (int) Math.floor((secondssince / (24 * 60 * 60)));
								if (Bukkit.getPlayer(l6.getObject()) == null) {

									k.sendMessage("§4Besitzer: " + l6.getObject() + " §4[§c Offline seit: §e" + tage
											+ "§c Tagen§4 ]");
								} else {
									k.sendMessage("§4Besitzer: " + l6.getObject() + " §4[ §eOnline!§4 ]");
								}
								l6.next();

							}
							l7.toFirst();

							if (!l7.isEmpty()) {

								while (l7.hasAccess()) {

									OfflinePlayer p2 = Bukkit.getOfflinePlayer(l7.getObject());
									long secondssince = (System.currentTimeMillis() - p2.getLastPlayed()) / 1000;

									int tage = (int) Math.floor((secondssince / (24 * 60 * 60)));
									if (Bukkit.getPlayer(l7.getObject()) == null) {
										k.sendMessage("§2Mitglied:   " + l7.getObject() + " §2[ §cOffline seit: §e"
												+ tage + "§c Tagen §2]");
									} else {
										k.sendMessage("§2Mitglied:   " + l7.getObject() + " §2[ §eOnline!§2 ]");
									}
									l7.next();

								}
							}
							k.sendMessage("");
							k.sendMessage("§c§l=============================================");
							k.sendMessage("");
						} else {

						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			default:
				if (k.hasPermission("scu.gsdef")) {
					if (sel != null) {
						try {
							maximum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMinimumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							minimum = sel.getSelection((com.sk89q.worldedit.world.World) world1).getMaximumPoint();
						} catch (IncompleteRegionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (sel != null) {
						if (Config.getPlayer(k) == 0) {
							String name = command1;
							ProtectedRegion rg = regions.getRegion(name);
							if (rg == null) {
								try {
									Config.addRegion(k, name);
									Config.addPlayer(k, Config.getPlayer(k) + 1);
									Output.suc(k, "nowNext1");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								Output.Err(k, "aExist");
							}
						} else {
							Output.Err(k, "aName");

						}
					} else {
						Output.Err(k, "nSel");
						try {
							Config.addP(k);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					Output.Err(k, "perm");
				}
				break;
			}

		} else {
			Output.Err(k, "cUse18");
		}
		return false;
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

	public static boolean isInRegion(Location corner, org.bukkit.Location location, int l, int b) {
		int x = corner.getBlockX(), z = corner.getBlockZ();
		int x1 = location.getBlockX(), z1 = location.getBlockZ();

		if (x1 > (x + l) || z1 > (z + b) || x1 < (x) || z1 < (z)) {
			return false;
		}
		return true;
	}

}
