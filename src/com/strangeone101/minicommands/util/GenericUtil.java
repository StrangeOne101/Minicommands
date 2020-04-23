package com.strangeone101.minicommands.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;

import com.strangeone101.minicommands.MiniCommandsPlugin;

public class GenericUtil {
	
	/**
	 * Checks if the string is an interger or not
	 * @param string The string to check
	 * @return If it's an integer or not.
	 */
	public static boolean isInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Turns a list of objects/strings into a single string
	 * that has nice formatting. E.g. [1, 2, 3] would become
	 * "1, 2 and 3"
	 * @param list The list of objects
	 * @return The fancy string
	 */
	public static String makeListFancy(List<? extends Object> list) {
		String s = "";
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {s = list.get(0).toString();}
			else if (i == list.size() - 1) {s = s + " and " + list.get(i);}
			else {s = s + ", " + list.get(i);}
		}
		return s;
	}
	
	/**
	 * Copies a resource located in the jar to a file.
	 * 
	 * @param resourceName The filename of the resource to copy
	 * @param output The file location to copy it to. Should not exist.
	 * @return True if the operation succeeded.
	 */
	public static boolean saveResource(String resourceName, File output) {
		if (MiniCommandsPlugin.PLUGIN.getResource(resourceName) == null) return false;
		
		try {
			InputStream in = MiniCommandsPlugin.PLUGIN.getResource(resourceName);
			
			if (!output.exists()) {
				output.createNewFile();
			}
			
			OutputStream out = new FileOutputStream(output);
	        byte[] buf = new byte[256];
	        int len;
	        
	        while ((len = in.read(buf)) > 0){
	        	out.write(buf, 0, len);
	        }
	        
	        out.close();
	        in.close();
	        
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/**Makes a description list for items.*/
	public static String getDescriptions(String description, int length)
	{
		Pattern p = Pattern.compile("\\G\\s*(.{1,"+length+"})(?=\\s|$)", Pattern.DOTALL);
		Matcher m = p.matcher(description);
		String newString = "";
		char lastColor = 'f';
		while (m.find())
		{
			String string = m.group(1);
			if (string.contains("\u00A7")) {
				lastColor = string.charAt(string.lastIndexOf('\u00A7') + 1);
			}
			newString = newString + "\n\u00A7" + lastColor + string;
		}
		return newString.substring(1);
	}
	
	/**
	 * Splits the string every x characters. Allows the same 
	 * string to wrap to the next line after so many characters
	 * @param line The full string
	 * @param length The length to cut off to
	 * */
	public static String lengthSplit(String line, int length)
	{
		Pattern p = Pattern.compile("\\G\\s*(.{1,"+length+"})(?=\\s|$)", Pattern.DOTALL);
		Matcher m = p.matcher(line);
		String newString = "";
		char lastColor = 'f';
		while (m.find())
		{
			String string = m.group(1);
			if (string.contains("\u00A7")) {
				lastColor = string.charAt(string.lastIndexOf('\u00A7') + 1);
			}
			newString = newString + "\n\u00A7" + lastColor + string;
		}
		return newString.substring(1);
	}

	public static List<String> getPlayerNameList() {
		List<String> players = new ArrayList<String>();
		Bukkit.getOnlinePlayers().forEach(x -> players.add(x.getName()));
		return players;
	}
}
