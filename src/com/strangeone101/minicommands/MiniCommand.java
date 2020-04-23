package com.strangeone101.minicommands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.Files;

public class MiniCommand implements CommandExecutor {
	
	private JavaPlugin plugin;
	
	public MiniCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!sender.hasPermission("minicommands.command")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
			return true;
		}	
		
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "Command usage is: " + ChatColor.DARK_GREEN + "/minicommands list " 
					+ ChatColor.GREEN + "or " + ChatColor.DARK_GREEN + "/minicommands <enable/disable> <command>");
			return true;
		} else if (args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(ChatColor.GREEN + "Listing all mini commands...");
			File folder = new File(plugin.getDataFolder(), "Minicommands");
			if (!folder.exists()) {
				sender.sendMessage(ChatColor.RED + "Error: Minicommand folder not found!");
			} else {
				String commands = "";
				for (File file : folder.listFiles()) {
					if (!file.getName().endsWith(".js")) continue;
					
					if (file.getName().startsWith("_")) {
						commands += ChatColor.YELLOW + "," + ChatColor.RED + file.getName().substring(1, file.getName().length() - 3);
					} else {
						commands += ChatColor.YELLOW + "," + ChatColor.GREEN + file.getName().substring(0, file.getName().length() - 3);
					}
				}
				
				if (commands.equalsIgnoreCase("")) {
					sender.sendMessage(ChatColor.RED + "No minicommands found!");
					return true;
				}
				
				commands = commands.substring(3); //1 char for the comma, 2 for the color code
				
				sender.sendMessage(ChatColor.GREEN + "Found commands: " + commands);
			}
		} else if (args[0].equalsIgnoreCase("enable")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED + "Usage is /minicommand enable <command>");
				return true;
			}
			
			File file = new File(plugin.getDataFolder(), "Minicommands/_" + args[1].toLowerCase() + ".js");
			
			if (!file.exists()) {
				File file2 = new File(plugin.getDataFolder(), "Minicommands/" + args[1].toLowerCase() + ".js");
				if (file2.exists()) {
					sender.sendMessage(ChatColor.RED + "Error: Minicommand is already enabled!");
				} else {
					sender.sendMessage(ChatColor.RED + "Error: Minicommand not found!");
				}
			} else {
				try {
					Files.move(file, new File(plugin.getDataFolder(), "Minicommands/" + args[1].toLowerCase() + ".js"));
					sender.sendMessage(ChatColor.GREEN + "Minicommand " + ChatColor.DARK_GREEN + args[1].toLowerCase() + ChatColor.GREEN + " enabled!");
				} catch (IOException e) {
					sender.sendMessage(ChatColor.RED + "ERROR: Failed to enable file! IO Exception!");
					e.printStackTrace();
				}
			}
		} else if (args[0].equalsIgnoreCase("disable")) {
			if (args.length == 1) {
				sender.sendMessage(ChatColor.RED + "Usage is /minicommand disable <command>");
				return true;
			}
			
			File file = new File(plugin.getDataFolder(), "Minicommands/" + args[1].toLowerCase() + ".js");
			
			if (!file.exists()) {
				File file2 = new File(plugin.getDataFolder(), "Minicommands/_" + args[1].toLowerCase() + ".js");
				if (file2.exists()) {
					sender.sendMessage(ChatColor.RED + "Error: Minicommand is already disabled!");
				} else {
					sender.sendMessage(ChatColor.RED + "Error: Minicommand not found!");
				}
			} else {
				try {
					Files.move(file, new File(plugin.getDataFolder(), "Minicommands/_" + args[1].toLowerCase() + ".js"));
					sender.sendMessage(ChatColor.GREEN + "Minicommand " + ChatColor.DARK_GREEN + args[1].toLowerCase() + ChatColor.GREEN + " disabled!");
				} catch (IOException e) {
					sender.sendMessage(ChatColor.RED + "ERROR: Failed to disable file! IO Exception!");
					e.printStackTrace();
				}
			}
		} else {
			sender.sendMessage(ChatColor.GREEN + "Command usage is: " + ChatColor.DARK_GREEN + "/minicommands list " 
					+ ChatColor.GREEN + "or " + ChatColor.DARK_GREEN + "/minicommands <enable/disable> <command>");
			return true;
		}
		return true;
	}

}
