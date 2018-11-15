package de.secretcraft.simpleCommands.commands;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ScStaatListener implements Listener {
	@EventHandler
public void onLeftClick(PlayerInteractEvent e) {
	 if(e.getAction() == (Action.LEFT_CLICK_BLOCK)&& e.getClickedBlock().getType() == Material.WALL_SIGN ) {
		 Sign sign = (Sign) e.getClickedBlock().getState();
                if(sign.getLine(0).equals("SC_Staat")) {
        	 Location loc=e.getClickedBlock().getLocation();
        	
        	 Location loc3=loc.subtract(2, 0, 0);
        	 Location loc4=loc.subtract(0, 0, 2);
        	Location loc5=loc.add(2, 0, 0);
        	Location loc6=loc.add(0, 0, 2);
        	Block block = e.getClickedBlock();
        	
        Block chesti=getBlockSignAttachedTo(block);
        Location chestl=chesti.getLocation();
        if(chestl.getBlock().getType().equals(Material.CHEST)) {
        	Chest chest=(Chest) chestl.getBlock().getState();
        	
        	 if(chest!=null) {
        		for(int i=0; i<27; i++) {
        			
        			if(chest.getInventory().getItem(i)!=null) {
        		
        			ItemStack item=chest.getInventory().getItem(i);
        		
        		 chest.getInventory().remove(item);
        	 }}
        	 }	}  
        		}
	 }
}
@EventHandler
	public void onLeftClick1(PlayerInteractEvent e) {
		 if(e.getAction() == (Action.LEFT_CLICK_BLOCK)&& e.getClickedBlock().getType() == Material.WALL_SIGN ) {
			 Sign sign = (Sign) e.getClickedBlock().getState();
	                if(sign.getLine(0).equals("Mülleimer")) {
	        	 Location loc=e.getClickedBlock().getLocation();
	        	
	        	 Location loc3=loc.subtract(2, 0, 0);
	        	 Location loc4=loc.subtract(0, 0, 2);
	        	Location loc5=loc.add(2, 0, 0);
	        	Location loc6=loc.add(0, 0, 2);
	        	Block block = e.getClickedBlock();
	        	
	        Block chesti=getBlockSignAttachedTo(block);
	        Location chestl=chesti.getLocation();
	        if(chestl.getBlock().getType().equals(Material.CHEST)) {
	        	Chest chest=(Chest) chestl.getBlock().getState();
	        	
	        	 if(chest!=null) {
	        		for(int i=0; i<27; i++) {
	        			
	        			if(chest.getInventory().getItem(i)!=null) {
	        		
	        			ItemStack item=chest.getInventory().getItem(i);
	        		
	        		 chest.getInventory().remove(item);
	        	 }}
	        	 }	}  
	        		}
		 }
	}
	public Block getBlockSignAttachedTo(Block block) {
       
            switch (block.getData()) {
           
                case 5:
                	
                    return block.getRelative(BlockFace.WEST);
                case 4:
                    return block.getRelative(BlockFace.EAST);
                case 2:
                    return block.getRelative(BlockFace.SOUTH);
                case 3:
                    return block.getRelative(BlockFace.NORTH);
            }
        return null;
	}
	
}

