(function() {
    'use strict';
    angular
        .module('aminiApp')
        .factory('GeneralEvent', GeneralEvent);

    GeneralEvent.$inject = ['$resource', 'DateUtils'];

    function GeneralEvent ($resource, DateUtils) {
        var resourceUrl =  'api/general-events/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertLocalDateFromServer(data.startDate);
                        data.createdOn = DateUtils.convertDateTimeFromServer(data.createdOn);
                        data.lastUpdatedOn = DateUtils.convertDateTimeFromServer(data.lastUpdatedOn);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.startDate = DateUtils.convertLocalDateToServer(copy.startDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
