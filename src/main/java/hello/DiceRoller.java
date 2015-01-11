package hello;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;

public class DiceRoller {
	private static Random generator = new Random();

	public static List<Integer> roll(String specs) {
		//TODO: parser

		//returns only 2d6 atm
		int rolls = 2;
		int type = 6;
		List<Integer> dice = new LinkedList<Integer>();
		for(int i = 0; i < rolls; i++) {
			dice.add(generator.nextInt(type) + 1);
		}
		return dice;
	}
}
