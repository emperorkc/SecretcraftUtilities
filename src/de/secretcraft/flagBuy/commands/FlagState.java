package de.secretcraft.flagBuy.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.secretcraft.main.Output;

public class FlagState {
	public static boolean hasSameValue(String flagname, String value, Player p, String region) {

		Plugin worldguard = p.getServer().getPluginManager().getPlugin("WorldGuard");
		WorldGuard wg = WorldGuard.getInstance();
		String worldname = p.getWorld().getName();
		World world = Bukkit.getWorld(worldname);
		LocalPlayer localPlayer = ((WorldGuardPlugin) worldguard).wrapPlayer(p);
		
		
	
com.sk89q.worldedit.world.World world1=BukkitAdapter.adapt(world);

			
		
		RegionManager regions = wg.getPlatform().getRegionContainer().get(world1);
		ProtectedRegion rg = regions.getRegion(region);
		
		Vector v = rg.getMaximumPoint();
		int x = v.getBlockX();
		int y = v.getBlockY();
		int z = v.getBlockZ();
		Location location = new Location(Bukkit.getWorld(p.getWorld().getName()), x, y, z, 0, 0);
		
	
		com.sk89q.worldguard.protection.regions.RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
		boolean state=true;
		String text1="";
		
		switch (flagname) {

		case "snow-fall":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.SNOW_FALL);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		
		case "tnt":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.TNT);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		
		case "snow-melt":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.SNOW_MELT);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			
		case "mob-spawning":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.MOB_SPAWNING);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		
		case "lighter":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.LIGHTER);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		
		case "pvp":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.PVP);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		
		case "build":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.BUILD);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			

			
			
		case "other-explosion":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.OTHER_EXPLOSION);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			

			
			
		case "creeper-explosion":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.CREEPER_EXPLOSION);
			
			if(state=true) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			

			
			
		case "enderpearl":
		state = query.testState(localPlayer.getLocation(), localPlayer, Flags.ENDERPEARL);
			
			if(state=false) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			
		case "ice-form":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.ICE_FORM);
			
			if(state=false) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
		

		case "ice-melt":
			state = query.testState(localPlayer.getLocation(), localPlayer, Flags.ICE_MELT);
			
			if(state=false) {
				text1="ein";
			} else {
				text1="aus";
			}
			if (text1.equals(value)) {
				return true;
			} else {
				return false;
			}
			

			
			
		case "hostilemobspawn":
			return false;
		default:
			Output.Err(p, "wFlag01");
			return false;
		}}

	

}
