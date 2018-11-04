package de.secretcraft.token.commands;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

import de.secretcraft.token.config.PlayerTokenData;
import de.secretcraft.voteStreaks.commands.List;
import de.secretcraft.voteStreaks.commands.votedToday;
import de.secretcraft.voteStreaks.listeners.MenuListener;
public class TokenCommands implements CommandExecutor {
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if (arg3.length == 0) {
			double token = Math.floor(PlayerTokenData.getToken(p)*100);
			double tokeni=token/100;
			p.sendMessage(prefix + "§6Du hast§e " + tokeni + " §6Tokens.");
		} else {
			
				switch (arg3[0]) {
				case "shop":
					if(p.hasPermission("scu.basics")) {
						
					openInvOrg(p);
					} else {
						Output.Err(p, "perm");
					}
					break;
				
				case "add":
				
					if (p.hasPermission("scu.tokenadmin")) {
						if (arg3.length > 2) {

							if (Bukkit.getOfflinePlayer(arg3[1]) != null) {
								
								try {
									p.sendMessage(prefix+"§3 Tokens hinzugefügt.");
									PlayerTokenData.addToken(Bukkit.getOfflinePlayer(arg3[1]),
											Integer.parseInt(arg3[2]));
								} catch (NumberFormatException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /token add <spieler> <anzahl>");
						}
					} else {
						Output.Err(p, "perm");
					}
					break;
					
				case "remove":
					if (p.hasPermission("scu.tokenadmin")) {
						if (arg3.length > 2) {

							if (Bukkit.getOfflinePlayer(arg3[1]) != null) {
								try {
									p.sendMessage(prefix+"§3 Tokens entfernt.");
									PlayerTokenData.subToken(Bukkit.getOfflinePlayer(arg3[1]),
											Integer.parseInt(arg3[2]));
								} catch (NumberFormatException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								p.sendMessage(prefix + " §cKorrekte Nutzung: /token remove <spieler> <anzahl>");

							}
						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /token remove <spieler> <anzahl>");
						}
					} else {
						Output.Err(p, "perm");
					}
					break;
				case "top":
					List < String> l1=PlayerTokenData.getPlayers();
					l1.toFirst();
					
					
				 List < String> l2= new List<>();
				 List<String >l4=PlayerTokenData.getPlayers();
				 String uuid1="";
					UUID id1= null;
					for(int i=1; i<=10; i++) {
						if(!l1.isEmpty()) {
						double largest=0;
						String largestP=null;
					
						
						l1.toFirst();
						while(l1.hasAccess()) {
							Player u= Bukkit.getPlayer(UUID.fromString(l1.getObject()));
							OfflinePlayer u1=Bukkit.getOfflinePlayer(UUID.fromString(l1.getObject()));
							if(u!=null) {
							if(PlayerTokenData.getToken(u)>largest) {
								largest=PlayerTokenData.getToken(u);
								largestP=l1.getObject();
								
							}
							}  else {
								
								if(PlayerTokenData.getToken(u1)>largest) {
									largest=PlayerTokenData.getToken(u1);
									largestP=l1.getObject();
									
								}
							}
							l1.next();
							
						} 
						l1.toFirst();
						while(l1.hasAccess()) {
							if(l1.getObject().equals(largestP)) {
								l1.remove();
							}
							l1.next();
						}
						
						l2.append(largestP);
					}}
					
					p.sendMessage("§3----§2 Die meisten Token haben: §3----");
					l2.toFirst();
					for(int i=1; i<=20; i++) {
						if(!l2.isEmpty()) {
							Player u= Bukkit.getPlayer(UUID.fromString(l2.getObject()));
							OfflinePlayer u1=Bukkit.getOfflinePlayer(UUID.fromString(l2.getObject()));
							l2.toFirst();
							String player1=null;
							
							
							
						
							if(u!=null) {
						p.sendMessage("         §2"+i+". §3"+u.getName()+": §4"+Math.round(PlayerTokenData.getToken(u))+ "§2 Token");
							} else {
								if(u1.getName()!=null) {
								p.sendMessage("         §2"+i+". §3"+u1.getName()+": §4"+Math.round(PlayerTokenData.getToken(u1))+ "§2 Token");
							}}
						l2.remove();
					}}
					break;
				default:
					p.sendMessage(prefix + " §cKorrekte Nutzung: /token shop/top/remove/add");
					break;
				}
			}
		
		return false;
	}
	
	public static void closeInv(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lToken-Shop     §cToken:§a " + tokeni, 4);
		
		menu.close(p);
		
	}
	public static ItemStack cH(String name, String item) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
	
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner(name);
		sm.setDisplayName("§6§l6 Token");
		sm.setLore(Arrays.asList("§3"+item));
		
		skull.setItemMeta(sm);
		return skull;
	}
	public static ItemStack cHs(String name, String item) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setDisplayName(item);
		sm.setOwner(name);
		
	
		skull.setItemMeta(sm);
		return skull;
	}

