package com.niit.collaboration.controller;


import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.collaboration.model.MainDate;
import com.niit.collaboration.model.Message;
import com.niit.collaboration.model.OutputMessage;

@Controller
@RequestMapping("/")
public class ChatController {
	private static final Logger logger = 
			LoggerFactory.getLogger(ChatForumController.class);

  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }
    
  @MessageMapping("/chat")
  @SendTo("/queue/message/{friendID}")
  public OutputMessage sendMessage(Message message) {
	  logger.debug("Calling the method sendMessage");
	  logger.debug("Message:",message.getMessage());
    return new OutputMessage(message, new MainDate(),"shail");
  }
}