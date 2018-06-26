(function() {
    'use strict';

    angular
        .module('aminiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('media', {
            parent: 'entity',
            url: '/media?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Media'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media.html',
                    controller: 'MediaController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('media-detail', {
            parent: 'media',
            url: '/media/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Media'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/media/media-detail.html',
                    controller: 'MediaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Media', function($stateParams, Media) {
                    return Media.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'media',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('media-detail.edit', {
            parent: 'media-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.new', {
            parent: 'media',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                caption: null,
                                createdBy: null,
                                createdOn: null,
                                lastUpdatedBy: null,
                                lastUpdatedOn: null,
                                filename: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('media');
                });
            }]
        })
        .state('media.edit', {
            parent: 'media',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-dialog.html',
                    controller: 'MediaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('media.delete', {
            parent: 'media',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/media/media-delete-dialog.html',
                    controller: 'MediaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Media', function(Media) {
                            return Media.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('media', null, { reload: 'media' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
