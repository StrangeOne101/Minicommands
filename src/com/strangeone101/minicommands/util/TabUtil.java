package com.strangeone101.minicommands.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabUtil {

	/**
	 * Breaks down the possible list and returns what is applicble depending on
	 * what the user has currently typed.
	 * 
	 * @author D4rKDeagle<br>
	 *         <br>
	 *         (Found at
	 *         <a>https://bukkit.org/threads/help-with-bukkit-tab-completion
	 *         -api.166436</a>)
	 * @param args Args of the command. Provide all of them.
	 * @param possibilitiesOfCompletion List of things that can be given
	 */
	public static List<String> getPossibleCompletions(String[] args, List<String> possibilitiesOfCompletion) {
		String argumentToFindCompletionFor = args[args.length - 1];

		List<String> listOfPossibleCompletions = new ArrayList<String>();

		for (String foundString : possibilitiesOfCompletion) {
			if (foundString.regionMatches(true, 0, argumentToFindCompletionFor, 0, argumentToFindCompletionFor.length())) {
				listOfPossibleCompletions.add(foundString);
			}
		}
		return listOfPossibleCompletions;
	}

	public static List<String> getPossibleCompletions(String[] args, String[] possibilitiesOfCompletion) {
		return getPossibleCompletions(args, Arrays.asList(possibilitiesOfCompletion));
	}
}
