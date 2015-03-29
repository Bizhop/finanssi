package finanssi.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

import finanssi.db.GameStateRepository;
import finanssi.db.GridRepository;
import finanssi.db.MagRepository;
import finanssi.model.GameForm;
import finanssi.model.GameState;
import finanssi.model.Mag;
import finanssi.model.Player;
import finanssi.model.Square;
import finanssi.util.DiceRoller;
import finanssi.util.Utils;

@RestController
@RequestMapping(value = "game")
public class GameController {
	@Autowired
	private GridRepository grids;
	@Autowired
	private MagRepository mags;
	@Autowired
	private GameStateRepository games;
	
	private static final int LAST_SQUARE = 46;
	
	@RequestMapping(value = "getGrid", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get grid")
	public @ResponseBody List<Square> getGrid(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
		return grids.findAll();
	}
	
	@RequestMapping(value = "getMags", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get mags")
	public @ResponseBody List<Mag> getMags(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
		return mags.findAll();
	}
	
	@RequestMapping(value = "init", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "Init game")
	public @ResponseBody GameState init(	@RequestBody GameForm form, 
											HttpServletResponse response) {
		Optional<GameState> lookForGame = games.findByGameId(form.getGameId());
		if(lookForGame.isPresent()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		
		GameState state = new GameState(form.getGameId(), grids.findAll());
		games.save(state);
		return state;
	}
	
	@RequestMapping(value = "roll", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "Roll")
	public @ResponseBody GameState roll(	@RequestBody GameForm form, 
											HttpServletResponse response) {
		Optional<GameState> lookForGame = games.findByGameId(form.getGameId());
		if(lookForGame.isPresent()) {
			GameState state = lookForGame.get();
			Player findPlayer = state.getPlayers().get(form.getPlayer());
			if(findPlayer == null) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}
			else {
				Integer rolled = DiceRoller.roll(1, 6);
				Integer targetIndex = findPlayer.getPosition().getIndex() + rolled;
				if(targetIndex > LAST_SQUARE) {
					targetIndex = 1;
				}
				Square targetSquare = Utils.findByIndex(grids.findAll(), targetIndex);
				findPlayer.setPosition(targetSquare);
				games.save(state);
			}
			return state;
		}
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}
	
	@RequestMapping(value = "getGames", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get games")
	public @ResponseBody List<String> getGames(HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_OK);
		return games.findAll().stream()
				.map(o -> o.getGameId())
				.collect(Collectors.toList());
	}
	
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get games")
	public @ResponseBody GameState get(	@RequestParam(value = "gameId", required = true) String gameId,
										HttpServletResponse response) {
		Optional<GameState> lookForGame = games.findByGameId(gameId);
		if(lookForGame.isPresent()) {
			response.setStatus(HttpServletResponse.SC_OK);
			return lookForGame.get();
		}
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
	
	@RequestMapping(value = "addPlayer", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "Add player to game")
	public @ResponseBody GameState addPlayer(	@RequestBody GameForm form, 
												HttpServletResponse response) {
		//System.out.println(form);
		Optional<GameState> lookForGame = games.findByGameId(form.getGameId());
		if(lookForGame.isPresent()) {
			response.setStatus(HttpServletResponse.SC_OK);
			GameState state = lookForGame.get();
			
			String player = form.getPlayer();
			if(!state.getPlayers().containsKey(player)) {
				Square first = Utils.findByIndex(grids.findAll(), 1);
				state.getPlayers().put(player, new Player(player, first));
				games.save(state);
			}
			
			return state;
		}
		else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
}