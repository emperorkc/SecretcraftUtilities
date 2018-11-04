package de.secretcraft.voteStreaks.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.secretcraft.main.Main;
import de.secretcraft.main.UtilitiesConfig;

public class VersteigerungListener implements Listener {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Versteigerung.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		if(Config.getInt("V")!=1) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = e.getPlayer();
	if(e.getClickedBlock()!=null) {
		if (e.getClickedBlock().getType().equals(Material.SIGN)
				|| e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {	

			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				Sign s = (Sign) e.getClickedBlock().getState();
				
				

				if (s.getLine(0).equals("[Versteigerung]")) {
					if(s.getLine(1)!=null) {
						if(p.hasPermission("scu.enteignen")) {
							if(Integer.parseInt(s.getLine(1))!=0) {
								Main.price+=Integer.parseInt(s.getLine(1));
								
							String pr=Integer.toString(Main.price);
							int length=pr.length();
							String pri="";
							if(length==4) {
								int i=0;
								int j=0;
								while(i<5) {
									if(i!=1) {
									pri=pri+pr.charAt(j);
									j++;
									} else {
										pri=pri+".";
										length++;
										
									}
									i++;
								}
							}
							if(length==5) {
								int i=0;int j=0;
								while(i<6) {
									if(i!=2) {
									pri=pri+pr.charAt(j);
									j++;
									} else {
										pri=pri+".";
									}
									i++;
								}
							}
							if(length==6) {
								int i=0;int j=0;
								while(i<7) {
									if(i!=3) {
									pri=pri+pr.charAt(j);
									j++;
									} else {
										pri=pri+".";
									}
									i++;
								}
							}
							if(length==7) {
								int i=0;int j=0;
								
								while(i<9) {
									if(i!=1 && i!=5) {
									pri=pri+pr.charAt(j);
									j++;
									} else {
										
										pri=pri+".";
									}	i++;
									
								}
							}

								
								Collection<Player> z=Bukkit.getWorld(s.getLocation().getWorld().getName()).getNearbyPlayers(s.getLocation(), 50);
							for(Player g: z) {
								g.sendMessage("§7[§4Versteigerung§7] §bDas derzeitige Gebot liegt bei §a"+pri+" SD");
							}
								Config.set("V", 1);	
								try {
									save();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
									@Override
									public void run() {
										Config.set("V", 0);	
										try {
											save();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}, 20);
							}
						}
					}
				}}}}}}
}
