var app = angular.module('myApp', [ 'ngRoute','ngCookies']);
app.config(function($routeProvider) {
	$routeProvider

	.when('/', {
		templateUrl : 'c_home/home.html',
		controller : 'HomeController'
	})

	/*.when('/event', {
		templateUrl : 'c_upload/upload.html',
		controller : 'FileUploadController'
	})
*/
	.when('/about', {
		templateUrl : 'c_about/about.html',
		controller : 'AboutController'
	})

	.when('/login', {
		templateUrl : 'c_user/login.html',
		controller : 'UserController'
	})

	.when('/register', {
		templateUrl : 'c_user/register.html',
		controller : 'UserController'
	})
	
	.when('/myProfile', {
		templateUrl : 'c_user/my_profile.html',
		controller : 'UserController'
	})
	
	.when('/manage_users', {
		templateUrl : 'c_admin/manage_users.html',
		controller : 'UserController'
	})


	/**
	 * Blog related mapping
	 */

	.when('/create_blog', {
		templateUrl : 'c_blog/create_blog.html',
		controller : 'BlogController'
	})

	.when('/list_blog', {
		templateUrl : 'c_blog/list_blog.html',
		controller : 'BlogController'
	})

	.when('/view_blog', {
		templateUrl : 'c_blog/view_blog.html',
		controller : 'BlogController'
	})

	/**
	 * Friend related mapping
	 */

	.when('/add_friend', {
		templateUrl : 'c_friend/add_friend.html',
		controller : 'FriendController'
	})

	.when('/search_friend', {
		templateUrl : 'c_friend/search_friend.html',
		controller : 'FriendController'
	})

	.when('/view_friend', {
		templateUrl : 'c_friend/view_friend.html',
		controller : 'FriendController'
	})
	
	.when('/viewFriendRequest', {
		templateUrl : 'c_friend/view_friend_request.html',
		controller : 'FriendController'
	})
	
	.when('/chat', {
		templateUrl : 'c_chat/chat.html',
		controller : 'ChatController'
	})
	
	.when('/chat_forum', {
		templateUrl : 'c_chat_forum/chat_forum.html',
		controller : 'ChatForumController'
	})



	/**
	 * Job related mappings
	 */
	.when('/job', {
		templateUrl : 'c_job/job.html',
		controller : 'JobController'
	})

	.when('/search_job', {
		templateUrl : 'c_job/search_job.html',
		controller : 'JobController'
	})

	.when('/view_applied_jobs', {
		templateUrl : 'c_job/view_applied_jobs.html',
		controller : 'JobController'
	})
	
	.when('/view_job_details', {
		templateUrl : 'c_job/view_job_details.html',
		controller : 'JobController'
	})

	.when('/post_job', {
		templateUrl : 'c_job/post_job.html',
		controller : 'JobController'
	})

	.otherwise({
		redirectTo : '/'
	});
});

app.run( function ($rootScope, $location,$cookieStore, $http) {

	 $rootScope.$on('$locationChangeStart', function (event, next, current) {
		 console.log("$locationChangeStart")
		 //http://localhost:8080/Collaboration/addjob
	        // redirect to login page if not logged in and trying to access a restricted page
	     
		 var userPages = ['/myProfile','/create_blog','/add_friend','/search_friend','/view_friend', '/viewFriendRequest','/chat']
		 var adminPages = ["/post_job","/manage_users"]
		 
		 var currentPage = $location.path()
		/* var isUserPage = $.inArray(currentPage, userPages)==1; 
		 var isAdminPage = $.inArray(currentPage, adminPages)==1;*/
		 var isUserPage = $.inArray(currentPage, userPages) 
		 var isAdminPage = $.inArray(currentPage, adminPages)
		 
		 var isLoggedIn = $rootScope.currentUser.id;
	        
	     console.log("isLoggedIn:" +isLoggedIn)
	     console.log("isUserPage:" +isUserPage)
	     console.log("isAdminPage:" +isAdminPage)
	        
	        if(!isLoggedIn)
	        	{
	        	/*if (isUserPage  || isAdminPage )*/
	        	 if (isUserPage === 0 || isAdminPage === 0) {
		        	  console.log("Navigating to login page:")
		        	  alert("You need to loggin to do this operation")

						            $location.path('/');
		                }
	        	}
	        
			 else //logged in
	        	{
	        	
				 var role = $rootScope.currentUser.role;
				 
				 if(isAdminPage && role!='admin' )
					 {
					 
					  alert("You can not do this operation as you are logged as : " + role )
					   $location.path('/login');
					 
					 }
				     
	        	
	        	}
	        
	 }
	       );
	 
	 
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
    }

});



   
   