	public static void openInvEgg(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lSpawneier     §cToken:§a " + tokeni, 3);
		ItemStack egg1 = new ItemStack(Material.COW_SPAWN_EGG);
		ItemMeta egg1m = egg1.getItemMeta();
		egg1m.setDisplayName("§6§l4 Token");
		egg1m.setLore(Arrays.asList("§7Spawnei Kuh"));
		egg1.setItemMeta(egg1m);
		ItemStack egg2 = new ItemStack(Material.PIG_SPAWN_EGG);
		ItemMeta egg2m = egg2.getItemMeta();
		egg2m.setDisplayName("§6§l4 Token");
		egg2m.setLore(Arrays.asList("§7Spawnei Schwein"));
		egg2.setItemMeta(egg2m);
		ItemStack egg3 = new ItemStack(Material.CHICKEN_SPAWN_EGG);
		ItemMeta egg3m = egg3.getItemMeta();
		egg3m.setDisplayName("§6§l4 Token");
		egg3m.setLore(Arrays.asList("§7Spawnei Huhn"));
		egg3.setItemMeta(egg3m);
		ItemStack egg4 = new ItemStack(Material.SHEEP_SPAWN_EGG);
		ItemMeta egg4m = egg4.getItemMeta();
		egg4m.setDisplayName("§6§l4 Token");
		egg4m.setLore(Arrays.asList("§7Spawnei Schaaf"));
		egg4.setItemMeta(egg4m);
		ItemStack egg5 = new ItemStack(Material.OCELOT_SPAWN_EGG);
		ItemMeta egg5m = egg5.getItemMeta();
		egg5m.setDisplayName("§6§l5 Token");
		egg5m.setLore(Arrays.asList("§7Spawnei Ocelot"));
		egg5.setItemMeta(egg5m);
		ItemStack egg6 = new ItemStack(Material.WOLF_SPAWN_EGG);
		ItemMeta egg6m = egg6.getItemMeta();
		egg6m.setDisplayName("§6§l5 Token");
		egg6m.setLore(Arrays.asList("§7Spawnei Wolf"));
		egg6.setItemMeta(egg6m);
		ItemStack egg7 = new ItemStack(Material.RABBIT_SPAWN_EGG);
		ItemMeta egg7m = egg7.getItemMeta();
		egg7m.setDisplayName("§6§l5 Token");
		egg7m.setLore(Arrays.asList("§7Spawnei Hase"));
		egg7.setItemMeta(egg7m);
		ItemStack egg8 = new ItemStack(Material.HORSE_SPAWN_EGG);
		ItemMeta egg8m = egg8.getItemMeta();
		egg8m.setDisplayName("§6§l5 Token");
		egg8m.setLore(Arrays.asList("§7Spawnei Pferd"));
		egg8.setItemMeta(egg8m);
		ItemStack egg9 = new ItemStack(Material.PARROT_SPAWN_EGG);
		ItemMeta egg9m = egg9.getItemMeta();
		egg9m.setDisplayName("§6§l5 Token");
		egg9m.setLore(Arrays.asList("§7Spawnei Papagei"));
		egg9.setItemMeta(egg9m);
		ItemStack egg10 = new ItemStack(Material.LLAMA_SPAWN_EGG);
		ItemMeta egg10m = egg10.getItemMeta();
		egg10m.setDisplayName("§6§l5 Token");
		egg10m.setLore(Arrays.asList("§7Spawnei Lama"));
		egg10.setItemMeta(egg10m);
		ItemStack egg11 = new ItemStack(Material.DONKEY_SPAWN_EGG);
		ItemMeta egg11m = egg11.getItemMeta();
		egg11m.setDisplayName("§6§l5 Token");
		egg11m.setLore(Arrays.asList("§7Spawnei Esel"));
		egg11.setItemMeta(egg11m);
		ItemStack ep=new ItemStack(Material.ENDER_EYE);
		ItemMeta epm = ep.getItemMeta();
		epm.setDisplayName("§6§l12 Token");
		epm.setLore(Arrays.asList("§6Schlage hiermit eines der", "§6folgenden Tiere um eines","§6einzufangen:","§7Huhn","§7Schaaf","§7Schwein","§7Kuh"));
	epm.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
	epm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		ep.setItemMeta(epm);
		menu.addButton(menu.getRow(0), 0, new ItemStack(egg1));
		menu.addButton(menu.getRow(0), 1, new ItemStack(egg2));
		menu.addButton(menu.getRow(0), 2, new ItemStack(egg3));
		menu.addButton(menu.getRow(0), 3, new ItemStack(egg4));
		menu.addButton(menu.getRow(0), 4, new ItemStack(egg5));
		menu.addButton(menu.getRow(0), 5, new ItemStack(egg6));
		menu.addButton(menu.getRow(0), 6, new ItemStack(egg7));
		menu.addButton(menu.getRow(0), 7, new ItemStack(egg8));
		menu.addButton(menu.getRow(0), 8, new ItemStack(egg9));
		menu.addButton(menu.getRow(1), 0, new ItemStack(egg10));
		menu.addButton(menu.getRow(1), 1, new ItemStack(egg11));
		menu.addButton(menu.getRow(1), 2, new ItemStack(ep));
		ItemStack Barrier=new ItemStack(Material.BARRIER);
		ItemMeta BarrierMeta = Barrier.getItemMeta();
		BarrierMeta.setDisplayName("§4§lZurück");
		Barrier.setItemMeta(BarrierMeta);
		menu.addButton(menu.getRow(2), 4, new ItemStack(Barrier));
		menu.open(p);
		
	}
	public static void openInvHead(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lDeko Seite 1  §cToken:§a " + tokeni, 6 );
				menu.addButton(menu.getRow(1), 1, cH("chest", "Kiste"));
				menu.addButton(menu.getRow(1), 2, cH("redstonekasi", "Grasblock"));
				menu.addButton(menu.getRow(1), 3, cH("Torias_Dax", "Enderkiste"));
				menu.addButton(menu.getRow(1), 4, cH("Inbox", "Briefkasten"));
				menu.addButton(menu.getRow(1), 5, cH("riles67", "Geschenk"));
				menu.addButton(menu.getRow(1), 6, cH("chessie2003", "Kamera"));
				menu.addButton(menu.getRow(1), 7, cH("BeaconBitch", "Beacon"));
				menu.addButton(menu.getRow(2), 1, cH("ThePearlyBum2", "Lautsprecher"));
				menu.addButton(menu.getRow(2), 2, cH("Hack", "Monitor"));
				menu.addButton(menu.getRow(2), 3, cH("Slime", "Schleim"));
				menu.addButton(menu.getRow(2), 4, cH("FuriousDestoryer", "Holzstamm"));
				menu.addButton(menu.getRow(2), 5, cH("Wood", "Holzbrett"));
				menu.addButton(menu.getRow(2), 6, cH("Witherxkill", "Kuchen"));
				menu.addButton(menu.getRow(2), 7, cH("Sponge", "Schwamm"));
				menu.addButton(menu.getRow(3), 1, cH("Toast", "Brot"));
				menu.addButton(menu.getRow(3), 2, cH("Evtema3", "Commandblock"));
				menu.addButton(menu.getRow(3), 3, cH("TurtleEdits", "Schildkröte"));
				menu.addButton(menu.getRow(3), 4, cH("GamingBread", "Küken"));
				menu.addButton(menu.getRow(3), 5, cH("MHF_EGuardian", "Elderguardian"));
				menu.addButton(menu.getRow(3), 6, cH("Davenator911", "Ofen"));
				menu.addButton(menu.getRow(3), 7, cH("xJao", "Laub"));
				menu.addButton(menu.getRow(4), 1, cH("sniperburst", "Burger"));
				menu.addButton(menu.getRow(4), 2, cH("Push_red_button", "Roter Knopf"));
				menu.addButton(menu.getRow(4), 3, cH("Irony", "Endportalrahmen"));
				menu.addButton(menu.getRow(4), 4, cH("fixitphil", "Glowstone"));
				menu.addButton(menu.getRow(4), 5, cH("Bebe2c", "Uhr"));
				menu.addButton(menu.getRow(4), 6, cH("Villager", "Dorfbewohner"));
				menu.addButton(menu.getRow(4), 7, cH("PumpkinSh00t", "Kürbislampe"));
				ItemStack Barrier=new ItemStack(Material.BARRIER);
				ItemMeta BarrierMeta = Barrier.getItemMeta();
				BarrierMeta.setDisplayName("§4§lZurück");
				Barrier.setItemMeta(BarrierMeta);
				ItemStack right= cHs("MHF_ArrowRight","§2§lSeite 2/3" );
				menu.addButton(menu.getRow(5), 
						8, new ItemStack(right));
				menu.addButton(menu.getRow(5), 4, new ItemStack(Barrier));
				menu.addButton(menu.getRow(5), 8, new ItemStack(right));
				menu.open(p);


	}
	public static void openInvHead2(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lDeko Seite 2  §cToken:§a " + tokeni, 6 );
				menu.addButton(menu.getRow(1), 1, cH("Nokman_Ala", "Geschenk"));
			
				
				menu.addButton(menu.getRow(1), 2, cH("sysfailure", "Fernseher"));
				menu.addButton(menu.getRow(1), 3, cH("uioz", "Radio"));

				menu.addButton(menu.getRow(1), 4, cH("Hannah4848", "Geschenk"));
				menu.addButton(menu.getRow(1), 5, cH("CruXXx", "Geschenk"));
				menu.addButton(menu.getRow(1), 6, cH("MHF_Present2", "Geschenk"));
			
				menu.addButton(menu.getRow(1), 7, cH("MHF_Youtube", "Youtube"));
				
				menu.addButton(menu.getRow(2), 1, cH("MHF_Instagram", "Instagram"));
				menu.addButton(menu.getRow(2), 2, cH("Baeumchen", "Fichtenbusch"));
				menu.addButton(menu.getRow(2), 3, cH("Bootleg_", "Kürbislaterne"));
				menu.addButton(menu.getRow(2), 4, cH("Rocket_Ash", "Ananas"));
				
				menu.addButton(menu.getRow(2), 5, cH("theCornyJoke", "Mais"));
				menu.addButton(menu.getRow(2), 6, cH("L_H_2012", "Apfel"));
				menu.addButton(menu.getRow(2), 7, cH("meatmods", "Laub"));
				menu.addButton(menu.getRow(3), 1, cH("TheReapHorn", "Blaubeere"));
				menu.addButton(menu.getRow(3), 2, cH("ChorusPlant", "Chorus-Pflanze"));
				menu.addButton(menu.getRow(3), 3, cH("Thypthon", "Dschungelbusch"));
				menu.addButton(menu.getRow(3), 4, cH("RuthlessTomato", "Tomate"));
				menu.addButton(menu.getRow(3), 5, cH("KylexDavis", "Kirsche"));
				menu.addButton(menu.getRow(3), 6, cH("Sugar_Cane_", "Zuckerrohr"));
				menu.addButton(menu.getRow(3), 7, cH("Squattamelon", "Offene Melone"));
				menu.addButton(menu.getRow(4), 1, cH("dmanrules", "Geschnittene Melone"));
				menu.addButton(menu.getRow(4), 2, cH("Ckathode", "Zitrone"));
				menu.addButton(menu.getRow(4), 3, cH("Pandasaurus_R", "Eggplant"));
				menu.addButton(menu.getRow(4), 4, cH("Kairu", "Kokosnuss"));
				menu.addButton(menu.getRow(4), 5, cH("Abmahnung", "Kaktuspflanze"));
				menu.addButton(menu.getRow(4), 6, cH("MHF_CoconutG", "Grüne Kokosnuss"));
				menu.addButton(menu.getRow(4), 7, cH("MHF_Cactus", "Kaktus"));
				ItemStack Barrier=new ItemStack(Material.BARRIER);
				ItemMeta BarrierMeta = Barrier.getItemMeta();
				BarrierMeta.setDisplayName("§4§lZurück");
				Barrier.setItemMeta(BarrierMeta);
				ItemStack green=new ItemStack(Material.RED_WOOL);
				ItemMeta greenm=green.getItemMeta();
				ItemStack left= cHs("MHF_ArrowLeft","§2§lSeite 1/3" );
				ItemStack right= cHs("MHF_ArrowRight","§2§lSeite 3/3" );
				green.setItemMeta(greenm);
				menu.addButton(menu.getRow(5), 0, new ItemStack(left));
				menu.addButton(menu.getRow(5), 
						8, new ItemStack(right));
				menu.addButton(menu.getRow(5), 4, new ItemStack(Barrier));
				
				menu.open(p);


	}
	public static void openInvHead3(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lDeko Seite 3  §cToken:§a " + tokeni, 6 );
				menu.addButton(menu.getRow(1), 1, cH("MHF_OakLog", "Oak-Log"));
			
				
				menu.addButton(menu.getRow(1), 2, cH("MHF_Melon", "Melone"));
				menu.addButton(menu.getRow(1), 3, cH("MHF_Pumpkin", "Kürbis"));

				menu.addButton(menu.getRow(1), 4, cH("MHF_TNT", "TNT"));
				menu.addButton(menu.getRow(1), 5, cH("MHF_TNT2", "TNT"));
				menu.addButton(menu.getRow(1), 6, cH("MHF_Twitter", "Twitter"));
			
			/*	menu.addButton(menu.getRow(1), 7, cH("75ff6c5d-a38b-4155-b559-70c7f4d8a12f", "Herz"));
				
				menu.addButton(menu.getRow(2), 1, cH("MHF_Instagram", "Instagram"));
				menu.addButton(menu.getRow(2), 2, cH("Baeumchen", "Fichtenbusch"));
				menu.addButton(menu.getRow(2), 3, cH("Bootleg_", "Kürbislaterne"));
				menu.addButton(menu.getRow(2), 4, cH("Rocket_Ash", "Ananas"));
				
				menu.addButton(menu.getRow(2), 5, cH("theCornyJoke", "Mais"));
				menu.addButton(menu.getRow(2), 6, cH("L_H_2012", "Apfel"));
				menu.addButton(menu.getRow(2), 7, cH("meatmods", "Laub"));
				menu.addButton(menu.getRow(3), 1, cH("TheReapHorn", "Blaubeere"));
				menu.addButton(menu.getRow(3), 2, cH("ChorusPlant", "Chorus-Pflanze"));
				menu.addButton(menu.getRow(3), 3, cH("Thypthon", "Dschungelbusch"));
				menu.addButton(menu.getRow(3), 4, cH("RuthlessTomato", "Tomate"));
				menu.addButton(menu.getRow(3), 5, cH("KylexDavis", "Kirsche"));
				menu.addButton(menu.getRow(3), 6, cH("Sugar_Cane_", "Zuckerrohr"));
				menu.addButton(menu.getRow(3), 7, cH("Squattamelon", "Offene Melone"));
				menu.addButton(menu.getRow(4), 1, cH("dmanrules", "Geschnittene Melone"));
				menu.addButton(menu.getRow(4), 2, cH("Ckathode", "Zitrone"));
				menu.addButton(menu.getRow(4), 3, cH("Pandasaurus_R", "Eggplant"));
				menu.addButton(menu.getRow(4), 4, cH("Kairu", "Kokosnuss"));
				menu.addButton(menu.getRow(4), 5, cH("Abmahnung", "Kaktuspflanze"));
				menu.addButton(menu.getRow(4), 6, cH("MHF_CoconutG", "Grüne Kokosnuss"));
				menu.addButton(menu.getRow(4), 7, cH("MHF_Cactus", "Kaktus"));*/
				ItemStack Barrier=new ItemStack(Material.BARRIER);
				ItemMeta BarrierMeta = Barrier.getItemMeta();
				BarrierMeta.setDisplayName("§4§lZurück");
				Barrier.setItemMeta(BarrierMeta);
				ItemStack left= cHs("MHF_ArrowLeft","§2§lSeite 2/3" );
				ItemStack green=new ItemStack(Material.RED_WOOL);
				ItemMeta greenm=green.getItemMeta();
				greenm.setDisplayName("§4§lSeite 2/3");
				green.setItemMeta(greenm);
				menu.addButton(menu.getRow(5), 0, new ItemStack(left));
				menu.addButton(menu.getRow(5), 4, new ItemStack(Barrier));
				
				menu.open(p);


	}
	public static void openInvSpec(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lBesonderes"
				+ "    §cToken:§a " + tokeni, 2);
		ItemStack spawner = new ItemStack(Material.SPAWNER);
		ItemMeta spawnerm = spawner.getItemMeta();
		spawnerm.setDisplayName("§6§l300 Token");
		spawnerm.setLore(Arrays.asList("§7Spawner"));

