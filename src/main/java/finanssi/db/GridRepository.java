package finanssi.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import finanssi.model.Square;

public interface GridRepository extends MongoRepository<Square,String> {

}
