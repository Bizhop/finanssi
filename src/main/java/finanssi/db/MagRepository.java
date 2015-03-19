package finanssi.db;

import org.springframework.data.mongodb.repository.MongoRepository;

import finanssi.model.Mag;

public interface MagRepository extends MongoRepository<Mag, String> {

}
