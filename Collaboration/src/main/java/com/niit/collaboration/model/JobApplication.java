package com.niit.collaboration.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "C_JOB_APPLIED")
@Component
public class JobApplication {
	@Id
	private int id;
	
	@Column(name ="user_id")
	private String userID;
	@Column(name ="job_id")
	private String jobID;
	@Column(name ="date_applied")
	private Date dateApplied;
	
	private String remarks;
	
	private char status;

	public int getId() {
		return id;
	}

	public void setId(int long1) {
		this.id = long1;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getJobID() {
		return jobID;
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public Date getDateApplied() {
		return dateApplied;
	}

	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	

}