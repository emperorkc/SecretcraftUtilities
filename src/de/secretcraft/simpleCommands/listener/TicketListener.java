package de.secretcraft.simpleCommands.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.simpleCommands.commands.TicketCommand;

public class TicketListener implements Listener {
	 @EventHandler
	 public void onJoin(PlayerJoinEvent e) {
		 String prefix=UtilitiesConfig.getPrefix();
		 Player p=e.getPlayer();
		 if(e.getPlayer().hasPermission("scu.ticket")) {
			 int i=1;
			 boolean t=true;
			 p.sendMessage(prefix+" §cUnbearbeitete Tickets:");
			 while (TicketCommand.Config.get("Ticket." + i) != null) {
					if(TicketCommand.Config.get("Ticket."+i+".gelesen")==null) {
						t=false;
					p.sendMessage(prefix+" §aTicket Nr.§2 "+i+" §avon §c"+TicketCommand.Config.getString("Ticket."+i+".name")+"§a§a. §a/ticket abrufen "+i);
					
					}
					i++;
				}
			 if(t==true) {
				 p.sendMessage(prefix+" §2Keine unbearbeiteten Tickets!");
			 }
		 }
		 
	 }
}
