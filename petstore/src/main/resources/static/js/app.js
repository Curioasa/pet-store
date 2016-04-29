angular.module('petStore', ['ui.router', 'ngTagsInput'])
    .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'home.html',
                controller: 'home'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'login.html',
                controller: 'navigation'
            });

    })
    .controller('home', function ($scope, $location, $anchorScroll, $http, $timeout) {
        var scrollToForm = function () {
            var old = $location.hash();
            $location.hash('petSection');
            $anchorScroll();
            $location.hash(old);
        };
        $scope.showForm = function(editable) {
            $scope.currentPet = undefined;
            $scope.formVisible = false;
            $scope.editable = editable;
            if (!editable) {
                $http.get('pet/' + $scope.petId).success(function (data) {
                    $scope.formVisible = true;
                    $scope.currentPet = data;
                    $timeout(scrollToForm, 50);
                    var pictureUrl = $scope.currentPet.pictureUrls[0];
                    if (pictureUrl !== undefined) {
                        var prefix = 'http://';
                        var start = pictureUrl.indexOf(prefix) + prefix.length;
                        $scope.currentPet.currentUrl = pictureUrl.indexOf(prefix) > -1 ? pictureUrl.substring(start, pictureUrl.length) : pictureUrl;
                    }
                    $scope.petNotFound = false;
                }).error(function () {
                    $scope.petNotFound = true;
                });
            } else {
                $scope.formVisible = true;
                $scope.currentPet = {status: 'AVAILABLE'};
                $timeout(scrollToForm, 50);
            }
        };
    })
    .controller('navigation', function ($rootScope, $scope, $http, $state) {
        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":" + credentials.password)
            } : {};

            $rootScope.authorization = {};

            $http.get('user', {headers: headers}).success(function (data) {
                if (data.username) {
                    if (data.roles) {
                        $rootScope.authorization.read = data.roles.indexOf('ROLE_USER') > -1;
                        $rootScope.authorization.write = data.roles.indexOf('ROLE_ADMIN') > -1;
                    }
                    $rootScope.authenticated = true;
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback();
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        };

        authenticate();

        $scope.$state = $state;

        $scope.credentials = {};
        $scope.login = function () {
            authenticate($scope.credentials, function () {
                if ($rootScope.authenticated) {
                    $state.go('home');
                    $scope.error = false;
                } else {
                    $state.go('login');
                    $scope.error = true;
                }
            });
        };
        $scope.logout = function () {
            $http.post('logout', {}).success(function () {
                $rootScope.authenticated = false;
                $state.go('home');
            }).error(function (data) {
                $rootScope.authenticated = false;
            });
        };
    });