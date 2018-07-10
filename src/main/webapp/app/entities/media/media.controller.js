(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('MediaController', MediaController);

    MediaController.$inject = ['Media'];

    function MediaController(Media) {

        var vm = this;

        vm.media = [];

        loadAll();

        function loadAll() {
            Media.query(function(result) {
                vm.media = result;
                vm.searchQuery = null;
            });
        }
    }
})();
