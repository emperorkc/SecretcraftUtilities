package de.secretcraft.flagBuy.commands;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import net.milkbowl.vault.economy.Economy;

public class FlagCommands implements CommandExecutor {
	static String prefix = UtilitiesConfig.getPrefix();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;
		Plugin worldguard = p.getServer().getPluginManager().getPlugin("WorldGuard");
		WorldGuard wg = WorldGuard.getInstance();
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1=BukkitAdapter.adapt(world);
		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);
		
		LocalPlayer localPlayer = ((WorldGuardPlugin) worldguard).wrapPlayer(p);
		String command1 = "";
		if (args.length != 0) {
			command1 = args[0];
		} else {
			command1 = "n";
		}
		switch (command1) {
		case "kaufen":
			if (p.hasPermission("scu.flag.kaufen")) {

				if (args.length == 2 || args.length == 3 || args.length == 1) {
					Output.Err(p, "cUse2");
				} else {

					String region = args[1];
					ProtectedRegion rg = regions.getRegion(region);
					String flagname = args[2];
					String arg = args[3];

					if (Config.isDenied(flagname, region)) {
						Output.Err(p, "nBuy01");
					} else {

						if (rg != null) {
							if (rg.isOwner(localPlayer)) {

								boolean x = Config.isInConfig(flagname);
								if (x == true) {

									if (flagname.equals("greeting") || flagname.equals("farewell")) {
										setFlagGreeting(p, region, flagname, args);

									} else {

										setFlag(p, region, flagname, arg);

									}
								} else {
									p.sendMessage(
											prefix+" §cDiese Flag gibt es nicht zu kaufen. Benutze /flags um die kaufbaren Flags zu sehen.");
								}

							} else {
								p.sendMessage(prefix+" §cDiese Region gehört dir nicht.");
							}
						} else {
							p.sendMessage(
									prefix+" §cDiese Region existiert nicht oder befindet sich in einer anderen Welt.");
						}
					}
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "add":

			if (p.hasPermission("scu.flag.add")) {
				if (args.length == 2 || args.length == 1 || args.length >= 4) {
					Output.Err(p, "cUse3");
				} else {
					String flagname = args[1];
					String preis1 = args[2];
					double preis = Double.parseDouble(preis1);
					boolean x = Config.isInConfig(flagname);
					if (x == false) {
						if (isFlag(flagname)) {

							try {
								Config.addFlag(flagname, preis);
								Output.suc(p, "sAdd01");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							Output.Err(p, "invFlag");

						}
					} else {
						Output.Err(p, "aRegi");
					}
				}
			} else {
				Output.Err(p, "perm");

			}
			break;
		case "remove":
			if (p.hasPermission("scu.flag.remove")) {
				if (args.length == 1 || args.length >= 3) {
					Output.Err(p, "cUse04");
				} else {
					String flagname = args[1];
					boolean x = Config.isInConfig(flagname);
					if (x == true) {

						try {
							Config.removeFlag(flagname);
							Output.suc(p, "sRem");
						} catch (IOException e) {

							e.printStackTrace();
						}
					} else {
						Output.Err(p, "nReg");
					}
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "preis":
			if (p.hasPermission("scu.flag.preis")) {
				if (args.length == 1 || args.length >= 3) {
					Output.Err(p, "cUse5");
				} else {

					String flagname = args[1];
					if (Config.isInConfig(flagname)) {
						double Preis = Config.getPreis(flagname);
						p.sendMessage(prefix+" §bDie Flag§2 " + flagname + " §bkostet §2" + Preis
								+ " §bSD.");
					} else {
						Output.Err(p, "nReg");
					}
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "info":
			if (p.hasPermission("scu.flag.info")) {
				if (args.length == 1 || args.length >= 3) {
					Output.Err(p, "cUse6");
				} else {

					String flagname = args[1];
					if (isFlag(flagname)) {
						switch (flagname) {
						case "pvp":
							Output.suc(p, "info1");
							break;
						case "greeting":
							Output.suc(p, "info2");
							break;
						case "farewell":
							Output.suc(p, "info3");
							break;
						case "snow-fall":
							Output.suc(p, "info4");
							break;
						case "creeper-explosion":
							Output.suc(p, "info5");
							break;
						case "other-explosion":
							Output.suc(p, "info6");
							break;
						case "tnt":
							Output.suc(p, "info7");
							break;
						case "lighter":
							Output.suc(p, "info8");
							break;
						case "ice-melt":
							Output.suc(p, "info9");
							break;
						case "ice-form":
							Output.suc(p, "info10");
							break;
						case "snow-melt":
							Output.suc(p, "info11");
							break;
						case "build":
							Output.suc(p, "info12");
							break;
						case "mob-spawning":
							Output.suc(p, "info13");
							break;
						case "enderpearl":
							Output.suc(p, "info14");
							break;
						case "hostilemobspawn":
							Output.suc(p, "info15");
							break;
						}
					} else {
						Output.Err(p, "invFlag");
					}
				}
			}
			break;
		case "change":
			if (p.hasPermission("scu.flag.preis")) {
				if (args.length <= 2 || args.length >= 4) {
					Output.Err(p, "cUse7");
				} else {
					String flagname = args[1];
					String preis = args[2];
					double preisdouble = Double.parseDouble(preis);
					Config.set("Flags." + flagname + ".Preis", preisdouble);
					p.sendMessage(prefix+" §bDie Flag§2 " + flagname + " §bkostet nun §2"
							+ preisdouble + " §bSD.");
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "del":

			if (p.hasPermission("scu.flag.del")) {
				if (args.length > 2 && args.length < 4) {
					String region = args[1];
					String flagname = args[2];

					String argss[] = new String[4];
					argss[3] = "del";

					if (flagname.equals("greeting") || flagname.equals("farewell")) {
						setFlagGreeting(p, region, flagname, null);

					} else {
						setFlag(p, region, flagname, null);

					}

				} else {
					Output.Err(p, "cUse8");
				}
			}
			break;
		case "forbid":

			if (p.hasPermission("scu.flag.forbid")) {

				if (args.length < 3 || args.length > 3) {
					Output.Err(p, "cUse13");
				} else {
					String region = args[1];
					String flagname = args[2];
					if (isFlag(flagname)) {
						if (!Config.getForbiddenRegion(flagname, region)) {
							try {
								Config.setForbid(flagname, region);
								Output.suc(p, "sDeny");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {

							Output.Err(p, "aForb");

						}

					} else {
						Output.Err(p, "nReg");

					}
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "allow":
			if (p.hasPermission("scu.flag.allow")) {

				if (args.length < 3 || args.length > 3) {
					Output.Err(p, "cUse14");
				} else {

					String region = args[1];
					String flagname = args[2];
					if (isFlag(flagname)) {
						if (Config.isDenied(flagname, region)) {
							if (isFlag(flagname)) {
								if (Config.getForbiddenRegion(flagname, region)) {
									try {
										Config.removeForbid(flagname, region);
										Output.suc(p, "sAllow");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {

									Output.suc(p, "sAllow");
								}
							} else {
								Output.Err(p, "nReg");
							}
						} else {
							Output.Err(p, "nForbid");
						}
					} else {
						Output.Err(p, "nReg");
					}
				}
			} else {
				Output.Err(p, "perm");
			}
			break;
		case "forbiddenflags":
			if (p.hasPermission("scu.flag.forbiddenflags")) {
				Output.suc(p, "forbFlags");
			}
			break;
		case "help":
			if (p.hasPermission("scu.flag.kaufen") && p.hasPermission("scu.flag.forbid")) {
				Output.suc(p, "helpAdmin");
			} else {
				Output.suc(p, "helpUser");
			}
			break;
		default:
			if (p.hasPermission("scu.flag.kaufen") && p.hasPermission("scu.flag.allow"))
				Output.Err(p, "cUse10");
			else if (p.hasPermission("scu.flag.kaufen"))
				Output.Err(p, "cUse11");
			break;
		}

		return false;

	}

	public boolean isFlag(String flagname) {
		if (flagname.equals("snow-fall") || flagname.equals("pvp") || flagname.equals("build")
				|| flagname.equals("mob-spawning") || flagname.equals("creeper-explosion")
				|| flagname.equals("enderpearl") || flagname.equals("other-explosion") || flagname.equals("tnt")
				|| flagname.equals("lighter") || flagname.equals("snow-melt") || flagname.equals("ice-melt")
				|| flagname.equals("ice-form") || flagname.equals("greeting") || flagname.equals("farewell")
				|| flagname.equals("hostilemobspawn")) {
			return true;
		}
		return false;
	}

	public void setFlagGreeting(Player p, String regionname, String flagname, String[] args) {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		Economy eco = rsp.getProvider();
		Plugin worldguard = p.getServer().getPluginManager().getPlugin("WorldGuard");
		WorldGuard wg = (WorldGuard) worldguard;
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1=BukkitAdapter.adapt(world);
		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);
		ProtectedRegion rg = regions.getRegion(regionname);
		String text = "";
		String arg[] = new String[4];
		arg[3]="lel";
		if (args == null) {

			arg[3] = "del";
		} else {
			text = args[3];
		}
		if (arg[3].equals("del")) {
			if (flagname.equals("greeting")) {
				rg.setFlag(Flags.GREET_MESSAGE, null);
				Output.suc(p, "sRem");
			}
			if (flagname.equals("farewell")) {
				rg.setFlag(Flags.FAREWELL_MESSAGE, null);
				Output.suc(p, "sRem");
			}
		} else {
			if (eco.getBalance(p.getName()) >= Config.getPreis(flagname)) {
				double preis = Config.getPreis(flagname);
				if (args.length > 3) {
					for (int i = 4; i < args.length; i++) {
						text = text + " " + args[i];
					}
				}

				Output.suc(p, "sBuy01");
				eco.withdrawPlayer(p.getName(), Config.getPreis(flagname));
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give SC_Staat " + preis + "");
				if (flagname.equals("greeting")) {
					rg.setFlag(Flags.GREET_MESSAGE, "" + text + "");

				}
				if (flagname.equals("farewell")) {
					rg.setFlag(Flags.FAREWELL_MESSAGE, "" + text + "");

				}

			}
		}
	}

	public void setFlag(Player p, String regionname, String flagname, String arg) {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		Economy eco = rsp.getProvider();
		
		Plugin worldguard = p.getServer().getPluginManager().getPlugin("WorldGuard");
		WorldGuard wg = WorldGuard.getInstance();
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1=BukkitAdapter.adapt(world);
		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);
		ProtectedRegion rg = regions.getRegion(regionname);
		if (eco.getBalance(p.getName()) >= Config.getPreis(flagname)) {

			double preis = Config.getPreis(flagname);
			if (arg == null) {
				arg = "null";
			}
			if (isFlag(flagname)) {
				boolean u = false;
				if (!arg.equals("null")) {
					u = FlagState.hasSameValue(flagname, arg, p, regionname);
				}
				if (u != true) {

					if (!arg.equals("null")) {
						eco.withdrawPlayer(p.getName(), Config.getPreis(flagname));

						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "money give SC_Staat " + preis + "");
						Output.suc(p, "sBuy01");
					}
					switch (flagname) {
					case "snow-fall":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.SNOW_FALL, null);
							break;
						case "aus":

							rg.setFlag(Flags.SNOW_FALL, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.SNOW_FALL, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "pvp":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.PVP, null);
							break;
						case "aus":
							rg.setFlag(Flags.PVP, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.PVP, StateFlag.State.ALLOW);
							break;
						}
						break;
					case "ice-melt":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.ICE_MELT, null);
							break;
						case "aus":
							rg.setFlag(Flags.ICE_MELT, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.ICE_MELT, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "build":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.BUILD, null);
							break;
						case "aus":
							rg.setFlag(Flags.BUILD, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.BUILD, StateFlag.State.ALLOW);
							break;
						}
						break;
					case "mob-spawning":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.MOB_SPAWNING, null);
							break;
						case "aus":
							rg.setFlag(Flags.MOB_SPAWNING, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.MOB_SPAWNING, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "creeper-explosion":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.CREEPER_EXPLOSION, null);
							break;
						case "aus":
							rg.setFlag(Flags.CREEPER_EXPLOSION, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "enderpearl":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.ENDERPEARL, null);
							break;
						case "aus":
							rg.setFlag(Flags.ENDERPEARL, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.ENDERPEARL, StateFlag.State.ALLOW);
							break;
						}
						break;
					case "other-explosion":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.OTHER_EXPLOSION, null);
							break;
						case "aus":
							rg.setFlag(Flags.OTHER_EXPLOSION, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.OTHER_EXPLOSION, StateFlag.State.ALLOW);
							break;
						}
						break;
					case "tnt":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.TNT, null);
							break;
						case "aus":
							rg.setFlag(Flags.TNT, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.TNT, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "lighter":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.LIGHTER, null);
							break;
						case "aus":
							rg.setFlag(Flags.LIGHTER, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.LIGHTER, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "snow-melt":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.SNOW_MELT, null);
							break;
						case "aus":
							rg.setFlag(Flags.SNOW_MELT, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.SNOW_MELT, StateFlag.State.ALLOW);
							break;
						}
						break;

					case "ice-form":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							rg.setFlag(Flags.ICE_FORM, null);
							break;
						case "aus":
							rg.setFlag(Flags.ICE_FORM, StateFlag.State.DENY);
							break;
						case "ein":
							rg.setFlag(Flags.ICE_FORM, StateFlag.State.ALLOW);
							break;

						}
						break;
					case "hostilemobspawn":
						switch (arg) {
						case "null":
							Output.suc(p, "sRem");
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"rg flag " + regionname + " hostilemobspawn -w " + worldname + "");
							break;

						case "aus":
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"rg flag " + regionname + " hostilemobspawn -w " + worldname + " deny");
							break;
						case "ein":
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
									"rg flag " + regionname + " hostilemobspawn -w " + worldname + " allow");
							break;
						}
						break;

					default:
						Output.Err(p, "wFlag01");
					}
				} else {
					Output.Err(p, "sFlag01");
				}
			} else {
				Output.Err(p, "wFlag01");
			}
		} else {
			Output.Err(p, "nMoney");
		}

	}
}
