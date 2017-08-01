var app = angular.module('hello', [ 'ngRoute', 'ngCookies', 'ngResource', 'file-model']);

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
app.config(function($routeProvider, $httpProvider, $sceProvider) {

	$sceProvider.enabled(false);
	
    $routeProvider.when('/', {
        templateUrl : 'pages/home.html',
        controller : 'home',
        controllerAs: 'controller',
        requireLogin : false,
        title : 'Home page'
    }).when('/login', {
        templateUrl : 'pages/login.html',
        controller : 'login',
        controllerAs: 'controller',
        requireLogin : false,
        title : 'Login'
    }).when('/create-user', {
    	templateUrl : 'pages/create-user.html',
    	controller : 'createuser',
    	controllerAs : 'createuser',
        requireLogin : false,
        title : 'Create a user'
    }).when('/admin', {
    	templateUrl : 'pages/admin/admin.html',
    	controller : 'adminCtrl',
    	controllerAs : 'admin',
        requireLogin : true,
        title : 'User area'
    }).when('/new-blog-post', {
    	templateUrl : 'pages/admin/new-blog-post.html',
    	controller : 'newBlog',
    	controllerAs : 'newBlog',
        requireLogin : true,
        title : 'New blog post'
    }).when('/list-all', {
    	templateUrl : 'pages/admin/list-all-blog.html',
    	controller : 'listBlog',
    	controllerAs : 'listBlog',
        requireLogin : true,
        title : 'List all blog post'
    }).when('/view-post', {
    	templateUrl : 'pages/admin/view-blog.html',
    	controller : 'viewBlog',
    	controllerAs : 'viewBlog',
        requireLogin : true,
        title : 'Blog post'
    }).when('/saved', {
    	templateUrl : 'pages/admin/saved.html',
    	controller : 'saved',
    	controllerAs : 'saved',
        requireLogin : false,
        title : 'Saved'
    }).when('/upload-file', {
    	templateUrl : 'pages/admin/upload-file.html',
    	controller : 'upload',
    	controllerAs : 'upload',
    	requireLogin : false
    }).otherwise('/');

    /* add this for spring framework */
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

  });




