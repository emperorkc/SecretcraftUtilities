package de.secretcraft.voteStreaks.commands;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import de.secretcraft.main.UtilitiesConfig;

public class addRewardCommand implements CommandExecutor {
	

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = UtilitiesConfig.getPrefix();
		Player p = (Player) sender;

		if (p.hasPermission("scu.addreward")) {
			if (args.length != 0) {
				switch (args[0]) {
				case "item":
					if (args.length>1) {
						if (p.getItemInHand() != null) {
							try {

								if (p.getInventory().getItemInHand().getItemMeta().getDisplayName() == null) {
									VotedPlayers.addReward(p,
											p.getInventory().getItemInHand().getType().name(),
											p.getInventory().getItemInHand().getEnchantments(),
											p.getInventory().getItemInHand().getAmount(),

											"Kleine Belohnung", Integer.parseInt(args[1]));
								} else {
									VotedPlayers.addReward(p,
											p.getInventory().getItemInHand().getType().name(),
											p.getInventory().getItemInHand().getEnchantments(),
											p.getInventory().getItemInHand().getAmount(),

											p.getInventory().getItemInHand().getItemMeta().getDisplayName(),
											Integer.parseInt(args[1]));
								}
								p.sendMessage(prefix+" §2Gegenstand erfolgreich hinzugefügt.");
							} catch (NumberFormatException e) {
							
								e.printStackTrace();
							} catch (IOException e) {
							
								e.printStackTrace();
							}
						} else {
							p.sendMessage(prefix
									+ "§c Halte das Item mit der jeweiligen Anzahl in deiner Hand und gebe den Befehl erneut ein.");
						}
					} else {
						p.sendMessage(prefix + "§c Korrekte Nutzung: /addreward item <Tag>.");
					}
					break;
				case "head":
					if (args[1] != null) {
						try {
							VotedPlayers.addHead(p, Integer.parseInt(args[1]));
						} catch (NumberFormatException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						p.sendMessage(prefix + "§c Korrekte Nutzung: /addreward head <Tag>.");
					}
					break;
				case "permission":
					try {
						VotedPlayers.addPermission(p, Integer.parseInt(args[1]), args[2]);
					} catch (NumberFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				
				default:
					p.sendMessage(prefix + "§c Korrekte Nutzung: /addreward item/head <Tag>.");

					break;
				}
			} else {
				p.sendMessage(prefix + "§c Korrekte Nutzung: /addreward item/head <Tag>.");
			}
		} else {
			p.sendMessage(prefix + "§c Du hast hierzu keine Rechte.");
		}
		return false;
	}

	public static ItemStack getItem(String name, String ItemID, int Day, Player p, int amount) {
		
		Material m=Material.getMaterial(ItemID);
		ItemStack is =new ItemStack(m);
		ItemMeta isMeta = is.getItemMeta();
		isMeta.setDisplayName(VotedPlayers.getRewardAttach( Day));
		isMeta.setLore(Arrays.asList(name));
		List<String> l1 = (VotedPlayers.getEnchants( Day));
		List<Integer> l2 = VotedPlayers.getEnchantmentLevel(p, Day);
		l1.toFirst();
		l2.toFirst();
		while (l1.hasAccess()) {
			String e = l1.getObject();
		
			int f = l2.getObject();
			l1.next();
			l2.next();
			isMeta.addEnchant(Enchantment.getByName(e), f, true);

		}

		is.setItemMeta(isMeta);
		is.setAmount(amount);
		return is;
	}

	public static ItemStack giveItemHead(Player p, String name, int Day) {
		ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
		skull.setDurability((short) 3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner(name);
		sm.setDisplayName("§2Kopf von §4" + name);
		/*
		 * int j = (int) (Math.random() * 5 + 1); int random = 0; int random1 = 0; for
		 * (int i = 0; i <= j; i++) { random = (int) (Math.random() * 30 + 1); random1 =
		 * (int) (Math.random() * 9 + 1); if (i > 0 ) { if
		 * (!sm.hasEnchant(Enchantment.getById(random))) {
		 * sm.addEnchant(Enchantment.getById(random), random1, true); } else { j+=1; } }
		 * else { sm.addEnchant(Enchantment.getById(random), random1, true); } }
		 */

		skull.setItemMeta(sm);
		return skull;
	}

	public static int getRandomInt(int max) {
		return (int) Math.floor(Math.random() * Math.floor(max));
	}
}
