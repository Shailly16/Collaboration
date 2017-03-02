package com.niit.collaboration.controller;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.JobDAO;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;;

@RestController
public class JobController {

	private static final Logger logger = LoggerFactory.getLogger(JobController.class);

	@Autowired
	Job job;

	@Autowired
	JobApplication jobApplication;

	@Autowired
	JobDAO jobDAO;

	@Autowired
	HttpSession httpSession;

	 @CrossOrigin(origins = "http://localhost:8081")
	//@CrossOrigin(orinigs="http://snapdeal.com")
	@RequestMapping(value = "/getAllJobs/", method = RequestMethod.GET) // $http.get(base_url+"/getAllJobs/)
	public ResponseEntity<List<Job>> getAllOpendJobs() {
		logger.debug("Starting of the method getAllOpendJobs");
		List<Job> jobs = jobDAO.getAllOpendJobs();
		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getMyAppliedJobs/", method = RequestMethod.GET)

	public ResponseEntity<List<Job>> getMyAppliedJobs() {
		logger.debug("Starting of the method getMyAppliedJobs");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Job> jobs = new ArrayList<Job>();

		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			job.setErrorCode("404");
			job.setErrorMessage("You have to login to see you applied jobs");
			jobs.add(job);

		} else {
			jobs = jobDAO.getMyAppliedJobs(loggedInUserID);
		}

		return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
	}

	@RequestMapping(value = "/getJobDetails/{jobID}", method = RequestMethod.GET)

	public ResponseEntity<Job> getJobDetails(@PathVariable("jobID") Long jobID) {
		logger.debug("Starting of the method getJobDetails");
		Job job = jobDAO.getJobDetails(jobID);

		if (job == null) {
			job = new Job();
			job.setErrorCode("404");
			job.setErrorMessage("Job not available for this id : " + jobID);
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateJob", method = RequestMethod.PUT)
	public ResponseEntity<Job> updateJob(@RequestBody Job job) {
		logger.debug("Starting of the method updateJob");
	
		if (jobDAO.updateJob(job) == false) {
			job.setErrorCode("404");
			job.setErrorMessage("Not able to updated a job");
			logger.debug("Not able to updated a job");
		} else {
			job.setErrorCode("200");
			job.setErrorMessage("Successfully updated the job");
			logger.debug("Successfully poted the updateJob");
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/postAJob", method = RequestMethod.POST)
	public ResponseEntity<Job> postAJob(@RequestBody Job job) {
		logger.debug("Starting of the method postAJob");
		job.setStatus('V'); // 1. V-Vacant 2. F-Filled 3. P-Pending 4.

		if (jobDAO.save(job) == false) {
			job.setErrorCode("404");
			job.setErrorMessage("Not able to post a job");
			logger.debug("Not able to post a job");
		} else {
			job.setErrorCode("200");
			job.setErrorMessage("Successfully poted the job");
			logger.debug("Successfully poted the job");
		}

		return new ResponseEntity<Job>(job, HttpStatus.OK);
	}

	@RequestMapping(value = "/applyForJob/{jobID}", method = RequestMethod.POST)
	public ResponseEntity<JobApplication> applyForJob(@PathVariable("jobID") String jobID) {
		logger.debug("Starting of the method applyForJob");
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");

		if (loggedInUserID == null || loggedInUserID.isEmpty()) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorCode("You have loggin to apply for a job");
		} else {

			if (isUserAppliedForTheJob(loggedInUserID, jobID) == false) {
				jobApplication.setJobID(jobID);
				jobApplication.setUserID(loggedInUserID);
				jobApplication.setStatus('N'); // N-Newly Applied; C->Call For
												// Interview, S->Selected
				jobApplication.setDateApplied(new Date(System.currentTimeMillis()));

				logger.debug("Applied Date : " + jobApplication.getDateApplied());

				if (jobDAO.save(jobApplication)) {
					jobApplication.setErrorCode("200");
					jobApplication.setErrorCode("You have successfully applied for the job :" + jobID +
							" Hr will getback to you soon.");
					logger.debug("Not able to apply for the job");
				}
			} else // If the user already applied for the job
			{
				jobApplication.setErrorCode("404");
				jobApplication.setErrorCode("You already applied for the job" + jobID);
				logger.debug("Not able to apply for the job");
			}

		}

		// jobApplication = jobDAO.getJobApplication(jobID);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}
	
	
	

	@RequestMapping(value = "/selectUser/{userID}/{jobID}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> selectUser(@PathVariable("userID") String userID,
			@PathVariable("jobID") String jobID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method selectUser");
		jobApplication = updateJobApplicationStatus(userID, jobID, 'S', remarks);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/callForInterview/{userID}/{jobID}/{remarks", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> callForInterview(@PathVariable("userID") String userID,
			@PathVariable("jobID") String jobID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method canCallForInterview");

		jobApplication = updateJobApplicationStatus(userID, jobID, 'C', remarks);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	@RequestMapping(value = "/rejectJobApplication/{userID}/{jobID}/{remarks}", method = RequestMethod.PUT)
	public ResponseEntity<JobApplication> rejectJobApplication(@PathVariable("userID") String userID,
			@PathVariable("jobID") String jobID, @PathVariable("remarks") String remarks) {
		logger.debug("Starting of the method rejectJobApplication");
		jobApplication = updateJobApplicationStatus(userID, jobID, 'R', remarks);

		return new ResponseEntity<JobApplication>(jobApplication, HttpStatus.OK);
	}

	private JobApplication updateJobApplicationStatus(String userID, String jobID, char status, String remarks) {
		logger.debug("Starting of the method updateJobApplicationStatus");

		if (isUserAppliedForTheJob(userID, jobID) == false) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorCode(userID + " not applied for the job " + jobID);
			return jobApplication;
		}

		String loggedInUserRole = (String) httpSession.getAttribute("loggedInUserRole");
		logger.debug("loggedInUserRole:" + loggedInUserRole);
		if (loggedInUserRole == null || loggedInUserRole.isEmpty()) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorCode("You are not logged in");
			return jobApplication;
		}

		if (!loggedInUserRole.equalsIgnoreCase("admin")) {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorCode("You are not admin.  You can not do this Operation");
			return jobApplication;
		}
		jobApplication = jobDAO.getJobApplication(userID, jobID);

		jobApplication.setStatus(status);
		jobApplication.setRemarks(remarks);
		if (jobDAO.updateJobApplication(jobApplication)==true) {
			jobApplication.setErrorCode("200");
			jobApplication.setErrorCode("Successfully updated the status as " + status);
			logger.debug("Successfully updated the status as " + status);
		} else {
			jobApplication.setErrorCode("404");
			jobApplication.setErrorCode("Not able to update the status " + status);
			logger.debug("Not able to update the status" + status);
		}

		return jobApplication;
	}

	private boolean isUserAppliedForTheJob(String userID, String jobID) {

		if (jobDAO.getJobApplication(userID, jobID) == null) {
			return false;
		}

		return true;

	}

}









