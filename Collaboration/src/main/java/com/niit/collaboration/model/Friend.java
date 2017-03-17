package com.niit.collaboration.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Entity
@Table(name ="C_FRIEND")
@Component
public class Friend extends BaseDomain implements Serializable{
	@Id
	private int id;
	@Column(name = "user_id")
	private String userID;
	@Column(name = "friend_id")
	private String friendID;
    
	
	
	private String status;
	@Column(name="is_online")
	private char  isOnline;
	
	
	public char getIsOnline() {
		return isOnline;
	}


	public void setIsOnline(char isOnline) {
		this.isOnline = isOnline;
	}


	public Date getLastSeenTime() {
		return lastSeenTime;
	}


	public void setLastSeenTime(Date lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}


	@Column(name = "LAST_SEEN_TIME")
	private Date lastSeenTime;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public String getFriendID() {
		return friendID;
	}


	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}