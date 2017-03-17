'use strict';

app	.controller('JobController',['JobService','$location', '$rootScope',
						function(JobService,  $location, $rootScope) {
							console.log("JobController...")
							var self = this;

							self.job = {
								id : '',
								title : '',
								description : '',
								dateTime : '',
								qualification : '',
								status : '',
								errorCode : '',
								errorMessage : ''
							};
							self.jobs = [];
							
							self.applyForJob = applyForJob

							function applyForJob(jobID) {
								console.log("applyForJob");
								var currentUser = $rootScope.currentUser
								console.log("currentUser.id:" + currentUser.id)
								//if(currentUser) -> not null, not empty and defined
								
								if (typeof currentUser.id == 'undefined') 
									{
									   alert("Please login to apply for the job")
	                                     console.log("User not logged in.  Can not apply for job")
	                                     /*$location
											.path('/login');*/
									   return
									
									}
								console.log("->userID :" + currentUser.id
										+ "  applying for job:" + jobID)
										
										
								JobService
										.applyForJob(jobID)
										.then(
												function(data) {
													self.job = data;
													alert(self.job.errorMessage)
												},
												function(errResponse) {
													console
															.error('Error while applying for job request');
												});

							}

							self.getMyAppliedJobs = function() {
								console.log('calling the method getMyAppliedJobs');
								JobService.getMyAppliedJobs()
								.then(
									function(d) {
								    	self.jobs = d;
									/* $location.path('/view_friend'); */
								    }, 
								    
								    function(errResponse) {
									console.error('Error while fetching Jobs');
								});
							};

							self.rejectJobApplication = function(userID) {
						    var jobID =$rootScope.selectedJob.id;
								JobService.rejectJobApplication(userID,jobID												)
										.then(
												function(d) {
													self.job = d;
													alert("You have successfully rejected the job application of the " +
															"user : " +userID)
												},
												function(errResponse) {
													console
															.error('Error while rejecting Job application.');
												});
							};

							self.callForInterview = function(userID) {
								var jobID =$rootScope.selectedJob.id;	
								JobService
										.callForInterview(userID,
												jobID)
										.then(
												function(d) {
													self.job = d;
													alert("Application status changed as call for interview")
												},
												function(errResponse) {
													console
															.error('Error while changing the status "call for interview" ');
												});
							};
							self.selectUser = function(userID) {
								var jobID =$rootScope.selectedJob.id;		
								JobService
										.selectUser(userID, jobID)
										.then(
												function(d) {
													self.job = d;
													alert("Application status sta as selected")
												},
												function(errResponse) {
													console
															.error('Error while changing the status "select user" ');
												});
							};

							self.getAllJobs = function() {
								console.log('calling the method getAllJobs');
								JobService
										.getAllJobs()
										.then(
												function(d) {
													self.jobs = d;
												},
												function(errResponse) {
													console
															.error('Error while fetching All opend jobs');
												});
							};

							self.getAllJobs();

							self.submit = function() {
								{
									console.log('submit a new job', self.job);
									self.postAJob(self.job);
								}
								self.reset();
							};

							self.postAJob = function(job) {
								console.log('submit a new job', self.job);
								JobService.postAJob(job).then(function(d) {
								alert("You successfully posted the job")
								}, function(errResponse) {
									console.error('Error while posting job.');
								});
							};

							self.getJobDetails = getJobDetails

							function getJobDetails(jobID) {
								console.log('get Job details of the id', jobID);
								JobService
										.getJobDetails(jobID)
										.then(
												function(d) {
													self.job = d;
													
													$location
															.path('/view_job_details');
												},
												function(errResponse) {
													console
															.error('Error while fetching blog details');
												});
							}
							;

							self.reset = function() {
								console.log('resetting the Job');
								self.job = {
									id : '',
									title : '',
									description : '',
									dateTime : '',
									qualification : '',
									status : '',
									errorCode : '',
									errorMessage : ''
								};
								$scope.myForm.$setPristine(); // reset Form
							};

						} ]);