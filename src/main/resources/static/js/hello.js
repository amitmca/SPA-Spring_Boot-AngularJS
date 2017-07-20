var app = angular.module('hello', [ 'ngRoute', 'ngCookies']);
  
app.config(function($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
      templateUrl : 'home.html',
      controller : 'home',
      controllerAs: 'controller'
    }).when('/login', {
      templateUrl : 'login.html',
      controller : 'navigation',
      controllerAs: 'controller'
    }).when('/create-user', {
    	templateUrl : 'create-user.html',
    	controller : 'create-user',
    	controllerAs : 'createuser'
    }).otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  });
  
app.controller('home', function($http) {
    var self = this;
    $http.get('/resource/').then(function(response) {
      self.greeting = response.data;
    })
  });
	
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

app.controller('create-user', function ($rootScope, $http, $cookies, $location) {
	// set the CSRF token here
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
	var self = this;
	self.createUser = function() {
		var url = '/add';
		var data = {'username' : self.credentials.username, 'password' : self.credentials.password};
		$http.post(url, data).then(function (response) {
			$location.path("/");
		}, function (response) {
			$location.path("/create-user");
		});
	}
});




