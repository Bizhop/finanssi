package finanssi.db;

import finanssi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Created by ville on 17.1.2015.
 */
public interface UserRepository extends MongoRepository<User, String> {
    //public Optional<User> findByUserNameAndPassword(String userName, String password);
    public Optional<User> findByUserName(String userName);
	public Optional<User> findByEmailAndPassword(String email, String password);
	Optional<User> findByEmail(String email);
}