package com.niit.collaboration.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;
import com.niit.collaboration.model.User;
@Repository
public interface JobDAO {
	
	public boolean postJob(Job job);
	public boolean updateJob(Job job);
	public List<Job> getAllvacantJobs();
	public boolean applyForJob(JobApplication jobApplication);
	public boolean updateJobApplication(JobApplication jobApplication);
	public JobApplication get(String userID, String jobID);
	public List<Job> list();
	public Job get(String id);
	

}