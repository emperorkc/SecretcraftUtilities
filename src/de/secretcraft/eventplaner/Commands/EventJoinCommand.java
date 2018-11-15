package de.secretcraft.eventplaner.Commands;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.secretcraft.eventplaner.listeners.EventJoinListener;
import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class EventJoinCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) arg0;
		if (p.hasPermission("scu.event")) {
			if (arg3.length > 0) {
				if (arg3[0].equals("del")) {
					if (arg3.length > 1) {
						if (EventJoinListener.Config.get("Events." + arg3[1]) != null) {
							EventJoinListener.Config.set("Events." + arg3[1], null);
							try {
								EventJoinListener.save();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							p.sendMessage(prefix + " §aEventnachricht §c" + arg3[1] + " §a gelöscht!");
						} else {
							p.sendMessage(prefix + " §cEventname nicht gefunden");
						}
					} else {
						p.sendMessage(prefix + " §cKorrekte Nutzung: /eventjoin del <eventname>");
					}
				} else {
					if (arg3.length > 1) {
						if (arg3.length > 2) {
							if (arg3.length > 3) {
								if (arg3[0].charAt(2) == '.' && arg3[0].charAt(5) == '.' && arg3[1].charAt(2) == ':') {
									String sDate1 = arg3[0];
									Date date1 = null;
									try {
										date1 = new SimpleDateFormat("dd.MM.yyyy").parse(sDate1);
									} catch (ParseException e) {
										p.sendMessage(prefix + " §cUngültiges Datum!");
										e.printStackTrace();
									}
									if (date1 != null) {
										int hour = Integer.parseInt(arg3[1].substring(0, 2));
										int minute;
										if (arg3[1].charAt(3) == '0') {
											minute = Integer.parseInt(arg3[1].substring(4));

										} else {
											minute = Integer.parseInt(arg3[1].substring(3, 5));

										}
										String text = "";
										for (int i = 3; i < arg3.length; i++) {
											text = text + arg3[i] + " ";
										}
										String bezeichung = arg3[2];
										try {
											EventJoinListener.setDate(arg3[0], arg3[2]);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										try {
											EventJoinListener.setHour(hour, arg3[2]);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										try {
											EventJoinListener.setMin(minute, arg3[2]);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										try {
											EventJoinListener.setText(text, arg3[2]);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										String b = "";
										if (EventJoinListener.getMin(bezeichung) < 10) {
											b = "0" + EventJoinListener.getMin(bezeichung);
										} else {
											b = Integer.toString(EventJoinListener.getMin(bezeichung));
										}
										p.sendMessage(prefix
												+ " §6Folgende Nachricht wird Spielern beim betreten des Servers angezeigt:");
										p.sendMessage("");
										p.sendMessage(prefix + " §b" + bezeichung + " §2am §3"
												+ EventJoinListener.getDate(bezeichung) + " §2um §3"
												+ EventJoinListener.getHour(bezeichung) + ":" + b + " §2Uhr:");
										p.sendMessage(prefix + "§9 " + EventJoinListener.getText(bezeichung));

									} else {
										p.sendMessage(prefix + " §cUngültiges Datum!");
									}
								}
							} else {
								p.sendMessage(prefix
										+ " §cKorrekte Nutzung: /eventjoin <Datum> <Uhrzeit> <bezeichnung> <Text>  Beispiel: /eventjoin 20.10.2018 19:00 Voteevent VoteEvent! Erhaltet für das Voten 10x so hohe Belohnungen!");

							}
						} else {
							p.sendMessage(prefix
									+ " §cKorrekte Nutzung: /eventjoin <Datum> <Uhrzeit> <bezeichnung> <Text>  Beispiel: /eventjoin 20.10.2018 19:00 Voteevent Voteevent!");

						}
					} else {
						p.sendMessage(prefix
								+ " §cKorrekte Nutzung: /eventjoin <Datum> <Uhrzeit> <bezeichnung> <Text>  Beispiel: /eventjoin 20.10.2018 19:00 Voteevent Voteevent!");

					}
				}
			} else {
				p.sendMessage(prefix
						+ " §cKorrekte Nutzung: /eventjoin <Datum> <Uhrzeit> <bezeichnung> <Text>  Beispiel: /eventjoin 20.10.2018 19:00 Voteevent Voteevent!");
			}
		} else {
			Output.Err(p, "nPerm");
		}

		return false;
	}

}
