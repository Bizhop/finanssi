package hello;

import java.util.ArrayList;
import java.util.List;

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
		messages.add(message);
		if(message.getMessage().startsWith("!roll")) {
			Dice dice = DiceRoller.roll(message.getMessage());
			ChatMessage system = new ChatMessage(dice, "system");
			messages.add(system);
		}
		return new Chat(messages);
	}
}
