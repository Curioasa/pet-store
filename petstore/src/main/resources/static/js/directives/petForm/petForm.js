var inject = ['$rootScope'];
var petForm = function () {
    var restrict = 'E';
    var scope = {
        edit: '=',
        writeAccess: '=',
        deleteAccess: '=',
        currentPet: '='
    };

    var controller = function ($scope, $http, $rootScope, $location, $anchorScroll) {
        $scope.currentPet = $scope.currentPet !== undefined ? $scope.currentPet : {};
        $scope.currentPet.error = undefined;

        var handleCalculatedFields = function () {
            if ($scope.currentPet.tags !== undefined) {
                $scope.currentPet.tags = $scope.currentPet.tags.map(function (tag) {
                    return tag.text;
                })
            } else {
                $scope.currentPet.tags = [];
            }
            if ($scope.currentPet.pictureUrls !== undefined) {
                $scope.currentPet.pictureUrls = [$scope.currentPet.pictureUrls];
            } else {
                $scope.currentPet.pictureUrls = [];
            }
        };

        $scope.savePet = function () {
            handleCalculatedFields();
            $http.post('pet', $scope.currentPet).success(function (data) {
                $scope.currentPet = data;
                $scope.currentPet.error = false;
            }).error(function () {
                $scope.currentPet.error = true;
            });
        };

        $scope.deletePet = function () {
            $http.delete('pet/' + $scope.currentPet.id).success(function (data) {
                $scope.$parent.$parent.formVisible = false;
                $location.hash('main-section');
                $anchorScroll();
                $scope.currentPet.error = false;
            }).error(function () {
                $scope.currentPet.error = true;
            });
        };

        $scope.resetForm = function () {
            $scope.currentPet = {status: 'AVAILABLE'};
        };
    };


    return {
        restrict: restrict,
        scope: scope,
        templateUrl: '/js/directives/petForm/petForm.html',
        controller: controller
    }
};

petForm.$inject = inject;
angular.module('petStore').directive('petForm', petForm);