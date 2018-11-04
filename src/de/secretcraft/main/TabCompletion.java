package de.secretcraft.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class TabCompletion implements TabCompleter {
	 @Override
	    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
		 List<String> list = new ArrayList<>();
	        if(cmd.getName().equalsIgnoreCase("konto") && args.length >= 0){
	            if(sender instanceof Player){
	               
	                list.add("kontostand");
	                list.add("einrichten");
                    list.add("einzahlen");
                    list.add("auszahlen");
                    list.add("aktivieren");
                    list.add("deaktivieren");
	             
	            
	                   
	                
return list;
	            }
	        } else {
	        	if(cmd.getName().equalsIgnoreCase("server") && args.length >= 0){
	        		 if(sender instanceof Player){
	        			
	 	                list.add("hauptserver");
	 	                list.add("funserver");
	 	               list.add("testserver");
	        		 }
	        	}
	        }
	        return list;
	    }
}
