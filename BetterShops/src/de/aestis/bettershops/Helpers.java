package de.aestis.bettershops;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class Helpers {
	
	private static Helpers instance;
	
	Economy econ = null;
	
	public static Helpers getInstance() {
		if (instance == null) {
			instance = new Helpers();
		}
		return instance;
	}

	public ArrayList<String> getSignLines(Sign sign) {
		ArrayList<String> lines = new ArrayList<String>();
		for (int i = 0; i < 4; i++) lines.add(ChatColor.stripColor(sign.getLine(i)));
		return lines;
	}
	
	private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();

        return econ != null;
    }
	
}
