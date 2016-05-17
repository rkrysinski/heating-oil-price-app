'use strict';

angular
    .module('OilApp', [
        'ui.router',
        'googlechart',
        'ngResource',
        'ui.bootstrap',
        'OilApp.views',
        'OilApp.common'
    ])
    .config(function ($urlRouterProvider, $resourceProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/home');
    });
