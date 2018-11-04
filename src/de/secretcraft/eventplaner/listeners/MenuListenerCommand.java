package de.secretcraft.eventplaner.listeners;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;


import de.secretcraft.main.Main;

public class MenuListenerCommand implements Listener {
	
	Plugin plugin = Main.getPlugin();
	public Integer cd = null;
	public Integer cd1 = null;
	private String name;
	private int size;
	private onClick click;
	List<String> viewing = new ArrayList<String>();

	private ItemStack[] items;

	public MenuListenerCommand(String name, int size) {
		this.name = name;
		this.size = size * 9;
		items = new ItemStack[this.size];
		// this.click = click;
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}

	public MenuListenerCommand() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		for (Player p : this.getViewers())
			close(p);
	}

	public MenuListenerCommand open(Player p) {
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

	public MenuListenerCommand close(Player p) {
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
			
			Player p = (Player) event.getWhoClicked();
			Row row = getRowFromSlot(event.getSlot());
			ItemStack item = event.getCurrentItem();
			
		
			
			
			
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

	public MenuListenerCommand addButton(Row row, int position, ItemStack item) {
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
		public abstract boolean click(Player clicker, MenuListenerCommand menu, Row row, int slot, ItemStack item);
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