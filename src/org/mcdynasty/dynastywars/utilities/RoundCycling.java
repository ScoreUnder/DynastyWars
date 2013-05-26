package org.mcdynasty.dynastywars.utilities;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.mcdynasty.dynastywars.DynastyWars;

public class RoundCycling {
	private final DynastyWars plugin;

	public RoundCycling(DynastyWars plugin) {
		this.plugin = plugin;
	}

	public void startGame() {
		unloadWorld("World"); 
		deleteDir(new File("World"));
		WorldCreator wc = new WorldCreator("World");
		wc.environment(Environment.NORMAL);
		wc.createWorld();
		World world = Bukkit.getServer().getWorld("World");
		for(Player p : Bukkit.getOnlinePlayers()){
			if(DynastyWars.red.hasPlayer(p) || DynastyWars.blue.hasPlayer(p)) {
				final int topY = plugin.getServer().getWorld("World").getHighestBlockYAt(0, 0);
				p.teleport(new Location(world, 0, topY, 0));
			}
		}
	}
	
	public int Countdown;
	public int count = 30;
	public void startCount() {
		Countdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.getPlugin(), new Runnable() {
			public void run() {
				count--;
				if(count == 5 || count == 4 || count == 3 || count == 2 || count == 1) {
					Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + count + ChatColor.RED + " seconds until game starts!");
				}
				if(count == 0){
					if(DynastyWars.red.getSize() < 1 && DynastyWars.blue.getSize() < 1){
						plugin.getServer().getScheduler().cancelTask(Countdown);
						count = 120;
						plugin.roundOn = false;
						plugin.getServer().broadcastMessage(ChatColor.GOLD + "There wasn't enough players!");
						for
						(final Player p : Bukkit.getOnlinePlayers()){
							p.setHealth(20);
							World world = Bukkit.getServer().getWorld("Spawn");
							p.teleport(world.getSpawnLocation());
						}
						unloadWorld("World");
						deleteDir(new File("World"));
					}
				}
				if(count == 0){
					if(DynastyWars.red.getSize() > 1 && DynastyWars.blue.getSize() > 1){
						Bukkit.getServer().getScheduler().cancelTask(Countdown);
						plugin.roundOn = true;
						startGame();
						Bukkit.getServer().broadcastMessage(ChatColor.GOLD + "The Round has started!");
					}
				}
			}
		}, 0L, 20L);
	}
	
	private void deleteDir(File dir) {
		System.gc();
		File file2 = new File(plugin.getServer().getWorldContainer(), "World");

		if(file2.exists()) {
			File[] files = dir.listFiles();
			for(File file : files) {
				if(file.isDirectory()) {
					deleteDir(file);
					file.delete();
				} else {
					file.delete();
				}
			}

			dir.delete();
		}
	}
	public void unloadWorld(String world){
		System.gc();
		if(Bukkit.getWorld(world) instanceof World){
			for(Chunk c : Bukkit.getWorld(world).getLoadedChunks()){
				c.unload();
			}
		}
		if(Bukkit.getServer().unloadWorld(world,true)) {
			Bukkit.getLogger().info("World was unloaded successfully!");
		}
		else
		{
			Bukkit.getLogger().info("World wasn't unloaded successfully!");

		}
	}

}
