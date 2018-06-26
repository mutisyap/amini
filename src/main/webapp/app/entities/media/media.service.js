(function() {
    'use strict';
    angular
        .module('aminiApp')
        .factory('Media', Media);

    Media.$inject = ['$resource', 'DateUtils'];

    function Media ($resource, DateUtils) {
        var resourceUrl =  'api/media/:id';

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
