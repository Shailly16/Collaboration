package com.niit.collaboration.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;
import com.niit.collaboration.model.User;
@Repository
public interface JobDAO {
	
	public List<Job> getAllOpendJobs();
	public Job getJobDetails(Long id);
	public JobApplication getJobApplication(String userID, String jobID);
	public JobApplication getJobApplication(String jobID);
	public boolean updateJob(Job job);
	public boolean updateJobApplication(JobApplication jobApplication);
	public boolean save(JobApplication jobApplied);
	public boolean save(Job job);
	public List<Job> getMyAppliedJobs(String userID);
	
}