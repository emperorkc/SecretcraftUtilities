package de.secretcraft.voteStreaks.commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.secretcraft.main.Main;
import de.secretcraft.main.Output;
import de.secretcraft.main.UtilitiesConfig;

public class MessengerCommand implements CommandExecutor{
	Plugin plugin=Main.getPlugin();
	double bossbarpos=0.05;
	 int i=1;
	 int task=0;
	@Override
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	
		String prefix=UtilitiesConfig.getPrefix();
		Player p=(Player) arg0;
		if(p.hasPermission("secretcraft.messenger.restart")) {
		if(arg3.length>0) {
			switch(arg3[0]) {
			case "restart":
				try {
					addStatus(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					createRestartBar(3,1, "§c§lR");
				}
			}, 3);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRe");
					}
				}, 6);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRes");
					}
				}, 9);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRest");
					}
				}, 12);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lResta");
					}
				}, 15);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestar");
					}
				}, 18);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart");
					}
				}, 21);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart i");
					}
				}, 24);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in");
					}
				}, 27);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3");
					}
				}, 30);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 M");
					}
				}, 33);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Mi");
					}
				}, 36);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Min");
					}
				}, 39);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minu");
					}
				}, 42);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minut");
					}
				}, 45);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minute");
					}
				}, 48);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minuten");
					}
				}, 51);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minuten.");
					}
				}, 54);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minuten..");
					}
				}, 57);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "");
					}
				}, 60);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "§c§lRestart in 3 Minuten...");
					}
				}, 63);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(3,1, "");
					}
				}, 66);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,1, "§c§lRestart in 3 Minuten...");
					}
				}, 69);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.05, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.10, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.15, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*3);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.20, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*4);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.25, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*5);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.30, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*6);Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.35, "§c§lRestart in 3 Minuten...");
					}
				}, 69+180*7);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(160,0.35, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.4, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.45, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160+180);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.5, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160+180*2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.55, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160+180*3);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.6, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160+180*4);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(140,0.65, "§c§lRestart in 2 Minuten...");
					}
				}, 69+180*7+20+160+180*5);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(40,0.65, "§c§lRestart in 1 Minute...");
					}
				}, 69+180*7+20+160+180*5+140);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.7, "§c§lRestart in 1 Minute...");
					}
				}, 69+180*7+20+160+180*5+140+40);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.75, "§c§lRestart in 1 Minute...");
					}
				}, 69+180*7+20+160+180*5+140+40+180);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.8, "§c§lRestart in 1 Minute...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.85, "§c§lRestart in 1 Minute...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3);
			
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(160,0.85, "§c§lRestart in 30 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,0.9, "§c§lRestart in 30 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(60,0.95, "§c§lRestart in 30 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 10 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 9 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 8 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 7 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 6 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 5 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 4 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 3 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 2 Sekunden...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(20,0.95, "§c§lRestart in 1 Sekunde...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20+20+20+20+20);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						createRestartBar(180,1, "§c§lServer wird neugestartet!...");
					}
				}, 69+180*7+20+160+180*5+140+40+180*3+180*2+60+20+20+20+20+20+20+20+20+20+20);
				
				
				break;
			}
		} else {
			p.sendMessage(prefix+" §cKorrekte Nutzung: /messenger add <text> oder /messenger restart");
		}} else {
			Output.Err(p, "perm");
		}
		return false;
	}
	
public BossBar createMessengerBar(int ticks,double pos, String message) {

	
	
	
	BossBar b1 = Bukkit.createBossBar(message, BarColor.GREEN, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY);
	b1.setProgress(pos);
	Collection<? extends Player> m = Bukkit.getOnlinePlayers();
	Iterator<? extends Player> iterator = m.iterator();

	
	while (iterator.hasNext()) {
		Player p = iterator.next();
		b1.addPlayer(p);
		
	}
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			removeMessengerBar(b1);
		}
	}, ticks);
	return b1;
	}
public void removeMessengerBar(BossBar b) {
	b.removeAll();
}
public BossBar createRestartBar(int ticks,double pos, String Message) {

	BossBar b1 = Bukkit.createBossBar(Message, BarColor.RED, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY);
	b1.setProgress(pos);
	Collection<? extends Player> m = Bukkit.getOnlinePlayers();
	Iterator<? extends Player> iterator = m.iterator();

	
	while (iterator.hasNext()) {
		Player p = iterator.next();
		b1.addPlayer(p);
		
	}
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			removeMessengerBar(b1);
		}
	}, ticks);
	return b1;
	}


