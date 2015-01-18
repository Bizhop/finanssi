package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

/**
 * Created by ville on 17.1.2015.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public HttpStatus create(@RequestBody User user) {
        //String query = String.format("{'userName': '%s'}", user.getUserName());
        //System.out.println("Query: " + query);
        User findThis = repository.findByUserName(user.getUserName());
        System.out.println(findThis);
        if(findThis == null) {
            repository.save(user);
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public HttpStatus login(@RequestBody User user) {
        //String query = String.format("{'userName': '%s', 'password': '%s'}", user.getUserName(), user.getPassword());
        //System.out.println("Query: " + query);
        User findThis = repository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        System.out.println(findThis);
        if(findThis != null) {
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.UNAUTHORIZED;
        }
    }

	@RequestMapping(method = RequestMethod.GET)
	public List<User> list() {
		return repository.findAll();
	}

	@RequestMapping(value = "{userName}", method = RequestMethod.GET)
	public User getUser(@PathVariable String userName) {
		return repository.findByUserName(userName);
	}
}
