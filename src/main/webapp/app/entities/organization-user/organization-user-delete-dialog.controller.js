(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationUserDeleteController',OrganizationUserDeleteController);

    OrganizationUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrganizationUser'];

    function OrganizationUserDeleteController($uibModalInstance, entity, OrganizationUser) {
        var vm = this;

        vm.organizationUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrganizationUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
