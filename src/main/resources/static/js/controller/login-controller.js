/* LOGIN CONTROLLER */
app.controller('login',

	  function($rootScope, $http, $location, $scope) {
	
	  var self = this
	  $scope.mainHeader = 'Share with the community by loggin in!';
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
	
	  self.credentials = {};
	  self.login = function() {
		  authenticate();
	      authenticate(self.credentials, function() {
	        if ($rootScope.authenticated) {
	          $location.path("/admin");
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
