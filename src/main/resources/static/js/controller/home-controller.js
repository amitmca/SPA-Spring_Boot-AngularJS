
/* HOME CONTROLLER */
app.controller('home', function($http, $location, $cookies, $scope) {
	$http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
    var self = this;
    $scope.mainHeader = 'The latest and greastest blog posts!';
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

