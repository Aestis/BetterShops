package de.aestis.bettershops;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;


public class CommandManager implements CommandExecutor {
		
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] argArr) {

		if (cmd.getName().equalsIgnoreCase("disenchanter") && argArr.length == 0) {
			sender.sendMessage("[]========= BetterDisenchanter v" + Main.Version + " =========[]");
			sender.sendMessage(ChatColor.GOLD + ">>> Ducke dich (Shift) und linksklicke mit einem Item");
			sender.sendMessage(ChatColor.GOLD + "auf einen Zaubertisch.");
			sender.sendMessage(ChatColor.GRAY + "Beachte dabei, dass du genügend Erfahrung hast!");
		}
		return false;
	}
}
