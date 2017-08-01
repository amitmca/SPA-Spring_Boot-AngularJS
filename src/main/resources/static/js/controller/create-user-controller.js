/* CREATE USER CONTROLLER */
app.controller('createuser', function ($scope, $http, $cookies, $location) {
	
	// set the CSRF token here
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    // add temp authority
    $http.defaults.headers.common.Authorization = 'Basic ' +
    // base64 encode credentials, need to make specific account for this in production env
    btoa('create_user' + ':' + 'create_user');
    
	var self = this;
	$scope.mainHeader = 'Interested in sharing? Create an account today!';
	var saved;
	self.createUser = function() {
		
		var url = "/add";
		/* request this in rest controller */
		var userData = {'username' : self.credentials.username, 'password' : self.credentials.password, 
				'firstName' : self.credentials.firstName, 'lastName' : self.credentials.lastName,
				'email' : self.credentials.email};
		
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
