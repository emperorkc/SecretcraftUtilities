package de.secretcraft.customFlags.flags;

import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;

public final class Flags {

	public static final StateFlag HostileMobSpawn = new StateFlag("HostileMobSpawn", true);
	public static final StringFlag CONSOLE_COMMAND_ON_ENTRY = new StringFlag("console-command-on-entry");
	public static final StringFlag CONSOLE_COMMAND_ON_EXIT = new StringFlag("console-command-on-exit");

}
