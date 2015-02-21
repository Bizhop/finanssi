package finanssi.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import finanssi.db.UserRepository;
import finanssi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Created by ville on 17.1.2015.
 */
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Create user")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request.")})
    public void create(@RequestBody User user, HttpServletResponse response) {
        Optional<User> byEmail = repository.findByEmail(user.getEmail());
		Optional<User> byUserName = repository.findByUserName(user.getUserName());
        if (!byEmail.isPresent() && !byUserName.isPresent()) {
            repository.save(user);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Login user")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request.")})
    public void login(@RequestBody User user, HttpServletResponse response) {
        Optional<User> findThis = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (findThis.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

	@RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get all users")
    public
    @ResponseBody
    List<User> list(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        return repository.findAll();
	}

	@RequestMapping(value = "{userName}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get user")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request.")})
    public
    @ResponseBody
    User getUser(@PathVariable String userName, HttpServletResponse response) {
        Optional<User> findThis = repository.findByUserName(userName);
        if (findThis.isPresent()) {
            response.setStatus(HttpServletResponse.SC_OK);
            return findThis.get();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}