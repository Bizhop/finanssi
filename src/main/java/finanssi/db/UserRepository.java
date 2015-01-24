package finanssi.db;

import finanssi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ville on 17.1.2015.
 */
public interface UserRepository extends MongoRepository<User, String> {
    public User findByUserNameAndPassword(String userName, String password);
    public User findByUserName(String userName);
}