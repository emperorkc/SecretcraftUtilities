package de.secretcraft.simpleCommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.secretcraft.main.Main;
import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class ReparaturCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
String prefix=UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if (p.hasPermission("scu.dev")) {
			
			 
            Bukkit.getPluginManager().disablePlugin(Main.getPlugin());
            Bukkit.getPluginManager().enablePlugin(Main.getPlugin());
             p.sendMessage(prefix+ " §3§lSecretcraftUtilities reloaded!");
		} else {
			Output.Err(p, "perm");
		}
		return false;
	}

}
