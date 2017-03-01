package com.niit.collaboration.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import oracle.sql.CLOB;

@Entity
@Table(name="C_BLOG")
@Component

public class Blog extends BaseDomain{
	
	@Id
	private int id;
	
	private String title;
	
	@Column(name="user_id")
	private String userID;
	
	@Transient
	@Column(name="date_time")
	private Date dateTime;
	private char status;
	private String reason;
	private CLOB description;
	public CLOB getDescription() {
		return description;
	}
	public void setDescription(CLOB description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
