package com.example.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.util.Scanner;

@Controller
public class ChatController {


	@Autowired
	private SimpMessageSendingOperations messagingTemplate;



	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

	@CrossOrigin(origins = "*")
	@MessageMapping("/chat1") // for send  => /app/chat
	//@SendTo("/queue/reply")
	public void register(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId , Principal principal) {


		//  failed attempt
	//	System.out.println(chatMessage.getContent());
//		System.out.println(readFromFile());
//		String sessionId = readFromFile();
		//System.out.println( headerAccessor.getSessionId());
		//headerAccessor.getSessionAttributes().put(chatMessage.getSender() , headerAccessor.getSessionId() );
		//  writeToFile(sessionId);
		//  messagingTemplate.convertAndSend("/queue/reply-"+sessionId, "test");
		System.out.println(principal.getName()); // get  userName

  		messagingTemplate.convertAndSendToUser(principal.getName(),"/queue/role","testads");

		//return chatMessage;



	}

	@CrossOrigin(origins = "*")
	@MessageMapping("/connection")
	public void connection(@Payload ChatMessage chatMessage, @Header("simpSessionId") String sessionId  , Principal principal) {

		//System.out.println(principal.getName());
//		System.out.println(sessionId);
 		messagingTemplate.convertAndSend("/connection/join-"+sessionId, principal.getName() );

	}



		public static void writeToFile(String sessionId){
        try {
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(sessionId);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



	@CrossOrigin(origins = "*")
	@MessageMapping("/chat2") // for send  => /app/chat
	@SendTo("/topic2/public2") // for subscribe
	public ChatMessage topic2(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

		// System.out.println( headerAccessor.getSessionId());
		headerAccessor.getSessionAttributes().put(chatMessage.getSender() , headerAccessor.getSessionId() );

		return chatMessage;
	}


	public String readFromFile(){

		try {
			File myObj = new File("filename.txt");
			Scanner myReader = new Scanner(myObj);

			String data = myReader.nextLine();
			myReader.close();

			return data;

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		return "";
	}

}
