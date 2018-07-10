(function() {
    'use strict';

    angular
        .module('aminiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('organization-user', {
            parent: 'entity',
            url: '/organization-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrganizationUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organization-user/organization-users.html',
                    controller: 'OrganizationUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('organization-user-detail', {
            parent: 'organization-user',
            url: '/organization-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrganizationUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organization-user/organization-user-detail.html',
                    controller: 'OrganizationUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrganizationUser', function($stateParams, OrganizationUser) {
                    return OrganizationUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'organization-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('organization-user-detail.edit', {
            parent: 'organization-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organization-user/organization-user-dialog.html',
                    controller: 'OrganizationUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrganizationUser', function(OrganizationUser) {
                            return OrganizationUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organization-user.new', {
            parent: 'organization-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organization-user/organization-user-dialog.html',
                    controller: 'OrganizationUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                username: null,
                                uuid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('organization-user', null, { reload: 'organization-user' });
                }, function() {
                    $state.go('organization-user');
                });
            }]
        })
        .state('organization-user.edit', {
            parent: 'organization-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organization-user/organization-user-dialog.html',
                    controller: 'OrganizationUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrganizationUser', function(OrganizationUser) {
                            return OrganizationUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organization-user', null, { reload: 'organization-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organization-user.delete', {
            parent: 'organization-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organization-user/organization-user-delete-dialog.html',
                    controller: 'OrganizationUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrganizationUser', function(OrganizationUser) {
                            return OrganizationUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organization-user', null, { reload: 'organization-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
