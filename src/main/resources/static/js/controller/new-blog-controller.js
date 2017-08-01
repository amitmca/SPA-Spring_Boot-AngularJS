/* NEW BLOG CONTROLLER */
app.controller('newBlog',  function ($http, $cookies, $location, $scope) {
	$scope.mainHeader = 'Have some interesting knowledge? Post a blog!';
	var self = this;  
	self.postBlog = function() {
		// set the CSRF token here
	    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
		
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