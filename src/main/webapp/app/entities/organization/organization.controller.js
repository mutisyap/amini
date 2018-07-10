(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationController', OrganizationController);

    OrganizationController.$inject = ['Organization'];

    function OrganizationController(Organization) {

        var vm = this;

        vm.organizations = [];

        loadAll();

        function loadAll() {
            Organization.query(function(result) {
                vm.organizations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
