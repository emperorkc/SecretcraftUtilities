package de.secretcraft.animals.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
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

public class MenuListenerAnimal implements Listener {
	Plugin plugin = Main.getPlugin();
	public Integer cd = null;
	public Integer cd1 = null;
	private String name;
	private int size;
	private onClick click;
	List<String> viewing = new ArrayList<String>();

	private ItemStack[] items;

	public MenuListenerAnimal(String name, int size) {
		this.name = name;
		this.size = size * 9;
		items = new ItemStack[this.size];
		// this.click = click;
		Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugins()[0]);
	}

	public MenuListenerAnimal() {
		// TODO Auto-generated constructor stub
	}

	@EventHandler
	public void onPluginDisable(PluginDisableEvent event) {
		for (Player p : this.getViewers())
			close(p);
	}

	public MenuListenerAnimal open(Player p) {
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

	public MenuListenerAnimal close(Player p) {
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
			if (event.getCurrentItem().getAmount() != 0) {
				ItemStack item = event.getCurrentItem();
				int day = 0;
				ItemMeta meta = item.getItemMeta();
				File ConfigFile = new File("plugins/Tiere", "lastclicked");
				FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
				String uuid = Config.getString("p." + p.getName());

				Entity e = Bukkit.getEntity(UUID.fromString(uuid));
				if (item.getType().equals(Material.EXPERIENCE_BOTTLE)) {
					int amount = 0;
					for (int i = 0; i < 36; i++) {
						if (p.getInventory().getItem(i) != null) {
							if (p.getInventory().getItem(i).getType().equals(Material.EXPERIENCE_BOTTLE)) {
								amount = amount + (p.getInventory().getItem(i).getAmount());
								p.getInventory().getItem(i).setAmount(0);
							}
						}
					}
					if (amount > 0) {
						while (amount > (MobLevelListener.getReq(e) - MobLevelListener.getXp(e))) {
							amount = amount - (MobLevelListener.getReq(e) - MobLevelListener.getXp(e));

							MobLevelListener.addLevel(e);

						}
						MobLevelListener.setXp(e, amount + MobLevelListener.getXp(e));
						MobLevelListener.setBar(e);
						MobLevelListener.openInv(p, e);
					} else {
						p.sendMessage(prefix + " §cDu hast keine Xp-Flaschen mehr!");
					}
				}
				if (item.getType().equals(Material.ENDER_EYE)) {
					ItemStack s = MobLevelListener.getEgg(e);

					ItemMeta sm = s.getItemMeta();
					sm.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
					sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					sm.setLore(Arrays.asList("§6Tier: " + e.getType().toString(),
							"§2Level: §6" + MobLevelListener.getLevel(e)));

					s.setItemMeta(sm);
					int amount = 0;
					for (int i = 0; i < 36; i++) {
						if (p.getInventory().getItem(i) != null) {
							if (p.getInventory().getItem(i).getType().equals(Material.EMERALD)) {
								amount = amount + p.getInventory().getItem(i).getAmount();
							}
						}
					}
					int ss = (MobLevelListener.getLevel(e) * 2);
					if (amount >= ss) {
						if (!invFull(p)) {

							for (int i = 0; i < 36; i++) {
								if (p.getInventory().getItem(i) != null) {
									if (p.getInventory().getItem(i).getType().equals(Material.EMERALD)) {
										if (ss > 0) {
											if (ss > p.getInventory().getItem(i).getAmount()) {
												ss -= p.getInventory().getItem(i).getAmount();

												p.getInventory().getItem(i).setAmount(0);
											} else {
												p.getInventory().getItem(i)
														.setAmount(p.getInventory().getItem(i).getAmount() - ss);
												ss = 0;
											}
										}
									}
								}
							}
							e.remove();
							p.getInventory().addItem(s);
							MobLevelListener.removeEntity(e);
							p.sendMessage(prefix + " §6Tier erfolgreich eingefangen!");
							MobLevelListener.close(p, e);
						} else {
							p.getItemInHand().setAmount(p.getItemInHand().getAmount() + 1);
							p.sendMessage(prefix + " §cDir fehlt der Platz im Inventar um das Tier zu fangen!");
						}
					} else {
						p.sendMessage(prefix + " §cDir fehlen §a" + ((MobLevelListener.getLevel(e) * 2) - amount)
								+ " §cEmeralds!");

					}
				}
				if (e instanceof Sheep) {
					if (meta.getDisplayName().equals("§3Farbe ändern")) {
						openInvC(e, p);
					}

				}
				if (item.getType().equals(Material.BARRIER)) {
					MobLevelListener.close(p, e);
				}
				if (event.getInventory().getTitle().equals("§1F§2a§3r§4b§5e§6n§7-§8A§au§bs§cw§da§eh§fl")) {
					if (e instanceof Sheep) {
						Sheep s = (Sheep) e;

						switch (item.getType()) {
						case WHITE_WOOL:
							s.setColor(DyeColor.WHITE);
							break;
						case BLACK_WOOL:
							s.setColor(DyeColor.BLACK);
							break;
						case LIME_WOOL:
							s.setColor(DyeColor.LIME);

							break;
						case MAGENTA_WOOL:
							s.setColor(DyeColor.MAGENTA);

							break;
						case BROWN_WOOL:
							s.setColor(DyeColor.BROWN);

							break;
						case YELLOW_WOOL:
							s.setColor(DyeColor.YELLOW);
							break;
						case RED_WOOL:
							s.setColor(DyeColor.RED);
							break;
						case PURPLE_WOOL:
							s.setColor(DyeColor.PURPLE);
							break;
						case PINK_WOOL:
							s.setColor(DyeColor.PINK);
							break;
						case GREEN_WOOL:
							s.setColor(DyeColor.GREEN);
							break;
						case CYAN_WOOL:
							s.setColor(DyeColor.CYAN);
							break;
						case LIGHT_BLUE_WOOL:
							s.setColor(DyeColor.LIGHT_BLUE);
							break;
						case LIGHT_GRAY_WOOL:
							s.setColor(DyeColor.LIGHT_GRAY);
							break;
						case GRAY_WOOL:
							s.setColor(DyeColor.GRAY);
							break;
						case ORANGE_WOOL:
							s.setColor(DyeColor.ORANGE);
							break;
						case BLUE_WOOL:
							s.setColor(DyeColor.BLUE);
							break;
						}

					}
				}
			}
		}
	}

	public void openInvC(Entity e, Player p) {
		MenuListenerAnimal m = new MenuListenerAnimal("§1F§2a§3r§4b§5e§6n§7-§8A§au§bs§cw§da§eh§fl", 3);
		ItemStack o = new ItemStack(Material.WHITE_WOOL);
		ItemMeta mi = o.getItemMeta();
		mi.setDisplayName("§fWeiß");
		o.setItemMeta(mi);
		ItemStack o1 = new ItemStack(Material.BLACK_WOOL);
		ItemMeta mi1 = o1.getItemMeta();
		mi1.setDisplayName("§8Schwarz");
		o1.setItemMeta(mi1);
		ItemStack o2 = new ItemStack(Material.BLUE_WOOL);
		ItemMeta mi2 = o2.getItemMeta();
		mi2.setDisplayName("§1Blau");
		o2.setItemMeta(mi2);
		ItemStack o3 = new ItemStack(Material.BROWN_WOOL);
		ItemMeta mi3 = o3.getItemMeta();
		mi3.setDisplayName("§cBraun");
		o3.setItemMeta(mi3);
		ItemStack o4 = new ItemStack(Material.CYAN_WOOL);
		ItemMeta mi4 = o4.getItemMeta();
		mi4.setDisplayName("§9Cyan");
		o4.setItemMeta(mi4);
		ItemStack o5 = new ItemStack(Material.GRAY_WOOL);
		ItemMeta mi5 = o5.getItemMeta();
		mi5.setDisplayName("§7Grau");
		o5.setItemMeta(mi5);
		ItemStack o6 = new ItemStack(Material.GREEN_WOOL);
		ItemMeta mi6 = o6.getItemMeta();
		mi6.setDisplayName("§2Grün");
		o6.setItemMeta(mi6);
		ItemStack o7 = new ItemStack(Material.LIGHT_BLUE_WOOL);
		ItemMeta mi7 = o7.getItemMeta();
		mi7.setDisplayName("§bHell-Blau");
		o7.setItemMeta(mi7);
		ItemStack o8 = new ItemStack(Material.LIGHT_GRAY_WOOL);
		ItemMeta mi8 = o8.getItemMeta();
		mi8.setDisplayName("§6Hell-Grau");
		o8.setItemMeta(mi8);
		ItemStack o9 = new ItemStack(Material.LIME_WOOL);
		ItemMeta mi9 = o9.getItemMeta();
		mi9.setDisplayName("§aHell-Grün");
		o9.setItemMeta(mi9);
		ItemStack o10 = new ItemStack(Material.MAGENTA_WOOL);
		ItemMeta mi10 = o10.getItemMeta();
		mi10.setDisplayName("§5Magenta");
		o10.setItemMeta(mi10);
		ItemStack o11 = new ItemStack(Material.ORANGE_WOOL);
		ItemMeta mi11 = o11.getItemMeta();
		mi11.setDisplayName("§6Orange");
		o11.setItemMeta(mi11);
		ItemStack o12 = new ItemStack(Material.PINK_WOOL);
		ItemMeta mi12 = o12.getItemMeta();
		mi12.setDisplayName("§dPink");
		o12.setItemMeta(mi12);
		ItemStack o13 = new ItemStack(Material.PURPLE_WOOL);
		ItemMeta mi13 = o13.getItemMeta();
		mi13.setDisplayName("§5Lila");
		o13.setItemMeta(mi13);
		ItemStack o14 = new ItemStack(Material.YELLOW_WOOL);
		ItemMeta mi14 = o14.getItemMeta();
		mi14.setDisplayName("§eGelb");
		o14.setItemMeta(mi14);
		ItemStack o15 = new ItemStack(Material.RED_WOOL);
		ItemMeta mi15 = o15.getItemMeta();
		mi15.setDisplayName("§4Rot");
		o15.setItemMeta(mi15);
		m.addButton(m.getRow(0), 0, o);
		m.addButton(m.getRow(0), 1, o1);
		m.addButton(m.getRow(0), 2, o2);
		m.addButton(m.getRow(0), 3, o3);
		m.addButton(m.getRow(0), 4, o4);
		m.addButton(m.getRow(0), 5, o5);
		m.addButton(m.getRow(0), 6, o6);
		m.addButton(m.getRow(0), 7, o7);
		m.addButton(m.getRow(0), 8, o8);
		m.addButton(m.getRow(1), 0, o9);
		m.addButton(m.getRow(1), 1, o10);
		m.addButton(m.getRow(1), 2, o11);
		m.addButton(m.getRow(1), 3, o12);
		m.addButton(m.getRow(1), 4, o13);
		m.addButton(m.getRow(1), 5, o14);
		m.addButton(m.getRow(1), 6, o15);
		ItemStack Barrier = new ItemStack(Material.BARRIER);
		ItemMeta BarrierMeta = Barrier.getItemMeta();
		BarrierMeta.setDisplayName("§4§lSchließen");
		Barrier.setItemMeta(BarrierMeta);
		m.addButton(m.getRow(2), 4, Barrier);
		m.open(p);

	}

	public boolean invFull(Player p) {
		return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (viewing.contains(event.getPlayer().getName()))
			viewing.remove(event.getPlayer().getName());
	}

	public MenuListenerAnimal addButton(Row row, int position, ItemStack item) {
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
		public abstract boolean click(Player clicker, MenuListenerAnimal menu, Row row, int slot, ItemStack item);
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