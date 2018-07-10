(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationUserDialogController', OrganizationUserDialogController);

    OrganizationUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrganizationUser', 'Organization'];

    function OrganizationUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrganizationUser, Organization) {
        var vm = this;

        vm.organizationUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.organizations = Organization.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.organizationUser.id !== null) {
                OrganizationUser.update(vm.organizationUser, onSaveSuccess, onSaveError);
            } else {
                OrganizationUser.save(vm.organizationUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aminiApp:organizationUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
