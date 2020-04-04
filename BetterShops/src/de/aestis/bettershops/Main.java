package de.aestis.bettershops;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	public static Main instance;
	public static String Version = "0.0.2";
	
	public void onEnable() {
		//Init Main Instance
		instance=this;

		try {
			getServer().getPluginManager().registerEvents((Listener) new EventListener(), this);
			getCommand("bsshop").setExecutor((CommandExecutor) new CommandManager());
		} catch (Exception ex) {
			System.out.println("Error whilst enabling: " + ex);
			return;
		}
		System.out.println("BetterShops v" + Main.Version + " sucessfully enabled.");
	}
	public void onDisable() {
		System.out.println("BetterShops v" + Main.Version + " disabled.");
	}
}
