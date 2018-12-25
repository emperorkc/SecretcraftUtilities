package de.secretcraft.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;

import de.secretcraft.MobCounter.commands.MobCountCommand;
import de.secretcraft.animals.commands.TiereNewCommand;
import de.secretcraft.animals.listener.MenuListenerAnimal;
import de.secretcraft.animals.listener.MobLevelListener;
import de.secretcraft.bank.commands.BankCommand;
import de.secretcraft.bank.listener.BankListener;
import de.secretcraft.basiclisteners.BasicListener;
import de.secretcraft.customFlags.Listeners.HostileMobSpawn;
import de.secretcraft.customFlags.flags.Flags;
import de.secretcraft.eventplaner.Commands.ChestCommands;
import de.secretcraft.eventplaner.Commands.EventJoinCommand;
import de.secretcraft.eventplaner.Commands.JMCommand;
import de.secretcraft.eventplaner.listeners.EventJoinListener;
import de.secretcraft.eventplaner.listeners.HalloweenListener;
import de.secretcraft.eventplaner.listeners.ItemListener;
import de.secretcraft.flagBuy.commands.AvaibleFlagsCommand;
import de.secretcraft.flagBuy.commands.FlagCommands;
import de.secretcraft.flagBuy.commands.FlagsCommand;
import de.secretcraft.groupCheck.commands.VipCommand;
import de.secretcraft.shilfe.commands.GsDefCommand;

import de.secretcraft.shilfe.commands.ResetConfig;
import de.secretcraft.simpleCommands.commands.DiscordCommand;
import de.secretcraft.simpleCommands.commands.EventCommand;
import de.secretcraft.simpleCommands.commands.ForumCommand;
import de.secretcraft.simpleCommands.commands.HCommand;
import de.secretcraft.simpleCommands.commands.HResetCommand;
import de.secretcraft.simpleCommands.commands.HTopCommand;
import de.secretcraft.simpleCommands.commands.HelpCommand;
import de.secretcraft.simpleCommands.commands.MenuListenerCommands;
import de.secretcraft.simpleCommands.commands.RegelnCommand;
import de.secretcraft.simpleCommands.commands.ReparaturCommand;
import de.secretcraft.simpleCommands.commands.ScStaatListener;
import de.secretcraft.simpleCommands.commands.SpawnersCommand;
import de.secretcraft.simpleCommands.commands.TeamCommand;
import de.secretcraft.simpleCommands.commands.TutorialCommand;
import de.secretcraft.simpleCommands.commands.TwitchCommand;
import de.secretcraft.simpleCommands.commands.UmfrageCommands;
import de.secretcraft.simpleCommands.commands.VoteCommand;
import de.secretcraft.simpleCommands.commands.YoutubeCommand;
import de.secretcraft.simpleCommands.listener.LiftSignListener;
import de.secretcraft.token.commands.MenuListenerToken;
import de.secretcraft.token.commands.TokenCommands;
import de.secretcraft.voteStreaks.commands.MessengerCommand;
import de.secretcraft.voteStreaks.commands.OpenInventoryCommand;
import de.secretcraft.voteStreaks.commands.RemoveRewardCommand;
import de.secretcraft.voteStreaks.commands.VanishListener;
import de.secretcraft.voteStreaks.commands.VanishedCommand;
import de.secretcraft.voteStreaks.commands.VoteEventCommand;
import de.secretcraft.voteStreaks.commands.addRewardCommand;
import de.secretcraft.voteStreaks.commands.setStreakCommand;
import de.secretcraft.voteStreaks.listeners.MenuListener;
import de.secretcraft.voteStreaks.listeners.VersteigerungListener;
import de.secretcraft.voteStreaks.listeners.votifierListener;
import de.secretcraft.voteStreaks.commands.WerbungCommand;

public class Main extends JavaPlugin {
	private static Main plugin;

	public Main() {
		plugin = this;
	}

	public static int price = 0;

