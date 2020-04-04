package de.aestis.bettershops;

import org.bukkit.entity.Player;

public class ShopInterface {

	private static ShopInterface instance;
	
	public static ShopInterface getInstance() {
		if (instance == null) {
			instance = new ShopInterface();
		}
		return instance;
	}
	
	public void showShopInformation(Player player, String shopSection) {
		
	}
}
