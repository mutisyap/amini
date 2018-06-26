(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('EventDetailController', EventDetailController);

    EventDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Event', 'GeneralEvent'];

    function EventDetailController($scope, $rootScope, $stateParams, previousState, entity, Event, GeneralEvent) {
        var vm = this;

        vm.event = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:eventUpdate', function(event, result) {
            vm.event = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
