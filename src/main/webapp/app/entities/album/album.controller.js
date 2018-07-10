(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('AlbumController', AlbumController);

    AlbumController.$inject = ['Album'];

    function AlbumController(Album) {

        var vm = this;

        vm.albums = [];

        loadAll();

        function loadAll() {
            Album.query(function(result) {
                vm.albums = result;
                vm.searchQuery = null;
            });
        }
    }
})();
