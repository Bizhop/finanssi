package hello;

import java.util.List;

public class Dice {
	private List<Integer> dice;
	private String command;
	
	public Dice(List<Integer> dice, String command) {
		this.dice = dice;
		this.command = command;
	}

	public List<Integer> getDice() {
		return dice;
	}

	public void setDice(List<Integer> dice) {
		this.dice = dice;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public int getTotal() {
		int total = new Integer(0);
		for(Integer i : this.dice) {
			if(i != null) {
				total += i.intValue();
			}
		}
		return total;
	}
	
	public String getValues() {
		StringBuilder sb = new StringBuilder();
		for(Integer i : this.dice) {
			if(i != null) {
				sb.append("[" + i.intValue() + "] ");
			}
		}
		return sb.toString().trim();
	}
	
	public String toString() {
		return "Command: " + this.command + ", values: " + this.getValues() + ", total: " + this.getTotal();
	}
}
