(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('GeneralEventDeleteController',GeneralEventDeleteController);

    GeneralEventDeleteController.$inject = ['$uibModalInstance', 'entity', 'GeneralEvent'];

    function GeneralEventDeleteController($uibModalInstance, entity, GeneralEvent) {
        var vm = this;

        vm.generalEvent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GeneralEvent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
