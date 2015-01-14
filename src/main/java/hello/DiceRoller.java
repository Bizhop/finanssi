package hello;

import java.util.Random;
import java.util.List;
import java.util.LinkedList;

public class DiceRoller {
	private static Random generator = new Random();

	public static Dice roll(String command) {
		//set 1d6 as default
		int rolls = 1;
		int type = 6;

		try {
			//try to parse command and parameters
			String[] cap = command.split(" ");
			if (cap.length > 1) {
				String parseThis = cap[1];
				String[] splitted = parseThis.split("d");
				if (splitted.length == 2) {
					rolls = Integer.parseInt(splitted[0]);
					type = Integer.parseInt(splitted[1]);
				}
			}
		} catch (Exception e) {
			//fall back to default if parsing fails
			command = "parse failed, doing 1d6";
			rolls = 1;
			type = 6;
		}
		
		List<Integer> dice = new LinkedList<Integer>();
		for(int i = 0; i < rolls; i++) {
			dice.add(generator.nextInt(type) + 1);
		}
		return new Dice(dice, command);
	}
}
