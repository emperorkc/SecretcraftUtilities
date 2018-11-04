package de.secretcraft.bank.listener;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.Acrobot.ChestShop.Events.PreTransactionEvent;
import com.Acrobot.ChestShop.Events.PreTransactionEvent.TransactionOutcome;
import com.Acrobot.ChestShop.Events.TransactionEvent;
import com.Acrobot.ChestShop.Events.TransactionEvent.TransactionType;

import de.secretcraft.bank.config.BankConfig;
import de.secretcraft.bank.config.BankData;
import de.secretcraft.main.UtilitiesConfig;
import net.milkbowl.vault.economy.Economy;

public class BankListener implements Listener {
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	Economy eco = rsp.getProvider();
	String prefix = UtilitiesConfig.getPrefix();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTransaktion(PreTransactionEvent e) throws IOException {
		OfflinePlayer sb = e.getOwner();
		Player p = e.getClient();
		if (BankData.isEingerichtet(sb)) {
			if (!BankData.isDeaktiviert(sb)) {
				if (!e.getTransactionType().equals(TransactionType.BUY)) {
					
				
					if (BankData.getMoney(sb) < e.getPrice()) {
						
					
					
						
												e.setCancelled(TransactionOutcome.OTHER);
						if(sb.isOnline()) {
							Player ps=(Player) sb;
							ps.sendMessage(prefix+" §6Dein Konto ist leer!");
						}
						p.sendMessage(prefix + " §6Der Käufer hat nicht genügend Geld auf dem Konto!");
					
				}}
			}
		}
	}
	
	/*
@EventHandler
public void onDeath(PlayerDeathEvent e) {
	Player p = e.getEntity();
	double balance = eco.getBalance(p);
	double lost=BankConfig.getLoss()*balance;
		eco.withdrawPlayer(p, lost);
		p.sendMessage(prefix+" §cDu bist gestorben und hast §4"+lost+" §cSD verloren! §6Auf deinem Konto ist dein Geld sicher");
}*/
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTransaktionPre(TransactionEvent e) throws IOException {
		OfflinePlayer sb = e.getOwner();
	
		if (BankData.isEingerichtet(sb)) {
			if (!BankData.isDeaktiviert(sb)) {
				if (e.getTransactionType().equals(TransactionType.BUY)) {
					if(eco.getBalance(sb)>0 ) {
					BankData.addMoney(sb, e.getPrice());
					eco.withdrawPlayer(sb, e.getPrice());
					} else {
						eco.depositPlayer(sb, 1);
					}
				} else {
					if (BankData.getMoney(sb) >= e.getPrice()) {
						BankData.subMoney(sb, e.getPrice());
						eco.depositPlayer(sb, e.getPrice());
					} }
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	if(p.hasPermission("scu.bank")) {	if (BankData.isEingerichtet(p)) {
			if (!BankData.isDeaktiviert(p)) {
				if (BankData.getMoney(p) == 0) {
					p.sendMessage(prefix + " §6Deine Bank verfügt über kein Geld mehr!");
				}
			}
		} else {
			p.sendMessage(prefix + " §6Du hast dir noch kein Bankkonto eingerichtet! /konto einrichten");
		}
	}}

}
