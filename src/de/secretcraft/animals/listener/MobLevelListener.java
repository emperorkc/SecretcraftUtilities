package de.secretcraft.animals.listener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Turtle;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Mushroom;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.flagBuy.commands.Config;
import de.secretcraft.main.Main;
import de.secretcraft.main.UtilitiesConfig;

import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;

public class MobLevelListener implements Listener {

	String prefix = UtilitiesConfig.getPrefix();
	int max = UtilitiesConfig.getMax();

	public static void setBar(Entity e) {
		String s = "§cLevel: §2" + getLevel(e) + " §3§l| §cXP: §2" + getXp(e) + "/" + getReq(e);
		e.setCustomName(s);
		e.setCustomNameVisible(true);

	}

	WorldGuard wg = WorldGuard.getInstance();

	@EventHandler
	public void onBreed(ProjectileHitEvent e) throws IOException {
		Entity enen = null;
		if (e.getEntity().getType().equals(EntityType.THROWN_EXP_BOTTLE)) {
			if (e.getHitEntity() != null) {
				if (isInType(e.getHitEntity()))
					if (isRegistered(e.getHitEntity())) {
						enen = e.getHitEntity();

						if (getLeveled(enen)) {
							setXp(enen, getXp(enen) + 1);

							if (getXp(enen) == getReq(enen)) {
								addLevel(enen);

								Bukkit.getWorld(enen.getWorld().getName()).playSound(enen.getLocation(),
										Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
							}
							setBar(enen);

						} else {
							addLevel(enen);
							setBar(enen);
						}
						List<Entity> l = Bukkit.getWorld(enen.getLocation().getWorld().getName()).getEntities();
						for (Entity em : l) {
							if (em.getType().equals(EntityType.EXPERIENCE_ORB)) {
								em.remove();
							}
						}
					}
			}
		}
	}

	public static boolean isInType(Entity e) {
		if (e.getType().equals(EntityType.COW) || e.getType().equals(EntityType.PIG)
				|| e.getType().equals(EntityType.CHICKEN) || e.getType().equals(EntityType.SHEEP)) {
			return true;
		}
		return false;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException {
		File ConfigFile = new File("plugins/Tiere", "joined");
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		if (Config.get("Joined." + e.getPlayer().getName()) == null) {
			Config.set("Joined." + e.getPlayer().getName(), 1);
			e.getPlayer().sendMessage("");
			e.getPlayer().sendMessage("");

			e.getPlayer().sendMessage(prefix
					+ " §6Das neue Tiere-Plugin wurde aufgespielt! Hole dir deinen ersten Tier-Fänger zum einfangen deines ersten Tieres am Spawn ab! Jedes weitere Auge kostet im Token-Shop 20 Token. Sobald du dein Tier auf deinem Grundstück gespawnt hast, kannst du mit dem Tier interagieren.");
			e.getPlayer().sendMessage("");
			e.getPlayer().sendMessage("");
			Config.save(ConfigFile);

		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) throws IOException {
		if (e.getDamager().getType().equals(EntityType.PLAYER)) {
			Player p = (Player) e.getDamager();
			if (isInType(e.getEntity())) {
				if (p.getItemInHand().getType().equals(Material.ENDER_EYE)) {
					if (p.getItemInHand().containsEnchantment(Enchantment.ARROW_DAMAGE)) {
						if (!isRegistered(e.getEntity())) {
							if (canBuild(p.getWorld().getName(), p, e.getEntity().getLocation())
									|| p.hasPermission("scu.*")) {
								ItemStack s = getEgg(e.getEntity());

								ItemMeta sm = s.getItemMeta();
								sm.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
								sm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
								sm.setLore(Arrays.asList("§6Tier: " + e.getEntity().getType().toString(),
										"§2Level: §6" + getLevel(e.getEntity())));

								p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
								s.setItemMeta(sm);
								if (!invFull(p)) {

									e.getEntity().remove();
									p.getInventory().addItem(s);
									removeEntity(e.getEntity());
									p.sendMessage(prefix + " §6Tier erfolgreich eingefangen!");
								} else {
									p.getItemInHand().setAmount(p.getItemInHand().getAmount() + 1);
									p.sendMessage(prefix + " §cDir fehlt der Platz im Inventar um das Tier zu fangen!");
								}

							}
						} else {
							p.sendMessage(prefix
									+ " §cDieses Tier musst du mit Emeralds fangen, da es bereits personalisiert ist!");
						}
						e.setCancelled(true);
					}
				}
			}

		}
	}

	public boolean invFull(Player p) {
		return !Arrays.asList(p.getInventory().getStorageContents()).contains(null);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBreedi(EntityBreedEvent e) throws IOException {
		if (isInType(e.getEntity())) {
			Entity mother = e.getMother();
			Entity father = e.getFather();
			Entity child = e.getEntity();
			Player p = (Player) e.getBreeder();
			if (isMaxed(p)) {
				e.setCancelled(true);
			} else {
				if (getBesitzer(mother).equals(p) && getBesitzer(father).equals(p)) {
					int level = 0;
					if (isRegistered(mother) && isRegistered(father)) {
						level = (int) (((getLevel(mother) + getLevel(father)) * 0.8) / 2);
					}
					Entity[] earray = father.getChunk().getEntities();
					int count = 0;
					for (Entity en : earray) {
						if (isInType(en)) {
							count++;
						}
					}
					if (count >= max) {
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							@Override
							public void run() {
								switch (child.getType()) {
								case COW:

									Cow c1 = (Cow) father;
									Cow c2 = (Cow) mother;

									c1.setBreed(true);
									c2.setBreed(true);

									break;

								case CHICKEN:
									Chicken cc1 = (Chicken) father;
									Chicken cc2 = (Chicken) mother;

									cc1.setBreed(true);
									cc2.setBreed(true);

									break;
								case PIG:

									Pig pi1 = (Pig) father;
									Pig pi2 = (Pig) mother;

									pi1.setBreed(true);
									pi2.setBreed(true);

									break;

								case SHEEP:

									Sheep s1 = (Sheep) mother;
									Sheep s2 = (Sheep) father;

									s1.setBreed(true);
									s2.setBreed(true);

									break;
								}
							}
						}, 20);
						p.sendMessage(
								prefix + " §cVermehren fehlgeschlagen, es sind zu viele Tiere im Chunk vorhanden!");
						child.remove();
					} else {

						if (isRegistered(mother) && isRegistered(father)) {
							setLevel(child, level);
							setBar(child);
							setBesitzer(child, p);
						}

					}
					if (((getLevel(mother) + getLevel(father)) / 2) > 25) {
						int dur = 6000 - (((getLevel(mother) + getLevel(father)) / 2) * 50);
						if (dur < 600) {
							dur = 600;
						}
						switch (child.getType()) {
						case COW:
							Cow c = (Cow) child;
							Cow c1 = (Cow) father;
							Cow c2 = (Cow) mother;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									c.setBreed(true);
								}
							}, 20);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									if (!(c1.canBreed() && c2.canBreed())) {
										c1.setBreed(true);
										c2.setBreed(true);
									}
								}
							}, dur);
							break;

						case CHICKEN:
							Chicken cc = (Chicken) child;
							Chicken cc1 = (Chicken) father;
							Chicken cc2 = (Chicken) mother;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									cc.setBreed(true);
								}
							}, 20);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									if (!(cc1.canBreed() && cc2.canBreed())) {
										cc1.setBreed(true);
										cc2.setBreed(true);
									}
								}
							}, dur);
							break;
						case PIG:
							Pig pi = (Pig) child;
							Pig pi1 = (Pig) father;
							Pig pi2 = (Pig) mother;
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									pi.setBreed(true);
								}
							}, 20);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									if (!(pi1.canBreed() && pi2.canBreed())) {
										pi1.setBreed(true);
										pi2.setBreed(true);
									}
								}
							}, dur);
							break;

						case SHEEP:
							Sheep s = (Sheep) child;
							Sheep s1 = (Sheep) mother;
							Sheep s2 = (Sheep) father;

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									s.setBreed(true);
								}
							}, 20);

							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								@Override
								public void run() {
									if (!(s1.canBreed() && s2.canBreed())) {
										s1.setBreed(true);
										s2.setBreed(true);
									}
								}
							}, dur);
							break;
						}
					}
				} else {
					p.sendMessage(prefix + " §3Dir müssen beide Tiere gehören.");
				}
			}

		}
	}

	@EventHandler
	public void onSheer(PlayerShearEntityEvent e) {
		if (getLeveled(e.getEntity())) {
			if (getLevel(e.getEntity()) > 1) {
				if (e.getEntity().getType().equals(EntityType.SHEEP)) {

					Sheep s = (Sheep) e.getEntity();

					int min = 0;
					if (getLevel(s) < 10) {
						min = 1;
					} else {
						min = (getLevel(s) / 10) + 1;
					}
					int max = getLevel(s);
					int range = max - min;
					int r = (int) (min + Math.random() * range);
					Bukkit.getWorld(e.getEntity().getWorld().getName())
							.dropItem(e.getEntity().getLocation().add(0, 2, 0), new ItemStack(wm(s), r));

				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onClick(PlayerInteractEntityEvent e) throws IOException {
		Player p = e.getPlayer();

		if (p.getItemInHand().getAmount() == 0) {
			if (isRegistered(e.getRightClicked())) {
				if (getBesitzer(e.getRightClicked()).equals(e.getPlayer()) || p.hasPermission("scu.*")) {
					File ConfigFile = new File("plugins/Tiere", "lastclicked");
					FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
					openInv(p, e.getRightClicked());
					Config.set("p." + p.getName(), e.getRightClicked().getUniqueId().toString());
					Config.save(ConfigFile);
				} else {
					e.getPlayer().sendMessage(prefix + " §cDies ist das Tier von §e"
							+ getBesitzer(e.getRightClicked()).getName() + "§c!");
				}
			}

		}
	}

	public static void close(Player p, Entity e) {
		MenuListenerAnimal m1 = new MenuListenerAnimal("", 3);
		m1.open(p);
		m1.close(p);
	}

	public static void openInv(Player p, Entity e) {
		if (isInType(e)) {
			ItemStack Barrier = new ItemStack(Material.BARRIER);
			ItemMeta BarrierMeta = Barrier.getItemMeta();
			BarrierMeta.setDisplayName("§4§lSchließen");
			Barrier.setItemMeta(BarrierMeta);
			ItemStack xp = new ItemStack(Material.EXPERIENCE_BOTTLE);
			ItemMeta xpm = xp.getItemMeta();
			xpm.setDisplayName("§6Tier Leveln");
			xp.setItemMeta(xpm);
			ItemStack en = new ItemStack(Material.ENDER_EYE);
			ItemMeta enm = en.getItemMeta();
			enm.setDisplayName("§3Tier einfangen!");
			enm.setLore(Arrays.asList("§2Kosten:", "§2" + (getLevel(e) * 2) + "§a Emeralds"));
			en.setItemMeta(enm);
			ItemStack temp = new ItemStack(Material.ACACIA_BOAT);
			ItemMeta drops = temp.getItemMeta();
			drops.setDisplayName("§cDrop-Multiplikator:");
			drops.setLore(Arrays.asList("§c" + ((getLevel(e) / 2) + 1) + "x §eerhöhte Drops!"));
			switch (e.getType()) {
			case SHEEP:
				MenuListenerAnimal m = new MenuListenerAnimal(
						"§bSchaaf - §2Level: §c" + getLevel(e) + " §6§l- §2Xp: " + getXp(e) + "§6/§2" + getReq(e), 3);
				ItemStack Wool = new ItemStack(Material.WHITE_WOOL);
				ItemMeta wm = Wool.getItemMeta();
				wm.setDisplayName("§3Farbe ändern");
				Wool.setItemMeta(wm);
				ItemStack eeye = new ItemStack(Material.SHEARS);
				ItemMeta eeyem = eeye.getItemMeta();
				eeyem.setDisplayName("§3Bonus-Wolle beim Scheeren");

				int min = 0;
				if (getLevel(e) < 10) {
					min = 1;
				} else {
					min = (getLevel(e) / 10) + 1;
				}
				int max = getLevel(e);
				int range = max - min;

				eeyem.setLore(Arrays.asList("§a" + min + "§3 - §a" + max + "§6 Wolle"));
				eeye.setItemMeta(eeyem);

				m.addButton(m.getRow(0), 1, Wool);
				m.addButton(m.getRow(0), 7, xp);
				m.addButton(m.getRow(2), 4, Barrier);
				m.addButton(m.getRow(1), 1, eeye);
				m.addButton(m.getRow(0), 4, en);
				m.open(p);
				break;
			case COW:
				MenuListenerAnimal m1 = new MenuListenerAnimal(
						"§bKuh - §2Level: §c" + getLevel(e) + " §6§l- §2Xp: " + getXp(e) + "§6/§2" + getReq(e), 3);

				ItemStack beef = new ItemStack(Material.BEEF);
				beef.setItemMeta(drops);
				m1.addButton(m1.getRow(0), 1, beef);
				m1.addButton(m1.getRow(0), 7, xp);
				m1.addButton(m1.getRow(2), 4, Barrier);
				m1.addButton(m1.getRow(0), 4, en);

				m1.open(p);
				break;

			case CHICKEN:
				MenuListenerAnimal m3 = new MenuListenerAnimal(
						"§bHuhn - §2Level: §c" + getLevel(e) + " §6§l- §2Xp: " + getXp(e) + "§6/§2" + getReq(e), 3);

				ItemStack beef1 = new ItemStack(Material.CHICKEN);
				beef1.setItemMeta(drops);
				m3.addButton(m3.getRow(0), 1, beef1);
				m3.addButton(m3.getRow(0), 7, xp);
				m3.addButton(m3.getRow(2), 4, Barrier);
				m3.addButton(m3.getRow(0), 4, en);

				m3.open(p);
				break;
			case PIG:
				MenuListenerAnimal m4 = new MenuListenerAnimal(
						"§bSchwein - §2Level: §c" + getLevel(e) + " §6§l- §2Xp: " + getXp(e) + "§6/§2" + getReq(e), 3);

				ItemStack beef2 = new ItemStack(Material.PORKCHOP);
				beef2.setItemMeta(drops);
				m4.addButton(m4.getRow(0), 1, beef2);
				m4.addButton(m4.getRow(0), 7, xp);
				m4.addButton(m4.getRow(2), 4, Barrier);
				m4.addButton(m4.getRow(0), 4, en);

				m4.open(p);
				break;

			}

		}
	}

	@EventHandler
	public void onBreed(EntitySpawnEvent e) {
		Entity[] en = e.getEntity().getChunk().getEntities();
		int i = 0;
		for (Entity ene : en) {
			if (isInType(ene)) {
				i++;
			}
		}
		if (isInType(e.getEntity())) {
			if (i >= max) {
				e.setCancelled(true);

			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLay(EntityDeathEvent e) throws IOException {
		if (isInType(e.getEntity())) {
			Entity ee = e.getEntity();

			if (getLeveled(e.getEntity())) {
				List<ItemStack> l = e.getDrops();

				for (ItemStack i : l) {

					int a = i.getAmount();

					i.setAmount(a * (getLevel(ee) / 2));
					if (i.getAmount() == 0) {
						i.setAmount(1);
					}
					Bukkit.getWorld(ee.getLocation().getWorld().getName()).dropItem(ee.getLocation().add(0, 2, 0),
							new ItemStack(i));

				}
				e.getEntity().setCustomName(null);
				e.getEntity().setCustomNameVisible(false);
				e.setCancelled(true);
				e.getEntity().remove();
				removeEntity(e.getEntity());
			}

		}
	}

	public static ArrayList<String> getPlayers() {
		ArrayList<String> l1 = new ArrayList<>();
		File ConfigFile2 = new File("plugins/Tiere/Liste", "Liste");
		FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
		if (Config2.get("Liste") != null) {
			ConfigurationSection s = Config2.getConfigurationSection("Liste.");
			for (String k : s.getKeys(false)) {
				l1.add(k);
			}
		}
		return l1;
	}

	public static ArrayList<String> getEntities(OfflinePlayer p1) {
		ArrayList<String> l1 = new ArrayList<>();
		File ConfigFile2 = new File("plugins/Tiere/Liste", "Liste");
		FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
		ConfigurationSection s = Config2.getConfigurationSection("Liste." + p1.getUniqueId().toString() + ".");
		for (String k : s.getKeys(false)) {
			l1.add(k);
		}

		return l1;
	}

	public static void removeEntity(Entity e) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		File ConfigFile2 = new File("plugins/Tiere/Liste", "Liste");
		FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
		ArrayList<String> l = getPlayers();
		if (!l.isEmpty()) {
			for (String u : l) {
				Config2.set("Liste." + u + "." + e.getUniqueId().toString(), null);
			}
		}
		Config.set("Besitzer", null);
		Config.set("Level", null);
		Config.set("Xp", null);
		Config.set("Req", null);
		Config.save(ConfigFile);
		Config2.save(ConfigFile2);

	}

	public static void setBesitzer(Entity e, OfflinePlayer p) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		File ConfigFile2 = new File("plugins/Tiere/Liste", "Liste");
		FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
		Config.set("Besitzer", p.getUniqueId().toString());
		Config.save(ConfigFile);
		Config2.set("Liste." + p.getUniqueId().toString() + "." + e.getUniqueId().toString(), "1");
		Config2.save(ConfigFile2);
	}

	public static OfflinePlayer getBesitzer(Entity e) {
		if (isRegistered(e)) {
			File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
			FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
			OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(Config.getString("Besitzer")));
			return p;
		}
		return null;
	}

	public static boolean isRegistered(Entity e) {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		return Config.getString("Besitzer") != null;
	}

	public static int getLevel(Entity e) {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		return Config.getInt("Level");
	}

	public static boolean getLeveled(Entity e) {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		return Config.getInt("Level") != 0;
	}

	public static int getReq(Entity e) {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		int req = Config.getInt("Level") * 15;

		return req;
	}

	public static int getXp(Entity e) {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		return Config.getInt("Xp");
	}

	public static void addLevel(Entity e) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		int level = Config.getInt("Level");
		Config.set("Level", level + 1);
		Config.save(ConfigFile);

		Config.set("Req", getReq(e));

		Config.set("Xp", 0);
		Config.save(ConfigFile);
	}

	public static void setLevel(Entity e, int i) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Level", i);

		Config.set("Req", i * 15);

		Config.save(ConfigFile);
	}

	public static void setReq(Entity e) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Req", getReq(e));
		Config.save(ConfigFile);
	}

	public static void setXp(Entity e, int i) throws IOException {
		File ConfigFile = new File("plugins/Tiere", e.getUniqueId().toString());
		FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
		Config.set("Xp", i);
		Config.save(ConfigFile);
	}

	public boolean canBuild(String worldname, OfflinePlayer p, Location l) {
		Plugin worldguard = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

		World world = Bukkit.getWorld(worldname);
		com.sk89q.worldedit.world.World world1 = BukkitAdapter.adapt(world);
		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);

		LocalPlayer localPlayer = ((WorldGuardPlugin) worldguard).wrapOfflinePlayer(p);
		java.util.List<String> l1 = regions.getApplicableRegionsIDs(BukkitAdapter.asVector(l));
		ProtectedRegion largest = null;
		if (l1.isEmpty()) {
			return true;
		} else {
			ProtectedRegion rg = regions.getRegion(l1.get(0));

			largest = rg;
			for (int i = 0; i < l1.size(); i++) {
				rg = regions.getRegion(l1.get(i));
				ProtectedRegion rg1 = null;
				if (i < l1.size() - 1) {
					rg1 = regions.getRegion(l1.get(i + 1));
				}
				if (rg1 != null) {
					if (rg.getPriority() >= rg1.getPriority() && largest.getPriority() < rg.getPriority()) {
						largest = rg;
					}

				}
			}
			if (largest.isMember(localPlayer) || largest.isOwner(localPlayer)) {
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getPlayer().getItemInHand().getType().equals(Material.ENDER_EYE)) {
				if (e.getPlayer().getItemInHand().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
					e.setCancelled(true);
					e.getPlayer().sendMessage(prefix + " §cDu kannst dieses Item nicht schmeißen!");
				}
			}
		}
	}

	@EventHandler
	public void onThrowa(PlayerInteractEntityEvent e) {

		if (e.getPlayer().getItemInHand().getType().equals(Material.ENDER_EYE)) {
			if (e.getPlayer().getItemInHand().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(prefix + " §cDu kannst dieses Item nicht schmeißen!");
			}

		}
	}

	@EventHandler
	public void chunk(ChunkLoadEvent e) throws IOException {

		if (e.getWorld().getName().toLowerCase().equals("stadtwelt")
				|| e.getWorld().getName().toLowerCase().equals("plotstart")
				|| e.getWorld().getName().toLowerCase().equals("spawn")) {
			Entity[] ea = e.getChunk().getEntities();
			int count = 0;
			for (Entity ef : ea) {
				if (isInType(ef)) {
					count++;
				}
			}
			ArrayList<Entity> l = new ArrayList<Entity>();
			if (count > max) {
				for (Entity ef : ea) {
					if (isInType(ef)) {
						l.add(ef);
					}

				}
			}
			if (!l.isEmpty()) {
				int count1 = 0;
				while (l.size() > max) {
					Entity min = null;
					int mini = 1000;
					for (int i = 0; i < l.size(); i++) {
						Entity eh = l.get(i);

						if (isRegistered(eh)) {

							if (canBuild(e.getWorld().getName(), getBesitzer(eh), eh.getLocation())) {

								if (getLevel(eh) < mini) {
									mini = getLevel(eh);
									min = eh;
								}

							} else {
								l.remove(eh);
								removeEntity(eh);

								eh.remove();
								min = null;
							}
						} else {

							l.remove(eh);
							if (e.getWorld().equals("stadtwelt"))
								count1++;
							eh.remove();
							min = null;

						}
					}
					if (min != null) {
						l.remove(min);
						if (e.getWorld().equals("stadtwelt"))
							count1++;
						min.remove();
					}
				}
				if (count > 10) {
					File ConfigFile = new File("plugins/Tiere/cleared", "cleared");
					FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
					Config.set(e.getChunk().getX() + "." + e.getChunk().getZ(), "cleared");
					Config.save(ConfigFile);
				}
			}
		}

	}

	@EventHandler
	public void onSpawnN(PlayerInteractEvent e) {
		if (e.getClickedBlock() != null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (e.getPlayer().getItemInHand().getType() != Material.AIR) {
					if (e.getPlayer().getItemInHand().getType().equals(Material.CHICKEN_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.CHICKEN_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.COW_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.HORSE_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.DONKEY_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.LLAMA_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.MOOSHROOM_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.PIG_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.SHEEP_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.OCELOT_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.PARROT_SPAWN_EGG)
							|| e.getPlayer().getItemInHand().getType().equals(Material.TURTLE_SPAWN_EGG)) {
						if (!e.getPlayer().getItemInHand().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {

							Entity[] earray = e.getClickedBlock().getChunk().getEntities();
							int count = 0;
							for (Entity en : earray) {
								if (isInType(en)) {
									count++;
								}

							}
							if (count >= max) {
								e.setCancelled(true);
								e.getPlayer().sendMessage(prefix + " §cChunk-Limit von " + max
										+ " Tiere darf nicht überschritten werden!");
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onSpawn(PlayerInteractEvent e) throws IOException {
		if (e.getClickedBlock() != null) {
			if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (e.getPlayer().getItemInHand().getType() != Material.AIR) {
					if (!e.getPlayer().getItemInHand().getType().equals(Material.ENDER_EYE)) {
						if (e.getPlayer().getItemInHand().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
							if (canBuild(e.getPlayer().getWorld().getName(), e.getPlayer(),
									e.getClickedBlock().getLocation()) || e.getPlayer().hasPermission("scu.*")) {
								if (!(isMaxed(e.getPlayer()))) {

									Entity[] earray = e.getClickedBlock().getChunk().getEntities();
									int count = 0;
									for (Entity en : earray) {
										if (isInType(en)) {
											count++;
										}
									}
									if (count < max) {
										Entity ent = Bukkit.getWorld(e.getPlayer().getWorld().getName()).spawnEntity(
												e.getClickedBlock().getLocation().add(0, 1, 0),
												getE(e.getPlayer().getItemInHand()));
										List<String> l = e.getPlayer().getItemInHand().getItemMeta().getLore();
										String a = l.get(1);

										String b = "";
										for (int i = 11; i < a.length(); i++) {
											b = b + a.charAt(i);

										}
										int z = Integer.parseInt(b);
										setLevel(ent, z);

										setBesitzer(ent, e.getPlayer());
										setBar(ent);
										e.getPlayer().getItemInHand()
												.setAmount(e.getPlayer().getItemInHand().getAmount() - 1);

									} else {
										e.getPlayer().sendMessage(prefix
												+ " §cDu hast die Maximale Anzahl von 15 personalisierten Tieren erreicht!");
									}

								}
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	public static boolean isMaxed(Player p) {
		File ConfigFile2 = new File("plugins/Tiere/Liste", "Liste");
		FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
		ArrayList<String> l1 = new ArrayList<>();
		if (Config2.get("Liste." + p.getUniqueId().toString()) != null) {
			ConfigurationSection s = Config2.getConfigurationSection("Liste." + p.getUniqueId().toString() + ".");
			for (String k : s.getKeys(false)) {
				l1.add(k);
			}
			if (l1.size() >= 15) {
				return true;
			}
		}
		return false;
	}

	public static EntityType getE(ItemStack i) {
		switch (i.getType()) {
		case COW_SPAWN_EGG:
			return EntityType.COW;
		case PIG_SPAWN_EGG:
			return EntityType.PIG;
		case CHICKEN_SPAWN_EGG:
			return EntityType.CHICKEN;
		case SHEEP_SPAWN_EGG:
			return EntityType.SHEEP;

		}
		return null;
	}

	public static ItemStack getEgg(Entity e) {
		switch (e.getType()) {
		case COW:
			return new ItemStack(Material.COW_SPAWN_EGG);
		case PIG:
			return new ItemStack(Material.PIG_SPAWN_EGG);
		case CHICKEN:
			return new ItemStack(Material.CHICKEN_SPAWN_EGG);
		case SHEEP:
			return new ItemStack(Material.SHEEP_SPAWN_EGG);

		}
		return null;
	}

	public static Material wm(Sheep s) {
		switch (s.getColor()) {
		case BLACK:
			return Material.BLACK_WOOL;
		case BLUE:
			return Material.BLUE_WOOL;
		case BROWN:
			return Material.BROWN_WOOL;
		case CYAN:
			return Material.CYAN_WOOL;
		case GRAY:
			return Material.GRAY_WOOL;
		case GREEN:
			return Material.GREEN_WOOL;
		case LIGHT_BLUE:
			return Material.LIGHT_BLUE_WOOL;
		case LIGHT_GRAY:
			return Material.LIGHT_GRAY_WOOL;
		case LIME:
			return Material.LIME_WOOL;
		case MAGENTA:
			return Material.MAGENTA_WOOL;
		case ORANGE:
			return Material.ORANGE_WOOL;
		case PINK:
			return Material.PINK_WOOL;
		case PURPLE:
			return Material.PURPLE_WOOL;
		case RED:
			return Material.RED_WOOL;
		case WHITE:
			return Material.WHITE_WOOL;
		case YELLOW:
			return Material.YELLOW_WOOL;
		}
		return null;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBabyTry(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		if (e.getRightClicked() != null) {
			if (p.getItemInHand().getAmount() != 0) {
				if (p.getItemInHand().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
					e.setCancelled(true);
				}
			}
		}
	}

	public static void createList() throws IOException {
		ArrayList<String> l1 = getPlayers();
		ArrayList<String> l2 = new ArrayList<>();
		ArrayList<Entity> l4 = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Entity highest = null;
			int highesti = 0;
			for (String ps : l1) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(ps));
				ArrayList<String> l3 = getEntities(p);
				for (String es : l3) {
					Entity e = Bukkit.getEntity(UUID.fromString(es));
					if (!l4.contains(e)) {
						if (e != null) {
							if (getLevel(e) >= highesti) {
								highest = e;
								highesti = getLevel(e);
							}
						}
					}
				}

			}
			l4.add(highest);
		}
		for (int i = 1; i < 11; i++) {
			File ConfigFile2 = new File("plugins/Tiere/Liste", "Topliste");
			FileConfiguration Config2 = YamlConfiguration.loadConfiguration(ConfigFile2);
			Entity e = l4.get(i - 1);
			if (e != null) {
				String art = "";
				switch (e.getType()) {
				case COW:
					art = "§7Kuh";
					break;
				case CHICKEN:
					art = "§eHuhn";
					break;

				case SHEEP:
					art = "§fSchaaf";
					break;
				case PIG:
					art = "§dSchwein";
					break;
				}
				Config2.set("Liste." + i, "§2" + i + ". §aBesitzer: §6" + getBesitzer(e).getName() + "§2, §aArt: " + art
						+ "§2, §aLevel: §6" + getLevel(e));

				Config2.save(ConfigFile2);
			}
		}
	}

}
