package hello;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class DiceRoller {
	private static Random generator = new Random();

	public static Set<Integer> roll(String specs) {
		//TODO: parser

		//returns only 2d6 atm
		int rolls = 2;
		int type = 6;
		Set<Integer> dice = new HashSet<Integer>();
		for(int i = 0; i < rolls; i++) {
			dice.add(generator.nextInt(type) + 1);
		}
		return dice;
	}
}
