(function() {
    'use strict';
    angular
        .module('aminiApp')
        .factory('Album', Album);

    Album.$inject = ['$resource', 'DateUtils'];

    function Album ($resource, DateUtils) {
        var resourceUrl =  'api/albums/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                        data.lastUpdatedOn = DateUtils.convertDateTimeFromServer(data.lastUpdatedOn);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
