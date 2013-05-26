package org.mcdynasty.dynastywars;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.mcdynasty.dynastywars.commands.TeamExecutor;
import org.mcdynasty.dynastywars.listeners.PlayerListener;
import org.mcdynasty.dynastywars.utilities.RoundCycling;

public class DynastyWars extends JavaPlugin implements Listener {
	public boolean roundOn = false;
	public boolean cooldown = false;
	private DynastyWars plugin;
	private final Logger log = Logger.getLogger("Minecraft");
	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard board = manager.getNewScoreboard();
	public static Team red = board.registerNewTeam("Red");
	public static Team blue = board.registerNewTeam("Blue");
	RoundCycling round = new RoundCycling(plugin);
	
	public void onEnable() {
		round.startCount();
		plugin = this;
		try {
			log.info("[DynastyWars] Loading config file");
			plugin.getConfig().save(new File("plugins/Infection/config.yml"));
			log.info("[DynastyWars] Config file successfully found and loaded");
		} catch (IOException e) {
			log.severe("[DynastyWars] Could not find the config file!");
			e.printStackTrace();
		}
		this.roundOn = false;
		deleteDir(new File("World"));
		log.info("[DynastyWars] Loading....");
		final PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), plugin);
		log.info("[DynastyWars] Loading commands...");
		getCommand("join").setExecutor(new TeamExecutor());
		getCommand("leave").setExecutor(new TeamExecutor());
		log.info("[DynastyWars] Successfully loaded commands");
		
	}
	
	public void onDisable() {
		unloadWorld("World");
	}
	
	public DynastyWars getPlugin(){
		return plugin;
	}
	
	public void reloadSettings()
	{
		reloadConfig();
		saveConfig();
	}
	
	public void deleteDir(File dir) {
		System.gc();
		File file2 = new File("World");

		if(file2.exists()) {
			File[] files = dir.listFiles();
			for(File file : files) {
				if(file.isDirectory()) {
					this.deleteDir(file);
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
