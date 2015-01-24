package finanssi.db;

import finanssi.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ville on 24.1.2015.
 */
public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
}
