var app = angular.module('hello', [ 'ngRoute', 'ngCookies', 'ngResource']);

/**
 * To add contents from rest controller to a page with a redirect 
 * in angular, use $http.get or post possibly when the template is loaded.
 * 
 * for example if I want to have a post load on a new page, maybe I should 
 * have the form actually submit to a link and when the link loads, 
 * that will call method retrieving the promise data.
 * 
 * I would have to send along the parameters though which may be hard with 
 * super long posts.
 * 
 */
app.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
      templateUrl : 'home.html',
      controller : 'home',
      controllerAs: 'controller'
    }).when('/login', {
      templateUrl : 'login.html', /* had to put login same as index for some reason, need to investigate */
      controller : 'navigation',
      controllerAs: 'controller'
    }).when('/create-user', {
    	templateUrl : 'create-user.html',
    	controller : 'createuser',
    	controllerAs : 'createuser'
    }).when('/admin', {
    	templateUrl : 'pages/admin/admin.html',
    	controller : 'adminCtrl',
    	controllerAs : 'admin'
    }).when('/new-blog-post', {
    	templateUrl : 'pages/admin/new-blog-post.html',
    	controller : 'newBlog',
    	controllerAs : 'newBlog'
    }).when('/list-all', {
    	templateUrl : 'pages/admin/list-all-blog.html',
    	controller : 'listBlog',
    	controllerAs : 'listBlog'
    }).when('/view-post', {
    	templateUrl : 'pages/admin/view-blog.html',
    	controller : 'viewBlog',
    	controllerAs : 'viewBlog'
    }).when('/saved', {
    	templateUrl : 'pages/admin/saved.html',
    	controller : 'saved',
    	controllerAs : 'saved'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  });
  
/* HOME CONTROLLER */
app.controller('home', function($http, $location, $cookies, $scope) {
	$http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    var self = this;
    
    /* this gets 5 list of blog posts for home */
    $http.get('/resource/').then(function(response) {
    	$scope.blogListHeader = '5 Most recent blog posts!';
        self.data = response.data;
    });
    
	self.viewPost = function() {
		var id = document.getElementById('id').value;
		var url = "/view-blog/" + id;
		$http.get(url).then(function(response) {
			// redirect to html chunk for viewing specified post
			$location.path("/view-post/");
			// window.alert(response.data.blog.title);
			// having trouble accessing this in html
			$scope.blog = response.data.blog;
		}); 
	}
    
  });
	
/* NAVIGATION CONTROLLER */
app.controller('navigation',

	  function($rootScope, $http, $location) {
	
	  var self = this
	
	  var authenticate = function(credentials, callback) {
	
	    var headers = credentials ? {authorization : "Basic "
	        + btoa(credentials.username + ":" + credentials.password)
	    } : {};
	
	    $http.get('user', {headers : headers}).then(function(response) {
	      if (response.data.name) {
	        $rootScope.authenticated = true;
	      } else {
	        $rootScope.authenticated = false;
	      }
	      callback && callback();
	    }, function() {
	      $rootScope.authenticated = false;
	      callback && callback();
	    });
	
	  }
	
	  authenticate();
	  self.credentials = {};
	  self.login = function() {
	      authenticate(self.credentials, function() {
	        if ($rootScope.authenticated) {
	          $location.path("/");
	          self.error = false;
	        } else {
	          $location.path("/login");
	          self.error = true;
	        }
	      });
	  	};
	  	
	  self.logout = function() {
		  $http.post('logout', {}).finally(function() {
		    $rootScope.authenticated = false;
		    $location.path("/");
		  });
		};
	});

/* CREATE USER CONTROLLER */
app.controller('createuser', function ($scope, $http, $cookies, $location) {
	// set the CSRF token here
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    // add temp authority
    $http.defaults.headers.common.Authorization = 'Basic ' +
    // base64 encode credentials, need to make specific account for this in production env
    btoa('blue' + ':' + 'blue2525989');
	var self = this;
	var saved;
	self.createUser = function() {
		
		var url = "/add";
		var userData = {'username' : self.credentials.username, 'password' : self.credentials.password};
		$http.post(url, userData).then(function (response) {
			// take away temp authority
			$http.defaults.headers.common.Authorization = 'Basic ';
			// grab saved parameter
			window.alert(response.data.saved);
			$location.path("/login");
		}, function (response) {
			$location.path("/create-user");
		});		
	}
});

/* CONTROLLER */
app.controller('adminCtrl', function ($rootScope, $http, $cookies, $location, $resource) {
	
});

/* CONTROLLER */
app.controller('newBlog',  function ($http, $cookies, $location) {
	// set the CSRF token here
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    
	var self = this;  
	self.postBlog = function() {
		
		var url = "/post-blog";
		// create model to send to spring controller
		var blogPart = {'topic' : self.blog.topic, 'title' : self.blog.title, 'content' : self.blog.content};
		$http.post(url, blogPart
		).then(function(response) {
			// grab saved parameter
			window.alert(response.data.saved);
			$location.path("/");
		}, function(response) {
			$location.path("/new-blog-post");
		});
		
	}	
});

/* CONTROLLER */
app.controller('listBlog', function ($rootScope, $http, $cookies, $location, $scope) {
	$http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
	
	var self = this;
	/* when page is loaded */
	var url = "/list-all-blog-post/";
	$http.get(url).then(function(response) {
		$scope.blogListHeader = 'All blog posts!';
		self.data = response.data;
	});
	
	self.viewPost = function() {
		var url = "/view-blog/" + self.id;
		$http.post(url).then(function(response) {

			window.alert(response.data.blog.title);
			//$location.path("/view-post");
			self.data = response.data;
		}); 
	}
		
});

/* CONTROLLER */
app.controller('saved', function ($rootScope, $http, $cookies, $location) {
	
});

/* CONTROLLER */
app.controller('viewBlog', function ($rootScope, $http, $cookies, $location) {
	
});





