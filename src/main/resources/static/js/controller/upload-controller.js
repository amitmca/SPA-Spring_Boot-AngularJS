app.controller('upload', function($scope, $http, $rootScope, $cookies, $location) {
	
	$scope.mainHeader = "Select an image file to use for your profile picture.";
			
	var self = this;
	
	self.uploadFile = function() {
		var formData = new FormData();
		var file = $scope.fileModel;
		var url = "/upload/";
		formData.append('file', file);
		
		$http.post(url, file, {
			headers : {
				'Content-Type' : undefined
			}
		}).then(function (response) {
			// grab saved parameter
			window.alert(response.data.saved);
			$location.path("/admin");
		}, function (response) {
			$location.path("/");
		});
	}
});