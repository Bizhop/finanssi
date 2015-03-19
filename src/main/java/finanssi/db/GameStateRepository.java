package finanssi.db;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import finanssi.model.GameState;

public interface GameStateRepository extends MongoRepository<GameState, String> {
	public Optional<GameState> findByGameId(String gameId);
}
