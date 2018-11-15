package de.secretcraft.voteStreaks.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import de.secretcraft.main.UtilitiesConfig;
import de.secretcraft.voteStreaks.commands.OpenInventoryCommand;
import de.secretcraft.voteStreaks.commands.VotedPlayers;
import de.secretcraft.voteStreaks.commands.addRewardCommand;
import de.secretcraft.voteStreaks.commands.votedToday;

public class MenuListener implements Listener {

	private String name;
	private int size;
	private onClick click;
	List<String> viewing = new ArrayList<String>();

	private ItemStack[] items;

	public MenuListener(String name, int size) {
		this.name = name;
		this.size = size * 9;
		items = new ItemStack[this.size];
		// this.click = click;
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}

	public MenuListener() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		for (Player p : this.getViewers())
			close(p);
	}

	public MenuListener open(Player p) {
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

	public MenuListener close(Player p) {
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
			ItemStack item = event.getCurrentItem();
			int day = 0;
			for (int i = 1; i <= votedToday.getStreak(p); i++) {
				if (VotedPlayers.getRewardAttach(i) != null) {
					if (VotedPlayers.getRewardAttach(i).equals(item.getItemMeta().getDisplayName())) {

						day = i;
					}
				}
			}

			if ((item.getType() != Material.REDSTONE_BLOCK) && (item.getType() != Material.PAPER)) {
				if (!votedToday.isPickedUp(p, day)) {
					ItemStack itemi = null;
					if (!VotedPlayers.getRewardID(p, day).equals("LEGACY_SKULL_ITEM")) {

						if (!VotedPlayers.getRewardName(p, day).equals("Kleine Belohnung")) {
							itemi = addRewardCommand.getItem(VotedPlayers.getRewardName(p, day),
									VotedPlayers.getRewardID(p, day), day, p, VotedPlayers.getRewardAmount(p, day));
							ItemMeta g = itemi.getItemMeta();
							g.setDisplayName(null);
							g.setLore(null);
							itemi.setItemMeta(g);
						} else {
							String id = VotedPlayers.getRewardID(p, day);
							itemi = new ItemStack(Material.getMaterial(id));
							itemi.setAmount(VotedPlayers.getRewardAmount(p, day));
							ItemMeta isMeta = itemi.getItemMeta();
							de.secretcraft.voteStreaks.commands.List<String> l1 = (VotedPlayers.getEnchants(day));
							de.secretcraft.voteStreaks.commands.List<Integer> l2 = VotedPlayers.getEnchantmentLevel(p,
									day);
							l1.toFirst();
							l2.toFirst();
							while (l1.hasAccess()) {
								String e = l1.getObject();
								int f = l2.getObject();
								l1.next();
								l2.next();

								isMeta.addEnchant(Enchantment.getByName(e), f, true);

							}

							itemi.setItemMeta(isMeta);
						}

					} else {
						itemi = addRewardCommand.giveItemHead(p, p.getName(), day);

					}
					if (!invFull(p)) {
						p.getInventory().addItem(itemi);
						votedToday.pickUp(p, day);
						p.sendMessage(prefix + " §2Du hast deine Belohnung abgeholt.");
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);

					} else {
						p.sendMessage(prefix + " §cDein Inventar ist voll.");
						p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
					}
				} else {
					p.sendMessage(prefix + " §cDu hast diese Belohnung bereits abgeholt.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
			} else if (item.getType() == Material.PAPER) {
				if (!votedToday.isPickedUp(p, day)) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "say hallo");
					votedToday.pickUp(p, day);
				} else {
					p.sendMessage(prefix + " §cDu hast diese Belohnung bereits abgeholt.");
					p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
				}
			} else {
				p.sendMessage(prefix + " §cDu hast diese Belohnung noch nicht freigeschaltet!");
				p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 100, 1);
			}

		}
	}

	public boolean invFull(Player p) {
		return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (viewing.contains(event.getPlayer().getName()))
			viewing.remove(event.getPlayer().getName());
	}

	public MenuListener addButton(Row row, int position, ItemStack item) {
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
		public abstract boolean click(Player clicker, MenuListener menu, Row row, int slot, ItemStack item);
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