(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('GeneralEventDetailController', GeneralEventDetailController);

    GeneralEventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GeneralEvent', 'Organization'];

    function GeneralEventDetailController($scope, $rootScope, $stateParams, previousState, entity, GeneralEvent, Organization) {
        var vm = this;

        vm.generalEvent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:generalEventUpdate', function(event, result) {
            vm.generalEvent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
