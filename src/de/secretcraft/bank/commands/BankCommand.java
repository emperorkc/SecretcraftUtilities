package de.secretcraft.bank.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.secretcraft.bank.config.BankData;
import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;
import net.milkbowl.vault.economy.Economy;

public class BankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {

		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if (p.hasPermission("scu.bank")) {
			RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager()
					.getRegistration(Economy.class);
			Economy eco = rsp.getProvider();
			if (arg3.length > 0) {
				switch (arg3[0]) {
				case "einzahlen":
					if (BankData.isEingerichtet(p)) {
						if (arg3.length > 1) {
							if (Double.parseDouble(arg3[1]) != 0) {

								if (eco.getBalance(p) >= Double.parseDouble(arg3[1])) {
									try {
										BankData.addMoney(p, Double.parseDouble(arg3[1]));
										p.sendMessage(prefix + " §e" + Double.parseDouble(arg3[1])
												+ " §6SD wurden auf dein Konto eingezahlt!");

									} catch (NumberFormatException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									eco.withdrawPlayer(p, Double.parseDouble(arg3[1]));

								} else {
									p.sendMessage(prefix + " §cDu hast nicht genügend Geld!");
								}
							} else {
								p.sendMessage(prefix + " §cKorrekte Nutzung: /konto einzahlen <Betrag>!");
							}

						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /konto einzahlen <Betrag>!");

						}
					} else {
						p.sendMessage(prefix + " §cDu musst dein Konto zuerst einrichten (§4/konto einrichten§c)!");
					}
					break;
				case "auszahlen":
					if (BankData.isEingerichtet(p)) {
						if (arg3.length > 1) {
							if (Double.parseDouble(arg3[1]) != 0) {

								if (BankData.getMoney(p) >= Double.parseDouble(arg3[1])) {
									try {
										BankData.subMoney(p, Double.parseDouble(arg3[1]));
										p.sendMessage(prefix + " §e" + Double.parseDouble(arg3[1])
												+ " §6SD wurden dir von deinem Konto ausgezahlt!");
									} catch (NumberFormatException e) {

										e.printStackTrace();
									} catch (IOException e) {

										e.printStackTrace();

									}
									eco.depositPlayer(p, Double.parseDouble(arg3[1]));
								} else {
									p.sendMessage(
											prefix + " §cDein Konto verfügt nicht über die angegebene Menge an SD!");
								}
							} else {
								p.sendMessage(prefix + " §cKorrekte Nutzung: /konto auszahlen <Betrag>!");

							}
						} else {
							p.sendMessage(prefix + " §cKorrekte Nutzung: /konto auszahlen <Betrag>!");
						}
					} else {
						p.sendMessage(prefix + " §cDu musst dein Konto zuerst einrichten (§4/konto einrichten§c)!");
					}
					break;

				case "kontostand":
					if (BankData.getMoney(p) > 0) {
						p.sendMessage(prefix + " §6Dein Kontostand beträgt §3" + BankData.getMoney(p) + " §6SD");
					} else {
						if (BankData.isEingerichtet(p)) {
							p.sendMessage(prefix + " §6Dein Konto ist leer!");
						} else {
							p.sendMessage(prefix + " §cDu musst dein Konto zuerst einrichten (§4/konto einrichten§c)!");
						}
					}
					break;

				case "einrichten":
					if (BankData.isEingerichtet(p)) {
						p.sendMessage(prefix + " §cDu hast dein Konto bereits eingerichtet!");
					} else {
						try {
							BankData.Einrichten(p);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						p.sendMessage(prefix + " §6Konto erfolgreich eingerichtet");
					}
					break;
				case "deaktivieren":
					if (BankData.isEingerichtet(p)) {
						if (BankData.isDeaktiviert(p)) {
							p.sendMessage(prefix + " §6Dein Konto ist bereits deaktiviert");
						} else {
							try {
								BankData.deaktivieren(p);
								p.sendMessage(prefix + " §6Konto erfolgreich deaktiviert!");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						p.sendMessage(prefix + " §cDu musst dein Konto zuerst einrichten (§4/konto einrichten§c)!");
					}
					break;
				case "aktivieren":
					if (BankData.isEingerichtet(p)) {
						if (!BankData.isDeaktiviert(p)) {
							p.sendMessage(prefix + " §6Dein Konto ist bereits aktiviert");
						} else {
							try {
								BankData.aktivieren(p);
								p.sendMessage(prefix + " §6Konto erfolgreich aktiviert!");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						p.sendMessage(prefix + " §cDu musst dein Konto zuerst einrichten (§4/konto einrichten§c)!");
					}
					break;

				}
			} else {
				p.sendMessage(prefix
						+ " §cKorrekte Nutzung: /konto <einzahlen/auszahlen/kontostand/deaktivieren/aktivieren>");
			}
		} else {
			Output.Err(p, "perm");
		}
		return false;
	}
}
