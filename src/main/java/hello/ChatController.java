package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	private static List<ChatMessage> messages;
	static
	{
		messages = new ArrayList<ChatMessage>();
	}
	
	@RequestMapping(value = "chat", method = RequestMethod.GET)
	public Chat getChat() {
		return new Chat(messages);
	}
	
	@RequestMapping(value = "chat", method = RequestMethod.POST)
	public Chat postMessage(@RequestBody ChatMessage message ) {
		System.out.println(message.getUser() + ": " + message.getMessage());
		if(message.getMessage().startsWith("!roll")) {
			Set<Integer> dice = DiceRoller.roll("2d6");
			StringBuilder dieRoll = new StringBuilder();
			for(Integer die : dice) {
				dieRoll.append("[" + die + "] ");
			}
			ChatMessage system = new ChatMessage(dieRoll.toString(), "system");
			messages.add(system);
		}
		else {
			messages.add(message);
		}
		return new Chat(messages);
	}
}
