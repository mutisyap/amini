(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationUserDetailController', OrganizationUserDetailController);

    OrganizationUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrganizationUser', 'Organization'];

    function OrganizationUserDetailController($scope, $rootScope, $stateParams, previousState, entity, OrganizationUser, Organization) {
        var vm = this;

        vm.organizationUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:organizationUserUpdate', function(event, result) {
            vm.organizationUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
