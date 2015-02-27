package finanssi.controller;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import finanssi.db.UserRepository;
import finanssi.model.User;
import finanssi.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
            user.setStatus(User.Status.INACTIVE);
            user.setResetToken(UUID.randomUUID().toString());
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
    public @ResponseBody User login(@RequestBody User user, HttpServletResponse response) {
        Optional<User> findThis = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (findThis.isPresent()) {
            if(findThis.get().getStatus() == User.Status.INACTIVE) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            response.setStatus(HttpServletResponse.SC_OK);
            return findThis.get();
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @RequestMapping(value = "setPassword", method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Set password")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Invalid request.")})
    public void setPassword(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        if (user.getResetToken() != null && !user.getResetToken().isEmpty()) {
            Optional<User> findThis = repository.findByResetToken(user.getResetToken());
            if(findThis.isPresent()) {
                User foundUser = findThis.get();
                foundUser.setPassword(user.getPassword());
                foundUser.setResetToken(null);
                foundUser.setStatus(User.Status.ACTIVE);
                repository.save(foundUser);
                Mailer.sendResetLink(foundUser.getEmail(), foundUser.getResetToken());
                response.setStatus(HttpServletResponse.SC_OK);
            }
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