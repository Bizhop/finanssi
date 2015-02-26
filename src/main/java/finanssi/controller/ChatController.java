package finanssi.controller;

import finanssi.db.ChatMessageRepository;
import finanssi.model.Chat;
import finanssi.model.ChatMessage;
import finanssi.model.Dice;
import finanssi.util.DiceRoller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "chat")
public class ChatController {
    @Autowired
    private ChatMessageRepository repository;

//	private static List<ChatMessage> messages;
//	static
//	{
//		messages = new ArrayList<ChatMessage>();
//	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ChatMessage> getChat(@RequestParam(value = "lastRequest", required = true) Long lastRequest) {
        if(lastRequest == null) {
            return repository.findAll();
        }
        else {
            List<ChatMessage> messages = repository.findAll();
            if(messages == null || messages.isEmpty()) {
                return new ArrayList<ChatMessage>();
            }
            else {
                return messages.stream()
                        .filter(o -> o.getTimestamp() > lastRequest)
                        .sorted((e1, e2) -> Long.compare(e1.getTimestamp(), e2.getTimestamp()))
                        .collect(Collectors.toList());
            }
        }
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public HttpStatus postMessage(@RequestBody ChatMessage message ) {
		System.out.println(message);
        repository.save(message);
		if(message.getMessage().startsWith("!roll ") || message.getMessage().equals("!roll")) {
			Dice dice = DiceRoller.roll(message.getMessage());
			ChatMessage system = new ChatMessage(dice.toString(), "system");
			repository.save(system);
		}
		return HttpStatus.OK;
	}
}
