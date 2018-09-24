package com.target.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class TextFilter {

	static Properties prop = new Properties();
	static int largestWordLength = 0;

	static {
		loadConfigs();
	}

	private static void loadConfigs() {
		try (InputStream input = TextFilter.class.getClassLoader()
				.getResourceAsStream("objectionableWords.properties")) {
			if (input == null) {
				System.out.println("Unable to find " + "objectionableWords.properties");
				return;
			}
			// load a properties file from class path, inside static method
			prop.load(input);
			Iterator<String> wordsIterator = prop.stringPropertyNames().iterator();
			while (wordsIterator.hasNext()) {
				try {
					String word = wordsIterator.next();
					if (word.length() > largestWordLength) {
						largestWordLength = word.length();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Boolean hasObjectionableWords(String input) {
		if (input == null) {
			return Boolean.FALSE;
		}
		// Replacing leet/eleet system letters
		input = input.replaceAll("1", "i").replaceAll("!", "i").replaceAll("3", "e").replaceAll("4", "a")
				.replaceAll("@", "a").replaceAll("5", "s").replaceAll("7", "t").replaceAll("0", "o")
				.replaceAll("9", "g");
		input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");
		Boolean hasObjectionableWords = Boolean.FALSE;
		for (int start = 0; start < input.length(); start++) {
			for (int offset = 1; offset < (input.length() + 1 - start) && offset <= largestWordLength; offset++) {
				String wordToCheck = input.substring(start, start + offset);
				if (prop.getProperty(wordToCheck) != null) {
					String[] ignoreCheck = prop.getProperty(wordToCheck).split(",");
					boolean ignore = false;
					for (int s = 0; s < ignoreCheck.length; s++) {
						if (input.contains(ignoreCheck[s])) {
							ignore = true;
							break;
						}
					}
					if (!ignore) {
						hasObjectionableWords = Boolean.TRUE;
						break;
					}
				}
			}
			if (hasObjectionableWords)
				break;
		}
		return hasObjectionableWords;
	}
}
