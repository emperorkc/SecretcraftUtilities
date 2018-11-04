package de.secretcraft.token.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitTask;

import de.secretcraft.main.Main;
import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.simpleCommands.commands.TeamCommand;
import de.secretcraft.token.config.PlayerTokenData;
import de.secretcraft.voteStreaks.commands.OpenInventoryCommand;
import de.secretcraft.voteStreaks.commands.VotedPlayers;
import de.secretcraft.voteStreaks.commands.addRewardCommand;
import de.secretcraft.voteStreaks.commands.votedToday;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.Node;
import me.lucko.luckperms.api.User;
import me.lucko.luckperms.api.event.LuckPermsEvent;
import me.lucko.luckperms.api.event.track.TrackCreateEvent;
import me.lucko.luckperms.api.event.user.track.UserPromoteEvent;
import me.lucko.luckperms.api.event.user.track.UserTrackEvent;

public class MenuListenerToken implements Listener {
	Plugin plugin = Main.getPlugin();
	public Integer cd = null;
	public Integer cd1 = null;
	private String name;
	private int size;
	private onClick click;
	List<String> viewing = new ArrayList<String>();

	private ItemStack[] items;

	public MenuListenerToken(String name, int size) {
		this.name = name;
		this.size = size * 9;
		items = new ItemStack[this.size];
		// this.click = click;
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}

