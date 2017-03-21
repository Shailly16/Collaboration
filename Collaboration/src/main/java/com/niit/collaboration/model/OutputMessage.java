package com.niit.collaboration.model;

import java.util.Date;

public class OutputMessage extends Message
{
	private MainDate time;
	
	private String userID;
	
	public MainDate getTime() {
		return time;
	}

	public void setTime(MainDate time) {
		this.time = time;
	}

	public OutputMessage(Message original, MainDate date,String string)
	{
		super(original.getId(), original.getMessage());
		this.time = date;
		this.setUserID(userID);
		
	}

	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
}