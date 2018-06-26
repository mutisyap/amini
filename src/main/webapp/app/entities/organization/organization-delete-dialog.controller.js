(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationDeleteController',OrganizationDeleteController);

    OrganizationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Organization'];

    function OrganizationDeleteController($uibModalInstance, entity, Organization) {
        var vm = this;

        vm.organization = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Organization.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
