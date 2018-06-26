(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('AlbumDetailController', AlbumDetailController);

    AlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Album'];

    function AlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, Album) {
        var vm = this;

        vm.album = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('aminiApp:albumUpdate', function(event, result) {
            vm.album = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
