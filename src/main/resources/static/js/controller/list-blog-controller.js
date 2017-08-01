/* CONTROLLER */
app.controller('listBlog', function ($rootScope, $http, $cookies, $location, $scope) {
	$http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
	
	var self = this;
	/* when page is loaded */
	var url = "/list-all-blog-post/";
	$http.get(url).then(function(response) {
		$scope.mainHeader = 'Check out the archive of blog posts.';
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