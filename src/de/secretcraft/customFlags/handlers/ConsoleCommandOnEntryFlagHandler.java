package de.secretcraft.customFlags.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;

import de.secretcraft.customFlags.flags.Flags;

public class ConsoleCommandOnEntryFlagHandler extends FlagValueChangeHandler<String> {
	public static final Factory FACTORY = new Factory();

	public static class Factory extends Handler.Factory<ConsoleCommandOnEntryFlagHandler> {
		@Override
		public ConsoleCommandOnEntryFlagHandler create(Session session) {
			return new ConsoleCommandOnEntryFlagHandler(session);
		}
	}

	public ConsoleCommandOnEntryFlagHandler(Session session) {
		super(session, Flags.CONSOLE_COMMAND_ON_ENTRY);
	}

	@Override
	protected void onInitialValue(LocalPlayer player, ApplicableRegionSet set, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean onSetValue(LocalPlayer player, com.sk89q.worldedit.util.Location from,
			com.sk89q.worldedit.util.Location to, ApplicableRegionSet toSet, String currentValue, String lastValue,
			MoveType moveType) {
		String[] splitted = currentValue.split("\\s+");
		String command = "";
		for (String partCommand : splitted) {
			if (partCommand.equals("%player%")) {
				command += player.getName() + " ";
			} else {
				command += partCommand + " ";
			}
		}
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.dispatchCommand(console, command);

		return true;
	}

	@Override
	protected boolean onAbsentValue(LocalPlayer player, com.sk89q.worldedit.util.Location from,
			com.sk89q.worldedit.util.Location to, ApplicableRegionSet toSet, String lastValue, MoveType moveType) {
		// TODO Auto-generated method stub
		return true;
	}

}