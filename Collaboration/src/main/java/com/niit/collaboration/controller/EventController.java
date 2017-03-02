package com.niit.collaboration.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.EventDAO;
import com.niit.collaboration.model.Event;
import com.niit.collaboration.model.Event;

@RestController
public class EventController {

	Logger logger = LoggerFactory.getLogger(EventController.class);
	@Autowired
	Event event;
	
	@Autowired
	EventDAO eventDAO;
	
	
	

	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public ResponseEntity<List<Event>> listAllEvents(){
		logger.debug("Starting of the method listAllEvents");
		
		List<Event> events = eventDAO.list();
		if(events.isEmpty()){
			return new ResponseEntity<List<Event>>(HttpStatus.NO_CONTENT);
		}
		logger.debug("Ending of the method listAllUser");
		return new ResponseEntity<List<Event>>(events,HttpStatus.OK);
	}
	@RequestMapping(value = "/event/" , method = RequestMethod.POST)
	public ResponseEntity<Event> createEvent(@RequestBody Event event){
		logger.debug("Starting of the method create event");
		eventDAO.save(event);
		logger.debug("Ending of the method create event");
		return new ResponseEntity<Event>(event,HttpStatus.OK);
	}

	@RequestMapping(value ="/event/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Event> updateEvent(@PathVariable("id") String id , @RequestBody Event event){
		logger.debug("Starting of the method updateEvent");
		if(eventDAO.get(event.getId())== null){
			event = new Event();
			event.setErrorMessage("Event does not exist" + event.getId());
			return new ResponseEntity<Event>(event,HttpStatus.NOT_FOUND);
			
		}
		eventDAO.update(event);
		logger.debug("Ending of the method updateEvent");
		return new ResponseEntity<Event>(event,HttpStatus.OK);
	}

	@RequestMapping(value ="/event/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Event> deleteEvent(@PathVariable("id") String id, @RequestBody Event event){
		
	logger.debug("Starting of the method deleteEvent");
	if(eventDAO.get(event.getId())==null){
		event = new Event();
		event.setErrorMessage("Event does not exist" + event.getId());
		return new ResponseEntity<Event>(event,HttpStatus.NOT_FOUND);
	}
	eventDAO.delete(event);
	logger.debug("Ending of the method deleteEvent");
	return new ResponseEntity<Event>(event,HttpStatus.OK);
	}

	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
	public ResponseEntity<Event> getEvent(@PathVariable("id") String id,@RequestBody Event event){
		logger.debug("Starting of the method getEvent");
		if(eventDAO.get(event.getId())== null){
			event = new Event();
		    event.setErrorMessage("Event does not exist" + event.getId());
		    return new ResponseEntity<Event>(event,HttpStatus.NOT_FOUND);
		}
		logger.debug("Ending of the method getEvent");
		return new ResponseEntity<Event>(event,HttpStatus.OK);
	}




	}