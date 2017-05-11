/**
 * Created by Dzb on 2017/3/6.
 */
angular.module('ws.stateConfig').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('main.tax', {
        url: '/tax',
        views: {
            'bottom@main': {
                templateUrl: 'tpls/tax/layout.html'
            },
            'left@main.tax': {
                templateUrl: 'tpls/tax/menu.html'
            },
            'right@main.tax': {
                templateUrl: 'tpls/tax/welcome.html'
            }
        }
    }).state('main.tax.business', {
        url: '/business'
    }).state('main.tax.business.manage', {
        url: '/manage',
        views: {
            'right@main.tax': {
                templateUrl: 'tpls/tax/business/manage.html'
            }
        }
    }).state('main.tax.statistics', {
        url: '/statistics'
    }).state('main.tax.statistics.statement', {
        url: '/statement',
        views: {
            'right@main.tax': {
                templateUrl: 'tpls/tax/statistics/statement.html'
            }
        }
    });
}]);