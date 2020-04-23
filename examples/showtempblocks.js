/**
* Revealed (temporily) what blocks were ProjectKorra TempBlocks
* A good example of how to do BukkitRunnable's in Javascript
*/

var TempBlock = Java.type('com.projectkorra.projectkorra.util.TempBlock');
var Material = Java.type('org.bukkit.Material');
var BukkitRunnable = Java.type('org.bukkit.scheduler.BukkitRunnable');
var ElementumCore = Java.type('com.elementum.elementumcore.ElementumCore');

for each (var i in TempBlock.instances.keySet()) {
    var block = i;

    var type = block.getType();
    block.setType(Material.GLASS);
    var task = Java.extend(BukkitRunnable,  {
        run: function() {
            block.setType(type);
        }
    });

    new task().runTaskLater(ElementumCore.plugin, 50);


}

player.sendMessage(ChatColor.RED + TempBlock.instances.keySet().size() +  " Temp Blocks revealed");
