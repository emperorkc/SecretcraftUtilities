package de.secretcraft.simpleCommands.commands;



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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitTask;


import de.secretcraft.main.Main;
import de.secretcraft.main.Output;

public class MenuListenerCommands implements Listener {
	
	Plugin plugin = Main.getPlugin();
	public Integer cd = null;
	public Integer cd1 = null;
	private String name;
	private int size;
	private onClick click;
	List<String> viewing = new ArrayList<String>();

	private ItemStack[] items;

	public MenuListenerCommands(String name, int size) {
		this.name = name;
		this.size = size * 9;
		items = new ItemStack[this.size];
		// this.click = click;
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}

	public MenuListenerCommands() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		for (Player p : this.getViewers())
			close(p);
	}

	public MenuListenerCommands open(Player p) {
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

	public MenuListenerCommands close(Player p) {
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
		
		if (viewing.contains(event.getWhoClicked().getName())) {
			event.setCancelled(true);
			Player p = (Player) event.getWhoClicked();
			Row row = getRowFromSlot(event.getSlot());
			ItemStack item = event.getCurrentItem();
			
			ItemMeta meta = item.getItemMeta();
			
			if (event.getInventory().getTitle()
					.equals("§6§lVoten!")) {
					if(meta.getDisplayName().equals("§6§lVote 1")) {
						Output.suc(p, "vote1");VoteCommand.closeInv(p);
						
					}
					if(meta.getDisplayName().equals("§6§lVote 2")) {
						Output.suc(p, "vote2");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§6§lVote 3")) {
						Output.suc(p, "vote3");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§6§lVote 4")) {
						Output.suc(p, "vote4");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§6§lVote 5")) {
						Output.suc(p, "vote5");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§6§lVote 6")) {
						Output.suc(p, "vote6");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§6§lAlle Votes")) {
						Output.suc(p, "vote");VoteCommand.closeInv(p);
					}
					if(meta.getDisplayName().equals("§c§lVerlassen")) {
						VoteCommand.closeInv(p);
					}
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

	public MenuListenerCommands addButton(Row row, int position, ItemStack item) {
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
		public abstract boolean click(Player clicker, MenuListenerCommands menu, Row row, int slot, ItemStack item);
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