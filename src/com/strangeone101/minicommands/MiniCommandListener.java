package com.strangeone101.minicommands;

import java.io.File;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.strangeone101.minicommands.util.GenericUtil;
import com.strangeone101.minicommands.util.TabUtil;


public class MiniCommandListener implements Listener {
	
	private MiniCommandsPlugin plugin;

	public MiniCommandListener(MiniCommandsPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChatEvent(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) return;
		String message = event.getMessage().startsWith("/") ? event.getMessage().substring(1) : event.getMessage();
		message = message.trim();
		String command = message.split(" ")[0];
		if (plugin.isCommand(command)) {
			String[] args = new String[0];
			if (message.contains(" ")) {
				args = message.substring(command.length() + 1).split(" ");
			}
			
			if (plugin.execute(command, args, event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onTab(PlayerChatTabCompleteEvent event) {
		if (!event.getTabCompletions().isEmpty()) return;
		String message = event.getChatMessage().startsWith("/") ? event.getChatMessage().substring(1) : event.getChatMessage();
		message = message.trim();
		String command = message.split(" ")[0];
		
		if (plugin.isCommand(command)) {
			event.getTabCompletions().addAll(TabUtil.getPossibleCompletions(event.getChatMessage().split(" "), GenericUtil.getPlayerNameList()));
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Not actually registered to the listener, as it doesn't accept generic events 
	 * @param event The event
	 */
	public void onGenericEvent(org.bukkit.event.Event event) {
		if (event instanceof PlayerMoveEvent) return; //Fires to often, shouldn't be used for scipting
		
		Class<? extends Event> clazz = event.getClass();
		while (clazz.getSuperclass() != null) {
			if (new File(plugin.getDataFolder(), "Events/" + clazz.getSimpleName() + ".js").exists()) {
				if (!new JSHandler().executeEvent(event, plugin.getCommandData(new File(plugin.getDataFolder(), "Events"), clazz.getSimpleName()))) {
					break;
				}
			}
			clazz = (Class<? extends Event>) clazz.getSuperclass();
		}
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerAnimationEvent(PlayerAnimationEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onEntityDamageEntityEvent(EntityDamageByEntityEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerConsumeEvent(PlayerItemConsumeEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerSneakEvent(PlayerToggleSneakEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerPickupEvent(PlayerPickupItemEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {onGenericEvent(event);}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {onGenericEvent(event);}
}
