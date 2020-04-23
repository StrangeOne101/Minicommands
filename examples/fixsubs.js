/***
* A command that fixes players missing subelements from
* the ProjectKorra plugin if they somehow managed to lose
* one. This was a tempory measure until the issue was fixed.
*/

var Element = Java.type('com.projectkorra.projectkorra.Element');
var SubElement = Java.type('com.projectkorra.projectkorra.Element.SubElement');
var GeneralMethods = Java.type('com.projectkorra.projectkorra.GeneralMethods');

var did = false;

for (var i = 0; i < Element.getAllSubElements().length; i += 1) {
	var sub = Element.getAllSubElements()[i];
	if (bPlayer.hasElement(sub.getParentElement()) && bPlayer.hasSubElementPermission(sub) && !bPlayer.hasSubElement(sub)) {
		bPlayer.addSubElement(sub);
		did = true;
	}
}

GeneralMethods.saveSubElements(bPlayer);

if (did) {
	player.sendMessage(ChatColor.GREEN + "Your subelements have been fixed!");
} else {
	player.sendMessage(ChatColor.RED + "You are not missing any subelements!");
}
