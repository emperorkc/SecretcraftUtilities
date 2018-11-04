package de.secretcraft.basiclisteners;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.Acrobot.ChestShop.Events.TransactionEvent;

import net.milkbowl.vault.economy.Economy;

public class BasicListener implements Listener {
	RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
	Economy eco = rsp.getProvider();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onTransaktionSCStaat(TransactionEvent e) {
		Sign s = e.getSign();
		if (s.getLine(0).equals("SC_Staat")) {
			eco.withdrawPlayer(Bukkit.getOfflinePlayer("SC_Staat"), e.getPrice());
		}
	}
}
