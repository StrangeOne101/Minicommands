package com.strangeone101.minicommands;

import java.util.logging.Level;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class JSHandler {
	
	protected ScriptEngine manager;
	
	public JSHandler() {
		ClassLoader cl = this.getClass().getClassLoader();
		Thread.currentThread().setContextClassLoader(cl);
		manager = new ScriptEngineManager().getEngineByName("nashorn");
		if (manager == null) {
			manager = new ScriptEngineManager().getEngineByName("js"); //Use old JS engine
		}		
	}
	
	private void initializeVariablesAndFunctions() {
		manager.put("plugin", MiniCommandsPlugin.PLUGIN);
		try {
			manager.eval("var ChatColor = Java.type('org.bukkit.ChatColor');");
			manager.eval("var Bukkit = Java.type('org.bukkit.Bukkit');");
			manager.eval("function sendTellraw(player, text) {"
					+ "       player.spigot().sendMessage(Java.type('com.strangeone101.minicommands.util.ChatParser').parse(text));"
					+ "   }");
			manager.eval("function wait(fnction, ticks) {"
					+ "       Java.extend(Java.type('org.bukkit.scheduler.BukkitRunnable'), {"
					+ "           run: fnction"
					+ "       }).runTaskLater(plugin, ticks);"
					+ "   }");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Executes JavaScript code and adds a few variables to the engine for use
	 * @param player The player running the code
	 * @param args The arguments provided with the command
	 * @param data The javascript code to run
	 */
	public void executeData(Player player, String[] args, String data) {
		manager.put("player", player);
		manager.put("args", args);
		manager.put("sender", player);
		try {
			initializeVariablesAndFunctions();
			manager.eval("function execute() { " + data + " };");
			manager.eval("execute();");
		} catch (Exception e) {
			player.sendMessage(e.getLocalizedMessage());
		}
	}
	
	public boolean executeEvent(Event event, String data) {
		manager.put("event", event);
		try {
			initializeVariablesAndFunctions();
			manager.eval("function execute() { " + data + " };");
			manager.eval("execute();");
			if (event instanceof Cancellable) {
				return ((Cancellable)event).isCancelled();
			}
		} catch (Exception e) {
			MiniCommandsPlugin.PLUGIN.getLogger().log(Level.WARNING, e.getLocalizedMessage());
		}
		return false;
	}

}
