/**
 * 
 */
package org.merih.service;

import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author Merih
 *
 */
@Scope(value = "singleton")
@Service
public final class DictionaryService {

	public static final HashMap<Character, Integer> alphabet = new HashMap<>(29);
	static {
		alphabet.put('A', 1);
		alphabet.put('B', 3);
		alphabet.put('C', 4);
		alphabet.put('Ç', 4);
		alphabet.put('D', 3);

		alphabet.put('E', 1);
		alphabet.put('F', 7);
		alphabet.put('G', 5);
		alphabet.put('Ğ', 8);
		alphabet.put('H', 5);

		alphabet.put('I', 2);
		alphabet.put('İ', 1);
		alphabet.put('J', 10);
		alphabet.put('K', 1);
		alphabet.put('L', 1);

		alphabet.put('M', 2);
		alphabet.put('N', 1);
		alphabet.put('O', 2);
		alphabet.put('Ö', 7);
		alphabet.put('P', 5);

		alphabet.put('R', 1);
		alphabet.put('S', 3);
		alphabet.put('Ş', 4);
		alphabet.put('T', 4);
		alphabet.put('U', 1);

		alphabet.put('Ü', 3);
		alphabet.put('V', 7);
		alphabet.put('Y', 3);
		alphabet.put('Z', 4);
	};

	private static Set<String> dictionary = new HashSet<>();

	/**
	 * 
	 */
	@SuppressWarnings("resource")
	public DictionaryService() {
		super();
		Scanner file;
		ClassLoader classLoader = getClass().getClassLoader();
		file = new Scanner(classLoader.getResourceAsStream("scrabble_turkish_dictionary.txt"));

		while (file.hasNext()) {
			dictionary.add(file.next().trim());
		}

	}

	public static boolean isAWord(String letters) {
		return dictionary.contains(letters.toLowerCase());
	}

	public static int calculatePoints(String letters) {
	 
	 
		return IntStream.range(0, letters.length())
				.map(i -> alphabet.get(letters.toUpperCase().charAt(i)))
				.reduce(0, Integer::sum);
		 
	}
}
