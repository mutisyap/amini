(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('GeneralEventDialogController', GeneralEventDialogController);

    GeneralEventDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GeneralEvent', 'Organization'];

    function GeneralEventDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GeneralEvent, Organization) {
        var vm = this;

        vm.generalEvent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.generalEvent.id !== null) {
                GeneralEvent.update(vm.generalEvent, onSaveSuccess, onSaveError);
            } else {
                GeneralEvent.save(vm.generalEvent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aminiApp:generalEventUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.lastUpdatedOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