	public MenuListenerToken() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		for (Player p : this.getViewers())
			close(p);
	}

	public MenuListenerToken open(Player p) {
		p.openInventory(getInventory(p));
		viewing.add(p.getName());
		return this;
	}

	private Inventory getInventory(Player p) {
		Inventory inv = Bukkit.createInventory(p, size, name);
		for (int i = 0; i < items.length; i++)
			if (items[i] != null)
				inv.setItem(i, items[i]);
		return inv;
	}

	public MenuListenerToken close(Player p) {
		if (p.getOpenInventory().getTitle().equals(name))
			p.closeInventory();
		return this;
	}

	public List<Player> getViewers() {
		List<Player> viewers = new ArrayList<Player>();
		for (String s : viewing)
			viewers.add(Bukkit.getPlayer(s));
		return viewers;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) throws IOException {
		String prefix = UtilitiesConfig.getPrefix();
		if (viewing.contains(event.getWhoClicked().getName())) {
			event.setCancelled(true);
			Player p = (Player) event.getWhoClicked();
			Row row = getRowFromSlot(event.getSlot());
			if(event.getCurrentItem().getAmount()!=0) {
			ItemStack item = event.getCurrentItem();
			int day = 0;
			ItemMeta meta = item.getItemMeta();

			
			if (meta.getDisplayName().equals("§3§lSpawneier")) {
				TokenCommands.openInvEgg(p);
			}
			if (meta.getDisplayName().equals("§3§lTemporäre Rechte")) {
				TokenCommands.openInvPerm(p);
			}
			if (meta.getDisplayName().equals("§3§lBesondere Items")) {
				TokenCommands.openInvSpec(p);
			}
			if (meta.getDisplayName().equals("§3§lDekoköpfe")) {
				p.sendMessage(prefix+" §cDekoköpfe sind deaktiviert!");
			}
		
			if ((meta.getDisplayName().equals("§6§l4 Token"))) {
				if (PlayerTokenData.getToken(p) > 4) {
					if (!invFull(p)) {
					

						meta.setDisplayName("");
					meta.setLore(Arrays.asList(""));
						item.setItemMeta(meta);
						p.getInventory().addItem(new ItemStack(item));

						PlayerTokenData.subToken(p, 4);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
				TokenCommands.openInvEgg(p);
			}
			if ((meta.getDisplayName().equals("§6§l5 Token"))) {
				if (PlayerTokenData.getToken(p) > 5) {
					if (!invFull(p)) {
						ItemStack item1 = new ItemStack(Material.HORSE_SPAWN_EGG);

						meta.setDisplayName("");
						meta.setLore(item1.getItemMeta().getLore());
						item.setItemMeta(meta);
						p.getInventory().addItem(new ItemStack(item));

						PlayerTokenData.subToken(p, 5);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
				TokenCommands.openInvEgg(p);
			}
			if ((meta.getDisplayName().equals("§6§l10 Token"))) {
				
				if (PlayerTokenData.getToken(p) >= 10) {
					
					if (!p.hasPermission("cmi.command.fly")) {

						PlayerTokenData.subToken(p, 10);
						p.sendMessage(prefix + " §6/fly-Rechte §eerfolgreich gekauft!");
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"lp user " + p.getName() + " permission settemp cmi.command.fly true 30min");
						cd1 = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {

								p.sendMessage(
										prefix + " §cAchtung, in 15 Sekunden wirst du nicht mehr fliegen können!");

							}
						}, 36200);
						cd = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							public void run() {

								p.setFlying(false);
								p.setAllowFlight(false);

							}
						}, 36500);

						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDu hast diese Rechte bereits!");
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
				TokenCommands.openInvPerm(p);

			}
			if ((meta.getDisplayName().equals("§6§l300 Token"))) {
				if (PlayerTokenData.getToken(p) > 300) {
					if (!invFull(p)) {
						p.getInventory().addItem(new ItemStack(Material.getMaterial(item.getType().name())));

						PlayerTokenData.subToken(p, 300);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
				TokenCommands.openInvSpec(p);

			}
			if ((meta.getDisplayName().equals("§6§l35 Token"))) {
				if (PlayerTokenData.getToken(p) > 35) {
					if (!invFull(p)) {
						ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

						EnchantmentStorageMeta esm = (EnchantmentStorageMeta) book.getItemMeta();
						esm.addStoredEnchant(Enchantment.MENDING, 1, true);
						book.setItemMeta(esm);
						p.getInventory().addItem(book);
						PlayerTokenData.subToken(p, 35);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);

				}
				TokenCommands.openInvSpec(p);

			}
			if (item.getType().equals(Material.BARRIER)) {
				TokenCommands.openInvOrg(p);
			}
			if(meta.getDisplayName().equals("§2§lSeite 1/3")) {
				TokenCommands.openInvHead(p);
			}
			if(meta.getDisplayName().equals("§2§lSeite 2/3")) {
				TokenCommands.openInvHead2(p);
			}
			if(meta.getDisplayName().equals("§2§lSeite 3/3")) {
				TokenCommands.openInvHead3(p);
			}
		if ((meta.getDisplayName().equals("§6§l12 Token"))) {
				if (PlayerTokenData.getToken(p) >= 12) {
					if (!invFull(p)) {
						ItemStack ep=new ItemStack(Material.ENDER_EYE);
						ItemMeta epm=ep.getItemMeta();
						epm.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
						epm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						epm.setDisplayName("§5Tier-Fänger");
						epm.setLore(Arrays.asList("§6Fange hiermit ein Tier ein,","§6indem du es mit diesem Item in der Hand","§6schlägst!","§3Das Tier behält seine Level!"));
					ep.setItemMeta(epm);
					p.getInventory().addItem(ep);
					PlayerTokenData.subToken(p, 12);
					p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
					}
					else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
					}else {
						p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
			}
			if ((meta.getDisplayName().equals("§6§l6 Token"))) {
				if (PlayerTokenData.getToken(p) >= 6) {
					if (!invFull(p)) {
						meta.setDisplayName("§7Dekoration");
						meta.setLore(Arrays.asList(""));
						item.setItemMeta(meta);
						p.getInventory().addItem(new ItemStack(item));

						PlayerTokenData.subToken(p, 6);
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDeine Token reichen nicht aus.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
				double token = Math.floor(PlayerTokenData.getToken(p)*100);
				double tokeni=token/100+6;
				
				if(event.getInventory().getTitle().equals("§3§lDeko Seite 1  §cToken:§a " + tokeni)) {
				TokenCommands.openInvHead(p);
				} else if(event.getInventory().getTitle().equals("§3§lDeko Seite 2  §cToken:§a " + tokeni)) {
					TokenCommands.openInvHead2(p);
					
				} else if(event.getInventory().getTitle().equals("§3§lDeko Seite 3  §cToken:§a " + tokeni)) {
					TokenCommands.openInvHead3(p);
				}
			}

			if ((meta.getDisplayName().equals("§c§lVerlassen"))) {
				p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
				TokenCommands.closeInv(p);
			}
		}}
	}

	public boolean invFull(Player p) {
		return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (viewing.contains(event.getPlayer().getName()))
			viewing.remove(event.getPlayer().getName());
	}

	public MenuListenerToken addButton(Row row, int position, ItemStack item) {
		items[row.getRow() * 9 + position] = getItem(item);
		return this;
	}

	public Row getRowFromSlot(int slot) {
		return new Row(slot / 9, items);
	}

	public Row getRow(int row) {
		return new Row(row, items);
	}

	public interface onClick {
		public abstract boolean click(Player clicker, MenuListenerToken menu, Row row, int slot, ItemStack item);
	}

	public class Row {
		private ItemStack[] rowItems = new ItemStack[9];
		int row;

		public Row(int row, ItemStack[] items) {
			this.row = row;
			int j = 0;
			for (int i = (row * 9); i < (row * 9) + 9; i++) {
				rowItems[j] = items[i];
				j++;
			}
		}

		public ItemStack[] getRowItems() {
			return rowItems;
		}

		public ItemStack getRowItem(int item) {
			return rowItems[item] == null ? new ItemStack(Material.AIR) : rowItems[item];
		}

		public int getRow() {
			return row;
		}
	}

	private ItemStack getItem(ItemStack item) {
		ItemMeta im = item.getItemMeta();

		item.setItemMeta(im);
		return item;
	}

}