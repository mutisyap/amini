(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('AlbumDialogController', AlbumDialogController);

    AlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Album'];

    function AlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Album) {
        var vm = this;

        vm.album = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.album.id !== null) {
                Album.update(vm.album, onSaveSuccess, onSaveError);
            } else {
                Album.save(vm.album, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('aminiApp:albumUpdate', result);
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
