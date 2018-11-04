package de.secretcraft.voteStreaks.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.secretcraft.main.UtilitiesConfig;

public class VanishListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities/Vanished", "Vanish.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	public static List<String> getVanished() {
		List<String> l1 = new List<>();
		ConfigurationSection s=Config.getConfigurationSection("");
		
		for (String k : s.getKeys(false)) {
			if(Config.getInt(k)!=0) {
			l1.append(k);
		}}
		
		
		return l1;
	}
	
@EventHandler
public void vEvent(PlayerCommandPreprocessEvent e) throws IOException {
	String prefix= UtilitiesConfig.getPrefix();
	if(e.getMessage().toLowerCase().equals("/vanish")) {
		Collection<? extends Player> s= Bukkit.getOnlinePlayers();
		Iterator is=s.iterator();
		while(is.hasNext()) {
			Player p = (Player) is.next();
			if(p.hasPermission("scu.vanish")) {
				if(Config.getInt(p.getName())==0) {
			//	p.sendMessage(prefix+e.getPlayer().getName()+" §c§lIst nun im Vanish!"	);
				Config.set(p.getName(), 1);
				} else {
		//			p.sendMessage(prefix+e.getPlayer().getName()+" §c§lIst nun nicht mehr im Vanish!"	);
					Config.set(p.getName(), null);
				}
			save();
			}
		}
	}
	if(e.getMessage().toLowerCase().equals("/op")) {
		Collection<? extends Player> s= Bukkit.getOnlinePlayers();
		Iterator is=s.iterator();
		while(is.hasNext()) {
			Player p = (Player) is.next();
			if(p.hasPermission("scu.vanish")) {
		//		p.sendMessage(prefix+e.getPlayer().getName()+" §4§lHat versucht sich Op zu geben!"	);
				
			}
		}
	}
	
}
}
