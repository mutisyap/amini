(function() {
    'use strict';

    angular
        .module('aminiApp')
        .controller('OrganizationUserController', OrganizationUserController);

    OrganizationUserController.$inject = ['OrganizationUser'];

    function OrganizationUserController(OrganizationUser) {

        var vm = this;

        vm.organizationUsers = [];

        loadAll();

        function loadAll() {
            OrganizationUser.query(function(result) {
                vm.organizationUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
