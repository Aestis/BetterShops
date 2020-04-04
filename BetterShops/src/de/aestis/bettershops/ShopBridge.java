package de.aestis.bettershops;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ShopBridge {
	
	private static ShopBridge instance;
	
	public static ShopBridge getInstance() {
		if (instance == null) {
			instance = new ShopBridge();
		}
		return instance;
	}

	/* ================================
	 * File Management And Input/Output
	 * ================================
	 * */
	
	public boolean shopFileExists(String playerName) {
		String fn = buildFileName(playerName);
		File fl = new File(Main.instance.getDataFolder() + "/data/" + fn + ".yml");
		if (fl.exists()) return true;
		return false;
	}
	
	public void initShopFile(String playerName) {
		 try {
         	getShopFile(playerName).createNewFile();
         }  catch (IOException ex) {
         	ex.printStackTrace();
         }
	}
	
	private String buildFileName(String playerName) {
		return playerName + " (" + Bukkit.getPlayer(playerName).getUniqueId() + ")";
	}
	
	private File getShopFile (String playerName) {
		return new File(Main.instance.getDataFolder() + "/data/" + buildFileName(playerName) + ".yml");
	}
	
	private FileConfiguration getFileConfiguration (String playerName) {
		FileConfiguration fc = new YamlConfiguration();
		try {
			fc.load(getShopFile(playerName));
			return fc;
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/* ================================
	 * General Entry Handling
	 * ================================
	 * */
	
	public ConfigurationSection getShopSection(String playerName, Block blk) {
		
		if (getFileConfiguration(playerName) == null) return null;
		
		FileConfiguration cfg = getFileConfiguration(playerName);
		String section = buildFileName(playerName);

		for(String key : cfg.getConfigurationSection("shops." + section).getKeys(false)) {
			String[] locstr = cfg.getString("shops." + section + "." + key + ".location").split(",");
			if (Integer.valueOf(locstr[1]) == blk.getX()
					&& Integer.valueOf(locstr[2]) == blk.getY()
					&& Integer.valueOf(locstr[3]) == blk.getZ())
			{
				return cfg.getConfigurationSection("shops." + section + "." + key);
			}
		}
		return null;
	}
	public ConfigurationSection getShopSectionByID(String playerName, Integer id) {
		
		if (getFileConfiguration(playerName) == null) return null;
		
		FileConfiguration cfg = getFileConfiguration(playerName);
		String section = buildFileName(playerName);

		for(String key : cfg.getConfigurationSection("shops." + section).getKeys(false)) {
			if (id == Integer.parseInt(key)) {
				return cfg.getConfigurationSection("shops." + section + "." + key);
			}
		}
		return null;
	}
	
	public int getShopNumber(String playerName, Block blk) {
		
		ConfigurationSection cfg = getShopSection(playerName, blk);
		if (cfg != null) {
			return Integer.parseInt(cfg.getName());
		}
		return -1;
	}
	
	public boolean isSellingShop (String playerName, int shopNumber) {
		ConfigurationSection cfg = getShopSectionByID(playerName, shopNumber);
		
		System.out.println("###" + cfg.getString("type") + "###");
		
		if (cfg != null && cfg.getString("type").equalsIgnoreCase("sell")) {
			return true;
		} else {
			return false;
		}
	}
}
