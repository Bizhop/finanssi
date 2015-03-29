package finanssi.model;

public class Player {
	String name;
	Square position;
	
	public Player(String name, Square position) {
		this.name = name;
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public Square getPosition() {
		return position;
	}
	public void setPosition(Square position) {
		this.position = position;
	}
}