	public void onEnable() {
		try {
			MobLevelListener.createList();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		votifierListener.Config.set("Uhr", null);
		votifierListener.Config.set("Check", null);
		try {
			votifierListener.save();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		price = 0;
		if (UtilitiesConfig.getMax() == 0) {
			try {
				UtilitiesConfig.setMax(5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Commands:

		// Event-"Temporär":
		getCommand("hc").setExecutor(new ChestCommands());
		getCommand("hcadd").setExecutor(new ChestCommands());
		getCommand("hcdel").setExecutor(new ChestCommands());
		getCommand("h").setExecutor(new HCommand());
		getCommand("hreset").setExecutor(new HResetCommand());
		getCommand("htop").setExecutor(new HTopCommand());
		getCommand("jm").setExecutor(new JMCommand());
		getCommand("preset").setExecutor(new VanishedCommand());

		// Event-"Nutzen":
		getCommand("eventjoin").setExecutor(new EventJoinCommand());
		getCommand("eventmsg").setExecutor(new EventCommand());

		// Playerwatch:
		getCommand("spawners").setExecutor(new SpawnersCommand());
		getCommand("animals").setExecutor(new MobCountCommand());

		// FlagBuy:
		getCommand("flag").setExecutor(new FlagCommands());
		getCommand("flags").setExecutor(new FlagsCommand());
		getCommand("availableflags").setExecutor(new AvaibleFlagsCommand());

		// Voten:
		getCommand("serie").setExecutor(new OpenInventoryCommand());
		getCommand("token").setExecutor(new TokenCommands());
		getCommand("voteevent").setExecutor(new VoteEventCommand());
		getCommand("vote").setExecutor(new VoteCommand());
		getCommand("setstreak").setExecutor(new setStreakCommand());
		getCommand("removereward").setExecutor(new RemoveRewardCommand());
		getCommand("addreward").setExecutor(new addRewardCommand());

		// Utilities:
		getCommand("gs").setExecutor(new GsDefCommand());
		getCommand("tutorial").setExecutor(new TutorialCommand());
		getCommand("team").setExecutor(new TeamCommand());
		getCommand("regeln").setExecutor(new RegelnCommand());
		getCommand("youtube").setExecutor(new YoutubeCommand());
		getCommand("forum").setExecutor(new ForumCommand());
		getCommand("discord").setExecutor(new DiscordCommand());
		getCommand("server").setTabCompleter(new TabCompletion());
		getCommand("vip").setExecutor(new VipCommand());
		getCommand("help").setExecutor(new HelpCommand());
		getCommand("umfrage").setExecutor(new UmfrageCommands());
		getCommand("ja").setExecutor(new UmfrageCommands());
		getCommand("nein").setExecutor(new UmfrageCommands());
		getCommand("messenger").setExecutor(new MessengerCommand());
		getCommand("scureload").setExecutor(new ReparaturCommand());
		getCommand("twitch").setExecutor(new TwitchCommand());
		getCommand("tiere").setExecutor(new TiereNewCommand());
		getCommand("werbung").setExecutor(new WerbungCommand());

		// Noch in der Entwicklung:
		getCommand("konto").setTabCompleter(new TabCompletion());
		getCommand("konto").setExecutor(new BankCommand());

		// Listener:
		Bukkit.getPluginManager().registerEvents(new VersteigerungListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListenerAnimal(), this);
		Bukkit.getPluginManager().registerEvents(new LiftSignListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
		Bukkit.getPluginManager().registerEvents(new VersteigerungListener(), this);
		Bukkit.getPluginManager().registerEvents(new EventJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new VanishListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListenerCommands(), this);
		Bukkit.getPluginManager().registerEvents(new HalloweenListener(), this);
		Bukkit.getPluginManager().registerEvents(new ScStaatListener(), this);
		Bukkit.getPluginManager().registerEvents(new ResetConfig(), this);
		Bukkit.getPluginManager().registerEvents(new votifierListener(), this);
		Bukkit.getPluginManager().registerEvents(new BasicListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListenerToken(), this);
		Bukkit.getPluginManager().registerEvents(new LiftSignListener(), this);

		Bukkit.getPluginManager().registerEvents(new HostileMobSpawn(), this);

		// Entwicklung:
		Bukkit.getPluginManager().registerEvents(new MobLevelListener(), this);
		Bukkit.getPluginManager().registerEvents(new BankListener(), this);

		// Unten: Messenger-Start
		MessengerCommand m1 = new MessengerCommand();
		m1.start();

		// Unten: Vote-Auswertungs-Timer
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {

				LocalDate localDate = LocalDate.now();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				String f = sdf.format(cal.getTime());
				if (votifierListener.Config.get("Uhr." + f.substring(0, 5)) == null) {

					if (f.substring(3, 5).equals("15")) {
						try {
							votifierListener.dod();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						votifierListener.Config.set("Uhr." + f.substring(0, 5), 1);
						try {
							votifierListener.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (votifierListener.Config.get("Uhr." + f.substring(0, 5)) == null) {

					if (f.substring(3, 5).equals("45")) {
						try {
							votifierListener.dod();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						votifierListener.Config.set("Uhr." + f.substring(0, 5), 1);
						try {
							votifierListener.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}, 200, 200);
		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {

				LocalDate localDate = LocalDate.now();

				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

				String f = sdf.format(cal.getTime());
				if (votifierListener.Config.get("Checked.b." + f.substring(0, 5)) == null) {
					if (f.substring(3, 5).equals("05")) {
						try {
							votifierListener.ded();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						votifierListener.Config.set("Checked.b." + f.substring(0, 5), 1);
						try {
							votifierListener.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				if (votifierListener.Config.get("Checked.b." + f.substring(0, 5)) == null) {
					if (f.substring(3, 5).equals("35")) {
						try {
							votifierListener.ded();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						votifierListener.Config.set("Checked.b." + f.substring(0, 5), 1);
						try {
							votifierListener.save();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}, 200, 200);

	}

	public static Main getPlugin() {
		return plugin;
	}

	public void onLoad() {
		FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
		try {
			// register our flag with the registry
			registry.register(Flags.HostileMobSpawn);
			registry.register(Flags.CONSOLE_COMMAND_ON_ENTRY);
			registry.register(Flags.CONSOLE_COMMAND_ON_EXIT);
			getLogger().log(Level.INFO, "Loaded all Flags");
		} catch (FlagConflictException e) {
			// some other plugin registered a flag by the same name already.
			// you may want to re-register with a different name, but this
			// could cause issues with saved flags in region files. it's better
			// to print a message to let the server admin know of the conflict
		}
	}
}