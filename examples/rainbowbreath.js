/**
* Allows players to toggle their RainbowBreath without typing out
* the very long easteregg sentence meant to toggle it. RainbowBreath
* is a feature of FireBreath, from a ProjectKorra addon.
*/

var perm1 = "bending.ability.FireBreath.RainbowBreath";
var perm2 = "elementumcore.rainbowbreath";
var FireBreath = Java.type('com.elementum.bendingpack.fire.FireBreath');

if (!sender.hasPermission(perm1) || !sender.hasPermission(perm2)) {
	sender.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
} else {
	var target;
	if (args.length >= 1) {
		if (sender.hasPermission(perm2 + ".others")) {
			if (Bukkit.getPlayer(args[0]) == null) {
				sender.sendMessage(ChatColor.RED + "That player is offline!");
			} else {
				target = Bukkit.getPlayer(args[0]);
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this command on others!");
		}
	} else {
		if ((sender instanceof Java.type('org.bukkit.entity.Player'))) {
			target = sender;
		} else {
			sender.sendMessage("Usage is /rainbowbreath <player>");
		}
	}

	if (target !== 'undefined' && target != null) {
		var name = target.getName() == sender.getName() ? "your" : target.getName() + "'s";
		if (!FireBreath.rainbowPlayer.contains(target.getUniqueId())) {
			FireBreath.rainbowPlayer.add(target.getUniqueId());
			player.sendMessage(ChatColor.GREEN + "You have toggled on " + name + " rainbow breath!");
		} else {
			FireBreath.rainbowPlayer.remove(target.getUniqueId());
			player.sendMessage(ChatColor.RED + "You have toggled off " + name + " rainbow breath!");
		}
	}
}
