package de.secretcraft.animals.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.secretcraft.animals.listener.MobLevelListener;
import de.secretcraft.main.UtilitiesConfig;

public class TiereNewCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix=UtilitiesConfig.getPrefix();
		Player p= (Player) arg0;
		if (arg3.length > 0) {
			switch (arg3[0]) {
			case "top":
				File ConfigFile2 = new File("plugins/Tiere/Liste", "Topliste");
				FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
				if (Config2.getString("Liste.1") != null) {
					for(int i=1; i<11; i++) {
						if(Config2.getString("Liste."+i)!=null) {
						p.sendMessage(Config2.getString("Liste."+i));
						}
					}
				} else {
					p.sendMessage(prefix+ "§cDerzeit ist keine Topliste vorhanden.");
				}
				break;
			default:
				if(p.hasPermission("scu.tiere")) {
				String name=arg3[0];
				OfflinePlayer p1=Bukkit.getPlayer(name);
				if(p1!=null ) {
				ArrayList<String> l=MobLevelListener.getEntities(p1);
				if(l.isEmpty()) {
					p.sendMessage(prefix+" §cIn dieser Welt hast du keine Tiere! Schau doch mal in der Stadtwelt nach.");
				} else {
				for(String i:l) {
					Entity e=Bukkit.getWorld(p.getWorld().getName()).getEntity(UUID.fromString(i));
					String art="";
					switch(e.getType()) {
					case COW:
						art="§7Kuh";
						break;
					case CHICKEN:
						art="§eHuhn";
						break;
					case TURTLE:
						art="§2Schildkröte";
						break;
					case SHEEP:
						art="§fSchaaf";
						break;
					case PIG:
						art="§dSchwein";
						break;
					}
					p.sendMessage("§7[§a"+p1.getName()+"§7] §2Tier: §3"+art+" §2, Level: §3"+MobLevelListener.getLevel(e));
				
				}}}} else {
					p.sendMessage(prefix+" §cDu hast hierzu keine Rechte!");
				}
				break;
			}
		} else {
			if(p.hasPermission("scu.tiere")) {
			ArrayList<String> l=MobLevelListener.getEntities(p);
			if(l.isEmpty()) {
				p.sendMessage(prefix+" §cIn dieser Welt hast du keine Tiere! Schau doch mal in der Stadtwelt nach.");
			} else {
			for(String i:l) {
				Entity e=Bukkit.getWorld(p.getWorld().getName()).getEntity(UUID.fromString(i));
				Location li=e.getLocation();
				String art="";
				switch(e.getType()) {
				case COW:
					art="§7Kuh";
					break;
				case CHICKEN:
					art="§eHuhn";
					break;
				case TURTLE:
					art="§2Schildkröte";
					break;
				case SHEEP:
					art="§fSchaaf";
					break;
				case PIG:
					art="§dSchwein";
					break;
				}
				p.sendMessage("§7[§a"+p.getName()+"§7] §2Tier: §3"+art+" §2, Level: §3"+MobLevelListener.getLevel(e)+ "§2, Koordinaten: §3X: "+li.getBlockX()+"§2, §3Y:"+li.getBlockY()+"§2, §3Z:"+li.getBlockZ()+"§2, Welt: §3"+e.getWorld().getName());
			
			}
		}}}
		return false;
	}

}
