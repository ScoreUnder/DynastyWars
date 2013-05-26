package org.mcdynasty.dynastywars.commands;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcdynasty.dynastywars.DynastyWars;

public class TeamExecutor implements CommandExecutor {

	public static void random(Player p) {
		Random r = new Random();
		int choose = r.nextInt(2) + 1;

		switch (choose) {
		default:
		case 1:
			DynastyWars.red.addPlayer(p);
			p.sendMessage(ChatColor.GOLD + "You joined the " + ChatColor.RED
					+ "Red " + ChatColor.GOLD + "team");
			Bukkit.broadcastMessage(ChatColor.AQUA + p.getName()
					+ ChatColor.GOLD + " joined the battle");
			break;
		case 2:
			DynastyWars.blue.addPlayer(p);
			p.sendMessage(ChatColor.GOLD + "You joined the " + ChatColor.BLUE
					+ "Blue " + ChatColor.GOLD + "team");
			Bukkit.broadcastMessage(ChatColor.AQUA + p.getName()
					+ ChatColor.GOLD + " joined the battle");
			break;
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("join")) {
				if (DynastyWars.blue.hasPlayer(p)
						|| DynastyWars.red.hasPlayer(p)) {
					sender.sendMessage(ChatColor.GOLD
							+ "You are already on a team");
					return true;
				} else if (DynastyWars.blue.getSize() < DynastyWars.red
						.getSize()) {
					DynastyWars.blue.addPlayer(p);
					sender.sendMessage(ChatColor.GOLD + "You joined the "
							+ ChatColor.BLUE + "Blue " + ChatColor.GOLD
							+ "team");
					Bukkit.broadcastMessage(ChatColor.AQUA + p.getName()
							+ ChatColor.GOLD + " joined the battle");
					return true;
				} else if (DynastyWars.red.getSize() < DynastyWars.blue
						.getSize()) {
					DynastyWars.red.addPlayer(p);
					sender.sendMessage(ChatColor.GOLD + "You joined the "
							+ ChatColor.RED + "Red " + ChatColor.GOLD + "team");
					Bukkit.broadcastMessage(ChatColor.AQUA + p.getName()
							+ ChatColor.GOLD + " joined the battle");
					return true;
				} else if (DynastyWars.red.getSize() == DynastyWars.blue
						.getSize()) {
					random(p);
					return true;
				}

			} else if (cmd.getName().equalsIgnoreCase("leave")) {
				if (DynastyWars.red.hasPlayer(p)
						|| DynastyWars.blue.hasPlayer(p)) {
					DynastyWars.red.removePlayer(p);
					DynastyWars.blue.removePlayer(p);
					Bukkit.broadcastMessage(ChatColor.AQUA + p.getName()
							+ ChatColor.GOLD + " left the battle");
					return true;
				}
			}
		}
		return false;
	}

}
