(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('MediaDetailController', MediaDetailController);

    MediaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Media', 'Album'];

    function MediaDetailController($scope, $rootScope, $stateParams, previousState, entity, Media, Album) {
        var vm = this;

        vm.media = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:mediaUpdate', function(event, result) {
            vm.media = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
