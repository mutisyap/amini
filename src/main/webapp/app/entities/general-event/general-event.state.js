(function() {
    'use strict';

    angular
        .module('aminiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('general-event', {
            parent: 'entity',
            url: '/general-event',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneralEvents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/general-event/general-events.html',
                    controller: 'GeneralEventController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('general-event-detail', {
            parent: 'general-event',
            url: '/general-event/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneralEvent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/general-event/general-event-detail.html',
                    controller: 'GeneralEventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GeneralEvent', function($stateParams, GeneralEvent) {
                    return GeneralEvent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'general-event',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('general-event-detail.edit', {
            parent: 'general-event-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-event/general-event-dialog.html',
                    controller: 'GeneralEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneralEvent', function(GeneralEvent) {
                            return GeneralEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('general-event.new', {
            parent: 'general-event',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-event/general-event-dialog.html',
                    controller: 'GeneralEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                type: null,
                                dayOfWeek: null,
                                startDate: null,
                                description: null,
                                ageLimit: null,
                                host: null,
                                venue: null,
                                latitude: null,
                                longitude: null,
                                cycle: null,
                                time: null,
                                language: null,
                                createdBy: null,
                                createdOn: null,
                                lastUpdatedBy: null,
                                lastUpdatedOn: null,
                                charges: null,
                                contact1: null,
                                contact1Value: null,
                                contact2: null,
                                contact2Value: null,
                                contact3: null,
                                contact3Value: null,
                                charged: null,
                                contentStatus: null,
                                uuid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('general-event', null, { reload: 'general-event' });
                }, function() {
                    $state.go('general-event');
                });
            }]
        })
        .state('general-event.edit', {
            parent: 'general-event',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-event/general-event-dialog.html',
                    controller: 'GeneralEventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneralEvent', function(GeneralEvent) {
                            return GeneralEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('general-event', null, { reload: 'general-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('general-event.delete', {
            parent: 'general-event',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-event/general-event-delete-dialog.html',
                    controller: 'GeneralEventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GeneralEvent', function(GeneralEvent) {
                            return GeneralEvent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('general-event', null, { reload: 'general-event' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
