package de.secretcraft.customFlags.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.FlagValueCalculator;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.customFlags.flags.Flags;



public class HostileMobSpawn implements Listener {



	public HostileMobSpawn() {

	}

	private boolean testState(Entity entity, StateFlag flag) {
		ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery()
				.getApplicableRegions(BukkitAdapter.adapt(entity.getLocation()));
		List<ProtectedRegion> checkForRegions = new ArrayList<>();
		for(ProtectedRegion region : regions)
		{
				checkForRegions.add(region);
		}
		ProtectedRegion global =WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(entity.getLocation().getWorld())).getRegion(ProtectedRegion.GLOBAL_REGION);
		FlagValueCalculator calc= new FlagValueCalculator(checkForRegions, global);
		State check= calc.queryValue(null, flag);
		if (check==State.ALLOW) {
			return true;
		}
		else {
			return false;
		}
		

	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onMobSpawn(CreatureSpawnEvent event) {
		EntityType entity = event.getEntity().getType();
		if (isMonster(entity)) {
			boolean canSpawn = testState(event.getEntity(), Flags.HostileMobSpawn);
			if (!canSpawn) {
				event.setCancelled(true);

			}
		}

	}

	private boolean isMonster(EntityType entity) {
		switch (entity) {
		case BAT:
		case BLAZE:
		case CAVE_SPIDER:
		case CREEPER:
		case ELDER_GUARDIAN:
		case ENDERMAN:
		case ENDERMITE:
		case GHAST:
		case GUARDIAN:
		case HUSK:
		case ILLUSIONER:
		case MAGMA_CUBE:
		case PIG_ZOMBIE:
		case SHULKER:
		case SILVERFISH:
		case SKELETON:
		case SLIME:
		case SPIDER:
		case VINDICATOR:
		case VEX:
		case WITHER_SKELETON:
		case ZOMBIE:
		case WITCH:
		case WITHER:
		case STRAY:
		case ZOMBIE_VILLAGER:
			return true;
		default:
			return false;

		}
	}

}
