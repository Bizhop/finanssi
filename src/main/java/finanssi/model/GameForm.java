package finanssi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameForm {
	String player;
	String gameId;
	
	@JsonCreator
	public GameForm(@JsonProperty("player") String player, @JsonProperty("gameId") String gameId) {
		this.player = player;
        this.gameId = gameId;
	}
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	@Override
	public String toString() {
		return this.gameId + ", " + this.player;
	}
}
