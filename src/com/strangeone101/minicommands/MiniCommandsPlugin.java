package com.strangeone101.minicommands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MiniCommandsPlugin extends JavaPlugin {
	
	public static MiniCommandsPlugin PLUGIN;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		PLUGIN = this;
		
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		File folder = new File(getDataFolder(), "Minicommands");
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		folder = new File(getDataFolder(), "Events");
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		Bukkit.getPluginCommand("minicommands").setExecutor(new MiniCommand(this));
		Bukkit.getPluginManager().registerEvents(new MiniCommandListener(this), this);
		
		getLogger().info("Minicommands is doing a thing!");
	}
	
	/**
	 * Gets if the provided command is a valid mini command
	 * @param string The command
	 * @return If it is a valid mini command or not
	 */
	public boolean isCommand(String string) {
		File file = new File(getDataFolder(), "Minicommands/" + string.toLowerCase().trim() + ".js");
		
		return file.exists() && !(string.contains(".") || string.contains("/") || string.startsWith("_"));
	}
	
	public boolean execute(String command, String[] args, Player player) {
		if (!isCommand(command)) return false;
		
		String commandData = getCommandData(new File(getDataFolder(), "Minicommands"), command);
		
		if (commandData == null) return false;
		
		//Execute the data from the file
		new JSHandler().executeData(player, args, commandData);
		
		return true;
	}
	
	protected String getCommandData(File folder, String command) {
		File file = new File(folder, command.toLowerCase().trim() + ".js");
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String baseData = "";
			String line;
			line = reader.readLine();
			while (line != null) {
				baseData = baseData + "\n" + line;
				
				line = reader.readLine();
			}
			
			reader.close();
			return baseData;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
