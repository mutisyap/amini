(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationDetailController', OrganizationDetailController);

    OrganizationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Organization'];

    function OrganizationDetailController($scope, $rootScope, $stateParams, previousState, entity, Organization) {
        var vm = this;

        vm.organization = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:organizationUpdate', function(event, result) {
            vm.organization = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
