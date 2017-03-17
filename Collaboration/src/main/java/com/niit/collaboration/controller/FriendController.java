package com.niit.collaboration.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.niit.collaboration.dao.FriendDAO;
import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.Friend;
import com.niit.collaboration.model.User;

@RestController
public class FriendController {
	
	private static final Logger logger = LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	User user;
	@Autowired
	UserDAO userDAO;

	@Autowired
	FriendDAO friendDAO;
	
	@Autowired
	Friend friend;
	@Autowired
	HttpSession http;
	@RequestMapping(value= "/myFriends",method =RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriend(HttpSession http){
		
		logger.debug("->->->->calling method listAllMyFriends");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		String loggedInUserID = (String) http.getAttribute("loggedInUserID");
		List<Friend> myFriends = new ArrayList<Friend>();
		if(loggedInUserID == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
			
		}
       
		logger.debug("getting friends of : " + loggedInUserID);
		 myFriends = friendDAO.getMyFriends(loggedInUserID);

		if (myFriends.isEmpty()) {
			logger.debug("Friends does not exsit for the user : " + loggedInUserID);
			friend.setErrorCode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		logger.debug("Send the friend list ");
		
		return new ResponseEntity<List<Friend>>(myFriends,HttpStatus.OK);
		
	}
	
	@RequestMapping(value ="/addFriend/{friendID}", method= RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendID") String friendID,HttpSession http){
		
	    logger.debug("->->->->calling method sendFriendRequest");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		friend.setUserID(loggedInUser.getId());
		friend.setFriendID(friendID);
		friend.setStatus("N"); // N - New, R->Rejected, A->Accepted
		friend.setIsOnline('N');
		if (friendDAO.save(friend) ==true)
		  {
			friend.setErrorCode("200");
			friend.setErrorMessage("Your request sent successfully");
		  }
		  else
		  {
			  friend.setErrorCode("404");
			  friend.setErrorMessage("Could not send the request");
	
			  
		  }
		 return new ResponseEntity<Friend>(friend,HttpStatus.OK);
		
	}
	private boolean isUserExist(String id)
	{
		if(userDAO.get(id)==null)
			return false;
		else
			return true;
	}
	
	
	private boolean isFriendRequestAvailabe(String friendID)
	{
		String loggedInUserID = (String) http.getAttribute("loggedInUserID");
		
		if(friendDAO.get(loggedInUserID,friendID)==null)
			return false;
		else
			return true;
	}
	@RequestMapping(value ="/unfriend/{friendID}",method =RequestMethod.GET)
	public ResponseEntity<Friend> unfriend(@PathVariable("friendID") String friendID,HttpSession http){

		logger.debug("->->->->calling method unfriend");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		friend.setUserID(loggedInUser.getId());
		friend.setFriendID(friendID);
		friend.setStatus("U");
		
		if (friendDAO.update(friend) ==true)
		  {
			friend.setErrorCode("200");
			friend.setErrorMessage("You are no more friends");
		  }
		  else
		  {
			  friend.setErrorCode("404");
			  friend.setErrorMessage("Cant be unfriended");
	
			  
		  }
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/getMyFriendRequests",method = RequestMethod.GET)
	public ResponseEntity<Friend> getMyFriendRequest(HttpSession http){
		
		logger.debug("->->->->calling method getMyFriendRequest");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		friendDAO.getNewFriendRequest(loggedInUser.getId());
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/acceptFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendID") String friendID,HttpSession http){
		
		logger.debug("->->->->calling method AcceptFriendRequest");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		friend.setUserID(loggedInUser.getId());
		friend.setFriendID(friendID);
		friend.setStatus("A");
		if(friendDAO.update(friend)==true)
			{friend.setErrorCode("200");
			friend.setErrorMessage("Friend Request Accepted");}
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value ="/rejectFriend/{friendID}", method = RequestMethod.GET)
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendID") String friendID,HttpSession http){
		logger.debug("->->->->calling method RejectFriendRequest");
		User loggedInUser =(User) http.getAttribute("loggedInUser");
		friend.setUserID(loggedInUser.getId());
		friend.setFriendID(friendID);
		friend.setStatus("R");
		if (friendDAO.update(friend) ==true)
		  {
			friend.setErrorCode("200");
			friend.setErrorMessage("Friend Request Rejected");
		  }
		  else
		  {
			  friend.setErrorCode("404");
			  friend.setErrorMessage("Cant reject friendRequest ");
	
			  
		  }
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	@RequestMapping(value ="/myFriends/{id}", method = RequestMethod.GET)
	public ResponseEntity<Friend> getMyFriendsTemp(@PathVariable("id")String id){
		
		logger.debug("->->->->calling method getMyFriendsTemp");
		List<Friend> myFriends = friendDAO.getMyFriends(id);
		return new ResponseEntity<Friend>((Friend) myFriends,HttpStatus.OK);
	
}
	private Friend updateRequest(String friendID, String status) {
		logger.debug("Starting of the method updateRequest");
		String loggedInUserID = (String) http.getAttribute("loggedInUserID");
		logger.debug("loggedInUserID : " + loggedInUserID);
		
		if(isFriendRequestAvailabe(friendID)==false)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist.  So you can not update to "+status);
		}
		
		if (status.equals("A") || status.equals("R"))
			friend = friendDAO.get(friendID, loggedInUserID);
		else
			friend = friendDAO.get(loggedInUserID, friendID);
		friend.setStatus(status); // N - New, R->Rejected, A->Accepted

		friendDAO.update(friend);

		friend.setErrorCode("200");
		friend.setErrorMessage(
				"Request from   " + friend.getUserID() + " To " + friend.getFriendID() + " has updated to :" + status);
		logger.debug("Ending of the method updateRequest");
		return friend;

	}

	@RequestMapping("/getRequestsSendByMe")
	public ResponseEntity<List<Friend>>  getRequestsSendByMe()
	{
		logger.debug("->->->->calling method getRequestsSendByMe");
		
		String loggedInUserID = (String) http.getAttribute("loggedInUserID");
		List<Friend> requestSendByMe = friendDAO.getRequestsSendByMe(loggedInUserID);
		
		logger.debug("->->->->Ending method getRequestsSendByMe");
		
		return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);
		
	}
	
}
	
	
