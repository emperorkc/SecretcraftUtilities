package de.secretcraft.eventplaner.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class JMCommand implements CommandExecutor {
	public static File ConfigFile = new File("plugins/SecretcraftUtilities", "JM");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	
	public static void save() throws IOException {
		Config.save(ConfigFile);
	}
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		if(p.hasPermission("scu.basics")) {
			if(Config.getInt("Spawn."+p.getName())==0) {
				Config.set("Spawn."+p.getName(), 1);
				try {
					save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p.sendMessage("§cJoin-Message ausgeschaltet");
			} else {
				Config.set("Spawn."+p.getName(), 0);
				try {
					save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				p.sendMessage("§cJoin-Message eingeschaltet");
			}
		}
		return false;
	}

}
