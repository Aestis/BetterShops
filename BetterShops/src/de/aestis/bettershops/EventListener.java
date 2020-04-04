package de.aestis.bettershops;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class EventListener implements Listener {
	
	ShopBridge Bridge = ShopBridge.getInstance();
	ShopInterface Interface = ShopInterface.getInstance();
	
	Helpers Helper = Helpers.getInstance();
	
	private static boolean debug = true;
	
	@EventHandler
	public void onBlockInteraction(PlayerInteractEvent event) throws FileNotFoundException, IOException, InvalidConfigurationException {
		
		//It's Nothing Interesting (Seriously!)
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) return;
		
		//Get Clicked Block
		Block blk = event.getClickedBlock();
		
		if (blk.getType() == Material.WALL_SIGN) {
			
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				
				//Fetch Sign Data
				BlockState state = blk.getState();
				Sign sign = (Sign)state;

				if (sign != null && blk.getState() instanceof Sign) {
				    BlockData data = sign.getBlockData();
				    if (data instanceof Directional)
				    {
				        Directional directional = (Directional)data;
				        Block blockBehind = blk.getRelative(directional.getFacing().getOppositeFace());
				        Bukkit.broadcastMessage(blockBehind.getLocation().toString());
				        Bukkit.broadcastMessage("Block Behind Type: " + blockBehind.getType().name());
				    }
				}
				
				//Bukkit.broadcastMessage("Sign is facing: " + face);
				ArrayList<String> lines = Helper.getSignLines(sign);
				
				//If First Line = Shop Identifier - Proceed		
				
				if (lines.get(0).equalsIgnoreCase("[shop]") || lines.get(0).equalsIgnoreCase("[sell shop]") || lines.get(0).equalsIgnoreCase("[buy shop]")) {
					
					//Check If This File Does Not Exist
					
					if (debug) event.getPlayer().sendMessage("You Clicked On A Shop Sign.");
					
					if (!Bridge.shopFileExists(lines.get(3))) {
						if (debug) event.getPlayer().sendMessage("This File Does Not Exist.");
						return;
					}
					
					//Everything Seems To Be Okay! Getting ShopID Now
					
					int sid = Bridge.getShopNumber(lines.get(3), blk);
					
					//Check If Shop ID Can Be Found
					
					if (sid == -1) {
						if (debug) event.getPlayer().sendMessage("This Shop Does Not Exist.");
						return;
					}
					
					//Check If Own Shop
					
					if (lines.get(3).equalsIgnoreCase(event.getPlayer().getName())) {
						if (debug) event.getPlayer().sendMessage("This Is Your Own Shop.");
					}
					
					if (Bridge.isSellingShop(lines.get(3), sid)) {
						if (debug) event.getPlayer().sendMessage("Shop With ID = " + sid + " Is Selling Stuff.");
					} else {
						if (debug) event.getPlayer().sendMessage("Shop With ID = " + sid + " Is Buying Stuff.");
					}
					
				}
			}
		}
	}
	
	 /*public ArmorStand setArmorStand(ItemStack item, Location loc) {
	        ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
	        stand.setVisible(false);
	        stand.setHelmet(item);
	        return stand;
	    }*/
}
