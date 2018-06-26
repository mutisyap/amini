(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('MediaDialogController', MediaDialogController);

    MediaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Media', 'Album'];

    function MediaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Media, Album) {
        var vm = this;

        vm.media = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.albums = Album.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.media.id !== null) {
                Media.update(vm.media, onSaveSuccess, onSaveError);
            } else {
                Media.save(vm.media, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aminiApp:mediaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdOn = false;
        vm.datePickerOpenStatus.lastUpdatedOn = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
