(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('GeneralEventController', GeneralEventController);

    GeneralEventController.$inject = ['GeneralEvent'];

    function GeneralEventController(GeneralEvent) {

        var vm = this;

        vm.generalEvents = [];

        loadAll();

        function loadAll() {
            GeneralEvent.query(function(result) {
                vm.generalEvents = result;
                vm.searchQuery = null;
            });
        }
    }
})();
