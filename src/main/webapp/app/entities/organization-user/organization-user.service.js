(function() {
    'use strict';
    angular
        .module('aminiApp')
        .factory('OrganizationUser', OrganizationUser);

    OrganizationUser.$inject = ['$resource'];

    function OrganizationUser ($resource) {
        var resourceUrl =  'api/organization-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
