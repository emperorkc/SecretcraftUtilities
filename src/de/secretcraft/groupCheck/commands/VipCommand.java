package de.secretcraft.groupCheck.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LocalizedNode;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.PermissionHolder;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.event.user.track.UserPromoteEvent;
import me.lucko.luckperms.api.manager.UserManager;
import net.milkbowl.vault.economy.Economy;

public class VipCommand implements CommandExecutor {
	String prefix = UtilitiesConfig.getPrefix();
	RegisteredServiceProvider<LuckPermsApi> provider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);

	LuckPermsApi api = provider.getProvider();

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {

		Player p = (Player) arg0;
		if (p.hasPermission("scu.vip")) {
			User user = loadUser(p);

			SortedSet<LocalizedNode> grou = user.getAllNodes();
			Iterator iterr = grou.iterator();
			int i = 0;
			while (iterr.hasNext()) {

				LocalDate localDate = LocalDate.now();

				LocalizedNode n1 = (LocalizedNode) iterr.next();
				if (!n1.isPermanent()) {
					i = 1;

					int seconds = (int) n1.getSecondsTilExpiry();
					   int day = (int)TimeUnit.SECONDS.toDays(seconds);        
				        long hours = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.DAYS.toHours(TimeUnit.SECONDS.toDays(seconds));
				        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds));
				        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));
					
				
				/*	
					int jahre = 0;
					int monate = 0;
					int tage = 0;
					if (d > localDate.getYear()) {
						if (e>=localDate.getMonthValue()) {
							jahre = d - (localDate.getYear() );
							monate = e - localDate.getMonthValue();
							if (f > localDate.getDayOfMonth()) {
								tage = (f - localDate.getDayOfMonth()) - 1;
							} else {
								if(f<localDate.getDayOfMonth()) {
								tage = ((f + 31) - localDate.getDayOfMonth()) - 1;
								} 
							}
						} else {
							monate = (e + 12) - localDate.getMonthValue();
							if (f > localDate.getDayOfMonth()) {
								tage = (f - localDate.getDayOfMonth()) - 1;
							} else {
								if(f<localDate.getDayOfMonth()) {
								tage = ((f + 31) - localDate.getDayOfMonth()) - 1;
								} else {
									tage = ((f + 31) - localDate.getDayOfMonth()) - 1;
								}
							}
						}
					} else {
						if (e > localDate.getMonthValue()) {
							monate = (e) - localDate.getMonthValue();
							if (f > localDate.getDayOfMonth()) {
								tage = (f - localDate.getDayOfMonth()) - 1;
							} else {
								if(f<localDate.getDayOfMonth()) {
								tage = ((f + 31) - localDate.getDayOfMonth()) - 1;
							}}
						} else {
							if (f > localDate.getDayOfMonth()) {
								tage = (f - localDate.getDayOfMonth()) - 1;
							} else {
								if(f<localDate.getDayOfMonth()) {
								tage = ((f + 31) - localDate.getDayOfMonth()) - 1;
							}}
						}
					}
					String jahrez="e";
					String monatez="e";
					String tagez="e";
					if(jahre==1) {
						jahrez="";
					}
					if(monate==1) {
						monatez="";
					}
					if(tage==1) {
						tagez="";
					}*/
					
						if(day!=0) {
							if(hours!=0)  {
								if(minute!=0) {
									p.sendMessage(prefix+" §6Du bist noch §e "+day+" Tage, "+hours+ " Stunden, "+minute+ " Minuten und "+second+" Sekunden §6Vip!");
								} else {
									p.sendMessage(prefix+" §6Du bist noch §e "+day+" Tage, "+hours+ " Stunden und "+second+" Sekunden §6Vip!");

								}
							} else {
								if(minute!=0) {
									p.sendMessage(prefix+" §6Du bist noch §e "+day+" Tage, "+minute+ " Minuten und "+second+" Sekunden §6Vip!");
								} else {
									p.sendMessage(prefix+" §6Du bist noch §e "+day+" Tage und "+second+" Sekunden §6Vip!");

								}
							}
						} else {
							if(hours!=0)  {
								if(minute!=0) {
									p.sendMessage(prefix+" §6Du bist noch §e "+hours+ " Stunden, "+minute+ " Minuten und "+second+" Sekunden §6Vip!");
								} else {
									p.sendMessage(prefix+" §6Du bist noch §e "+hours+ " Stunden und "+second+" Sekunden §6Vip!");

								}
							} else {
								if(minute!=0) {
									p.sendMessage(prefix+" §6Du bist noch §e "+minute+ " Minuten und "+second+" Sekunden §6Vip!");
								} else {
									p.sendMessage(prefix+" §6Du bist noch §e "+second+" Sekunden §6Vip!");

								}
							}
						}
				}

			}
		} else {
			Output.Err(p, "perm");
		}
		

		return false;
	}

	public User loadUser(Player player) {
		// assert that the player is online
		if (!player.isOnline()) {
			throw new IllegalStateException("Player is offline");
		}

		return api.getUserManager().getUser(player.getUniqueId());
	}

}