public static File ConfigFile = new File("plugins/SecretcraftUtilities", "Messenger.yml");
public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

public static void save() throws IOException {
	Config.save(ConfigFile);
}
public void addMessage(String Message, Player p) throws IOException {
	Config.set("Message."+p.getName(), Message);
	Config.set("Amount", Config.getInt("Amount")+1);
	
	save();
}
public List<String> getPlayers() {
	List<String> l1 = new List<>();
	if(Config.getString("Message")!=null) {
	ConfigurationSection s=Config.getConfigurationSection("Message.");
	for (String k : s.getKeys(false)) {
		l1.append(k);
	}
	}
	return l1;
}
public void addStatus(int i) throws IOException {
	Config.set("Status", i);
	save();
}
public int getStatus() {
	return(Config.getInt("Status"));
}

public int getAmount() {
	return (Config.getInt("Amount"));
}
public void start() {
	
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.05,"§4§k-----§r §2Die Befehle findest du unter §6/help §4§k-----"
);
		}
	}, 100);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.1, "§4§k-----§r §2Das Team kannst du mit §6/team §2sehen §4§k-----"
);
		}
	}, 100*2);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.15, "§4§k-----§r §2Wir suchen aktuell weitere Teammitglieder. §c-> /forum §4§k-----"
);
		}
	}, 100*3);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.2, "§4§k-----§r §2Zu den wichtigsten Orten gelangst du mit §6/teleport §4§k-----"
);
		}
	}, 400);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.25, "§4§k-----§r §2Die Städte findest du unter §6/städte §4§k-----"
);
		}
	}, 500);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.3, "§4§k-----§r §2Dein Zuhause kannst du mit §6/sethome §2setzen §4§k-----"
);
		}
	}, 600);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.35, "§4§k-----§r §2Nach Hause kommst du mit §6/home §4§k-----"
);
		}
	}, 700);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.4, "§4§k-----§r §2Dein Kontostand siehst du unter §6/money §4§k-----"
);
		}
	}, 800);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.45, "§4§k-----§r §2Deine Tokens siehst du unter §6/token §4§k-----"
);
		}
	}, 900);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.50, "§4§k-----§r §2Unseren Kanal findest du mit §6/youtube §4§k-----");
		}
	}, 1000);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.55, "§4§k-----§r §2Unseren Discord findest du mit §6/discord §4§k-----"
);
		}
	}, 1100);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.6, "§4§k-----§r §2Unser Forum findest du mit §6/forum §4§k-----"
);
		}
	}, 1200);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.65, "§4§k-----§r §2Zum Adminshop gelangst du mit §6/adminshop §4§k-----"
);
		}
	}, 1300);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.7, "§4§k-----§r §2Spende mit §6/spenden §2um Vorteile zu erhalten §4§k-----"
);
		}
	}, 1400);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.75, "§4§k-----§r §2Vote mit §6/vote §2und erhalte Belohnungen §4§k-----"
);
		}
	}, 1500);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.8, "§4§k-----§r §2Die Votes werden alle §630 Minuten §2ausgewertet! §4§k-----"
);
		}
	}, 1600);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.85, "§4§k-----§r §2Für Information zu einem Grundstück: §6/gs info §4§k-----"
);
		}
	}, 1700);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.9, "§4§k-----§r §2Den Token-Shop siehst du mit §6/token shop§2!§4§k-----"
);
		}
	}, 1800);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,0.95, "§4§k-----§r §2Jeden §6Samstag §2wird eine Stadt versteigert! §4§k-----"
);
		}
	}, 1900);
	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			createMessengerBar(100,1, "§4§k-----§r §2Durch tägliches Voten erhöht sich deine Voteserie! §6/serie §4§k-----"
);
		}
	}, 2000);
	
	
 /* task=	Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		public void run() {
			List<String> l1= getPlayers();
			l1.toFirst();
			Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
				
				public void run() {
					if(l1.hasAccess()) {
					createMessengerBar(200, 0.75+0.25/getAmount(),getMessage(l1.getObject())  );
					l1.next();
					} else {
						Bukkit.getScheduler().cancelTask(task);
						
					}
					
					try {
						save();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}, 0, 200);
		}
		
	}, 1600);*/
  Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	  public void run() {
 
		start();
	  }
  }, 2000);
}
public String getMessage(String name) {
	return (Config.getString("Message."+name));
}
public void removeMessage(String name) throws IOException {
	Config.set("Message."+name, null);
	Config.set("Amount", Config.getInt("Amount")-1);
	save();
}

}
