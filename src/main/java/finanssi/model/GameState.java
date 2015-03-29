package finanssi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class GameState {
	@Id
	String gameId;
	Map<String, Player> players;
	List<Square> board;
	
	public GameState() {}
	
	//init new game
	public GameState(String gameId, List<Square> board) {
		this.gameId = gameId;
		this.players = new HashMap<String, Player>();
		//players.put(name, new Player(name));
		this.board = board;
	}
	
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public Map<String, Player> getPlayers() {
		return players;
	}
	public void setPlayers(Map<String, Player> players) {
		this.players = players;
	}
	public List<Square> getBoard() {
		return board;
	}
	public void setBoard(List<Square> board) {
		this.board = board;
	}
}