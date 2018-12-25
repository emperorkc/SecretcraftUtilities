package de.secretcraft.simpleCommands.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.secretcraft.main.Output;
import de.secretcraft.token.commands.MenuListenerToken;

public class TeamCommand implements CommandExecutor {
public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	Player p=(Player) arg0;
	if(p.hasPermission("scu.basics")) {
		MenuListenerCommands menu = new MenuListenerCommands(
			"§6§lTeam-Mitglieder", 4);
	
	ItemStack door = new ItemStack(Material.BIRCH_TRAPDOOR);
	ItemMeta doorm = door.getItemMeta();
	doorm.setDisplayName("§c§lVerlassen");
	door.setItemMeta(doorm);
	ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull.setDurability((short) 3);
	SkullMeta sm = (SkullMeta) skull.getItemMeta();
	sm.setOwner("Steve");
	sm.setDisplayName("§4§lOwner:");
	sm.setLore(Arrays.asList("§7Serial1990"));
	skull.setItemMeta(sm);
	ItemStack skull2 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull2.setDurability((short) 3);
	SkullMeta sm2 = (SkullMeta) skull2.getItemMeta();
	sm2.setOwner("Steve");
	sm2.setDisplayName("§c§lAdmins:");
	sm2.setLore(Arrays.asList("§7Priester666"));
	skull2.setItemMeta(sm2);
	ItemStack skull3 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull3.setDurability((short) 3);
	SkullMeta sm3 = (SkullMeta) skull3.getItemMeta();
	sm3.setOwner("Steve");
	sm3.setDisplayName("§5§lEntwickler:");
	sm3.setLore(Arrays.asList("§7SotoMoto","§7CrashOverrideNXT"));
	skull3.setItemMeta(sm3);
	ItemStack skull4 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull4.setDurability((short) 3);
	SkullMeta sm4 = (SkullMeta) skull4.getItemMeta();
	sm4.setOwner("Steve");
	sm4.setDisplayName("§1§lEventplaner:");
	sm4.setLore(Arrays.asList("§7ValyrX"));
	skull4.setItemMeta(sm4);
	ItemStack skull5 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull5.setDurability((short) 3);
	SkullMeta sm5 = (SkullMeta) skull5.getItemMeta();
	sm5.setOwner("Steve");
	sm5.setDisplayName("§3§lSupporter:");
	sm5.setLore(Arrays.asList("§cEmperor-KC","§7Krizso95","§7FantasyFlash","§7Loiytzia1","§7einszweidreidein","§7Aetios87","§7Legowaaas","§7Susalicious"));
	skull5.setItemMeta(sm5);
	ItemStack skull6 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull6.setDurability((short) 3);
	SkullMeta sm6 = (SkullMeta) skull6.getItemMeta();
	sm6.setOwner("Steve");
	sm6.setDisplayName("§8§lArchitekt:");
	sm6.setLore(Arrays.asList("§7Sunnij"));
	skull6.setItemMeta(sm6);
	ItemStack skull7 = new ItemStack(Material.LEGACY_SKULL_ITEM);
	skull7.setDurability((short) 3);
	SkullMeta sm7 = (SkullMeta) skull7.getItemMeta();
	sm7.setOwner("Steve");
	sm7.setDisplayName("§4§lOwner:");
	sm7.setLore(Arrays.asList("§7Serial1990"));
	skull7.setItemMeta(sm7);
	
	menu.addButton(menu.getRow(1), 1, skull);
	menu.addButton(menu.getRow(1), 2, skull2);
	menu.addButton(menu.getRow(1), 3, skull3);
	menu.addButton(menu.getRow(1), 4, skull4);
	menu.addButton(menu.getRow(1), 5, skull5);
	menu.addButton(menu.getRow(1), 6, skull6);
	
	menu.addButton(menu.getRow(3), 4, door);
	menu.open(p);

	} else {
		Output.Err(p, "perm");
	}
	
	return false;
	}
public static void closeInv(Player p) {
	MenuListenerToken menu = new MenuListenerToken(
			"§6§lTeam-Mitglieder", 4);
	
	menu.close(p);
	
}
}
