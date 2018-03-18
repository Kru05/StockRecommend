
// create the module and name it scotchApp
	var scotchApp = angular.module('scotchApp', ['ngRoute']);
	scotchApp.run(function ($rootScope) {
		$rootScope.loginid=0;
		$rootScope.logged_username='';
    });
	// configure our routes
	scotchApp.config(function($routeProvider) {
		$routeProvider
			
			// route for the login page
			.when('/', {
				templateUrl : 'pages/login.html',
				controller  : 'loginController'
			})
			// route for the logout page
			.when('/logout', {
				templateUrl : 'pages/logout.html',
				controller  : 'logoutController'
			})
			// route for the signup page
			.when('/signup', {
				templateUrl : 'pages/signup.html',
				controller  : 'signupController'
			})
			// route for the update page
			.when('/update', {
				templateUrl : 'pages/update.html',
				controller  : 'updateController'
			})
			// route for the home page
			.when('/home', {
				templateUrl : 'pages/home.html',
				controller  : 'mainController'
			})

			// route for the about page
			.when('/about', {
				templateUrl : 'pages/about.html',
				controller  : 'aboutController'
			})

			// route for the history page
			.when('/history', {
				templateUrl : 'pages/history.html',
				controller  : 'historyController'
			});
	});

	// create the controller and inject Angular's $scope
	scotchApp.controller('loginController', function($scope,$http,$rootScope) {
		
		$scope.message = 'Look! I am an about page.';
    	$http.get('http://192.168.43.17:8090/getCompanyList')
			.then(function(response) {
				$scope.content = response.data;
		});

		$scope.submit=function()
		{
			var id=$scope.uid;
			var p=$scope.pwd;
			$http.get('http://192.168.43.17:8090/login',{params:{username:id,password:p}})
			.then(function(response) {
			$scope.content = response.data;
			if($scope.content.userName == null)
			{
			alert("Wrong Username or Password");
			}
		else{
			alert("Login Successful");
			$rootScope.loginid = $scope.content.id;
			$rootScope.logged_username=$scope.content.userName;
			window.location.href = "http://192.168.43.17:8090/#/home";
			}
			
			});
			
		}
	});

	scotchApp.controller('logoutController', function($scope,$http,$rootScope) {
		$rootScope.loginid=0;
		setTimeout(myFunction, 3000);
		function myFunction() {
			window.location.href = "http://192.168.43.17:8090/#/";
		}
		
	});
	
	scotchApp.controller('signupController', function($scope,$http,$rootScope) {
		$scope.genders = ['Male','Female','Other'];
		$scope.risks = ['Low','Medium','High'];
		$scope.set=function()
    	{
				var uname=$scope.username;
				var pwd=$scope.password;
				var pwd2=$scope.password2;
				var gen=$scope.gender;
				var dateofbirth =$scope.DOB;
				var mailid=$scope.email;
				var r=$scope.risk;
				var dd = dateofbirth.getDate();
                var mm = dateofbirth.getMonth()+1; //January is 0!
                var yyyy = dateofbirth.getFullYear();

                if(dd<10) {
                    dd = '0'+dd
                } 

                if(mm<10) {
                    mm = '0'+mm
                } 

                var dt = yyyy + '/' + mm + '/' +dd ;
    			if(pwd==pwd2)
    				{
    				alert(uname+" "+pwd+" "+pwd2+" "+gen+" "+dt+" "+mailid+" "+r);
    				$http.get('http://192.168.43.17:8090/setProfile',{params:{username:uname,password:pwd,gender:gen,DOB:dt,email:mailid,risk:r}})
    				.then(function(response) {
    				$scope.saveStatus = response.data;
    				});
    				}
    			else
    				alert("not same");
		}
	});
	
	scotchApp.controller('updateController', function($scope,$http,$rootScope) {
		$scope.genders = ['Male','Female','Other'];
		$scope.risks = ['Low','Medium','High'];
		if($rootScope.loginid == 0)
			window.location.href = "http://192.168.43.17:8090/#/";
		$scope.set=function()
    	{
				var uid=$rootScope.loginid;
				var uname=$rootScope.logged_username;
				var pwd=$scope.password;
				var pwd2=$scope.password2;
				var gen=$scope.gender;
				var dateofbirth =$scope.DOB;
				var mailid=$scope.email;
				var r=$scope.risk;
				var dd = dateofbirth.getDate();
                var mm = dateofbirth.getMonth()+1; //January is 0!
                var yyyy = dateofbirth.getFullYear();

                if(dd<10) {
                    dd = '0'+dd
                } 

                if(mm<10) {
                    mm = '0'+mm
                } 

                var dt = yyyy + '/' + mm + '/' +dd ;
    			if(pwd==pwd2)
    				{
    				alert(uname+" "+pwd+" "+pwd2+" "+gen+" "+dt+" "+mailid+" "+r);
    				$http.get('http://192.168.43.17:8090/updateProfile',{params:{userid:uid,username:uname,password:pwd,gender:gen,DOB:dt,email:mailid,risk:r}})
    				.then(function(response) {
    				$scope.saveStatus = response.data;
    				});
    				}
    			else
    				alert("not same");
		}
		
	});
	
	scotchApp.controller('mainController', function($scope,$http,$rootScope) {
		// create a message to display in our view
		if($rootScope.loginid == 0)
			window.location.href = "http://192.168.43.17:8090/#/";
		$scope.levels = ['Basic','Intermediate','Advance'];
		$scope.message = $rootScope.logged_username;
		$scope.recommend=function()
    	{
				var id=$rootScope.loginid;
				var lvl=$scope.level;
				var tot=$scope.total;
    			$http.get('http://192.168.43.17:8090/knapsack',{params:{userid:id,level:lvl,total:tot}})
				.then(function(response) {
				$scope.content = response.data;
		});};
		
		$scope.openGraph = function(company){
			$scope.code=company.name;
			alert($scope.code);
			
		};
		
		$scope.save = function (content) {
          
            angular.forEach(content, function (value, key) {
                if (content[key].selected == content[key].name) {
                   
                    var id=$rootScope.loginid;
                    var cd=content[key].code;
                    var today = new Date();
                    var dd = today.getDate();
                    var mm = today.getMonth()+1; //January is 0!
                    var yyyy = today.getFullYear();

                    if(dd<10) {
                        dd = '0'+dd
                    } 

                    if(mm<10) {
                        mm = '0'+mm
                    } 

                    var dt = yyyy + '/' + mm + '/' +dd ;
                    var cl=content[key].close;
             
                    $http.get('http://192.168.43.17:8090/save',{params:{userid:id,code:cd,date:dt,close:cl}})
    				.then(function(response) {
    				$scope.saveStatus = response.data;
    				});
                }
            });

            
        }
		
	});

	scotchApp.controller('aboutController', function($scope,$http,$rootScope) {
		$scope.message = 'Look! I am an about page.';
		
	});

	scotchApp.controller('historyController', function($scope,$http,$rootScope) {
		if($rootScope.loginid == 0)
			window.location.href = "http://192.168.43.17:8090/#/";
		$scope.message = 'history ';
		
				var user=$rootScope.loginid;
    			$http.get('http://192.168.43.17:8090/getSavedCompanyForUser',{params:{id:user}})
				.then(function(response) {
				$scope.content = response.data;
				});
    	
		
		$scope.del = function (content) {
	         
            angular.forEach(content, function (value, key) {
                if (content[key].selected == content[key].name) {
                   
                    var id=$rootScope.loginid;
                    var cd=content[key].code;
                    var dt=content[key].date;
                    var cl=content[key].close;
               
                    $http.get('http://192.168.43.17:8090/delete',{params:{userid:id,code:cd,date:dt,close:cl}})
    				.then(function(response) {
    				$scope.saveStatus = response.data;
    				});
                }
            });
        }
		
	});