		spawner.setItemMeta(spawnerm);
		ItemStack rep = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta repm = rep.getItemMeta();
		repm.setDisplayName("§6§l35 Token");
		repm.setLore(Arrays.asList("§7Reparatur Buch"));
	
		rep.setItemMeta(repm);
		menu.addButton(menu.getRow(0), 0, new ItemStack(rep));
		menu.addButton(menu.getRow(0), 1, new ItemStack(spawner));
		ItemStack Barrier=new ItemStack(Material.BARRIER);
		ItemMeta BarrierMeta = Barrier.getItemMeta();
		BarrierMeta.setDisplayName("§4§lZurück");
		Barrier.setItemMeta(BarrierMeta);
		menu.addButton(menu.getRow(1), 4, new ItemStack(Barrier));
		menu.open(p);
	}
	
	public static void openInvPerm(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lTemporäre-Rechte"
				+ " §cToken:§a " + tokeni, 2);
		ItemStack fly = new ItemStack(Material.ELYTRA);
		ItemMeta flym = fly.getItemMeta();
		flym.setDisplayName("§6§l10 Token");
		flym.setLore(Arrays.asList("§7Befehl /fly für 30 Minuten"));
		fly.setItemMeta(flym);
		ItemStack tp= new ItemStack(Material.ENDER_EYE);
		ItemMeta tpm=tp.getItemMeta();
		tpm.setDisplayName("§6§l3 Token");
		tpm.setLore(Arrays.asList("§7Einmalige /tpa - Anfrage || Coming Soon!"));
			tp.setItemMeta(tpm);
		
		menu.addButton(menu.getRow(0), 0, new ItemStack(fly));
		menu.addButton(menu.getRow(0), 1, new ItemStack(tp));
		ItemStack Barrier=new ItemStack(Material.BARRIER);
		ItemMeta BarrierMeta = Barrier.getItemMeta();
		BarrierMeta.setDisplayName("§4§lZurück");
		Barrier.setItemMeta(BarrierMeta);
		menu.addButton(menu.getRow(1), 4, new ItemStack(Barrier));
		menu.open(p);
		
		
	}
	public static void openInvOrg(Player p) {
		double token = Math.floor(PlayerTokenData.getToken(p)*100);
		double tokeni=token/100;
		MenuListenerToken menu = new MenuListenerToken(
				"§3§lToken-Shop     §cToken:§a " + tokeni, 4);
		
		ItemStack egg = new ItemStack(Material.WOLF_SPAWN_EGG);
		ItemMeta eggm = egg.getItemMeta();
		eggm.setDisplayName("§3§lSpawneier");
		eggm.setLore(Arrays.asList("§7Kaufe hier verschiedenste Spawneier!"));
		egg.setItemMeta(eggm);
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta paperm = paper.getItemMeta();
		paperm.setDisplayName("§3§lTemporäre Rechte");
		paperm.setLore(Arrays.asList("§7Erlange temporäre Rechte!"));
		paper.setItemMeta(paperm);
		ItemStack spawn = new ItemStack(Material.SPAWNER);
		ItemMeta spawnm = spawn.getItemMeta();
	
		spawnm.setDisplayName("§3§lBesondere Items");
		spawnm.setLore(Arrays.asList("§7Kaufe hier besondere Items!"));
		spawn.setItemMeta(spawnm);
		
		ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM);
		skull.setDurability((short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner("Chest");
		sm.setDisplayName("§3§lDekoköpfe");
		sm.setLore(Arrays.asList("§7Kaufe hier Dekoköpfe!"));
		
		skull.setItemMeta(sm);
		ItemStack rep = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta repm = rep.getItemMeta();
		repm.setDisplayName("§6§l35 Token");
		repm.setLore(Arrays.asList("§7Reparatur Buch"));
	
		rep.setItemMeta(repm);
		ItemStack door = new ItemStack(Material.BIRCH_TRAPDOOR);
		ItemMeta doorm = door.getItemMeta();
		doorm.setDisplayName("§c§lVerlassen");
		door.setItemMeta(doorm);
		/*for(int k=0; k<4; k++) {
		for(int j=0; j<=8; j++) {
			menu.addButton(menu.getRow(k), j, new ItemStack(i));
		}
		}*/
		menu.addButton(menu.getRow(1), 1, new ItemStack(paper));
		menu.addButton(menu.getRow(1), 3, new ItemStack(egg));
		menu.addButton(menu.getRow(1), 5, new ItemStack(spawn));
		menu.addButton(menu.getRow(1), 7, new ItemStack(skull));
		menu.addButton(menu.getRow(3), 4, new ItemStack(door));
		menu.open(p);
		
	}
}
