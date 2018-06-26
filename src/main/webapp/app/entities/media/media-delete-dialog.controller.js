(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('MediaDeleteController',MediaDeleteController);

    MediaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Media'];

    function MediaDeleteController($uibModalInstance, entity, Media) {
        var vm = this;

        vm.media = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Media.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
