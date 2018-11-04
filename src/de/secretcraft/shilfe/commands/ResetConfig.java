package de.secretcraft.shilfe.commands;

import java.io.IOException;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class ResetConfig implements Listener {
	
	
	@EventHandler
	public void onLeave(PlayerQuitEvent event) throws IOException {
		Player p= event.getPlayer();
		if(p.hasPermission("scu.gsdef")) {
		
			Config.addP(p);
		}
	}
	
}
