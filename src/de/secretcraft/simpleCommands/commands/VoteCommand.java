package de.secretcraft.simpleCommands.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.secretcraft.main.Output;
import de.secretcraft.token.commands.MenuListenerToken;

public class VoteCommand implements CommandExecutor {
public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	Player p = (Player)arg0;
	if(p.hasPermission("scu.basics")) {
	MenuListenerCommands menu = new MenuListenerCommands(
			"§6§lVoten!", 4);
	
	ItemStack door = new ItemStack(Material.BIRCH_TRAPDOOR);
	ItemMeta doorm = door.getItemMeta();
	doorm.setDisplayName("§c§lVerlassen");
	door.setItemMeta(doorm);
	ItemStack e= new ItemStack(Material.EMERALD_BLOCK);
	ItemMeta em=e.getItemMeta();
	em.setDisplayName("§6§lAlle Votes");
	em.setLore(Arrays.asList("§eJetzt Voten!"));
	e.setItemMeta(em);
	ItemStack e1= new ItemStack(Material.PAPER);
	ItemMeta em1=e1.getItemMeta();
	em1.setDisplayName("§6§lVote 1");
	em1.setLore(Arrays.asList("§eJetzt Voten!"));
	e1.setItemMeta(em1);
	ItemStack e2= new ItemStack(Material.PAPER);
	ItemMeta em2=e2.getItemMeta();
	em2.setDisplayName("§6§lVote 2");
	em2.setLore(Arrays.asList("§eJetzt Voten!"));
	e2.setItemMeta(em2);
	ItemStack e3= new ItemStack(Material.PAPER);
	ItemMeta em3=e3.getItemMeta();
	em3.setDisplayName("§6§lVote 3");
	em3.setLore(Arrays.asList("§eJetzt Voten!"));
	e3.setItemMeta(em3);
	ItemStack e4= new ItemStack(Material.PAPER);
	ItemMeta em4=e4.getItemMeta();
	em4.setDisplayName("§6§lVote 4");
	em4.setLore(Arrays.asList("§eJetzt Voten!"));
	e4.setItemMeta(em4);
	ItemStack e5= new ItemStack(Material.PAPER);
	ItemMeta em5=e5.getItemMeta();
	em5.setDisplayName("§6§lVote 5");
	em5.setLore(Arrays.asList("§eJetzt Voten!"));
	e5.setItemMeta(em5);
	ItemStack e6= new ItemStack(Material.PAPER);
	ItemMeta em6=e6.getItemMeta();
	em6.setDisplayName("§6§lVote 6");
	em6.setLore(Arrays.asList("§eJetzt Voten!"));
	e6.setItemMeta(em6);
	ItemStack rew=new ItemStack(Material.DIAMOND);
	ItemMeta rewm=rew.getItemMeta();
	rewm.setDisplayName("§6§lBelohnungen:");
	rewm.setLore(Arrays.asList("§e100SD!", "§e1 Token --> §c/token shop", "§e1 Votebox-Key --> §c/warp vote", "§ePro Vote eine Voteserie --> §c/serie" ));
	rew.setItemMeta(rewm);

	menu.addButton(menu.getRow(0), 4, rew);
	
	menu.addButton(menu.getRow(1), 1, e1);
	menu.addButton(menu.getRow(1), 2, e2);
	menu.addButton(menu.getRow(1), 3, e3);
	menu.addButton(menu.getRow(1), 4, e);
	menu.addButton(menu.getRow(1), 5, e4);
	menu.addButton(menu.getRow(1), 6, e5);
	menu.addButton(menu.getRow(1), 7, e6);

	menu.addButton(menu.getRow(3), 4, door);
	menu.open(p);
	

	} else {
		Output.Err(p, "perm");
	}
	return false;
		
	}
public static void closeInv(Player p) {
	MenuListenerToken menu = new MenuListenerToken(
			"§6§lVoten!", 4);
	
	menu.close(p);
	
}
}
