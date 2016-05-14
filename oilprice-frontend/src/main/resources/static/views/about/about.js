'use strict';

angular
    .module('OilApp.about', [])
    .config(function ($stateProvider) {
        $stateProvider.state('about', {
            url: '/about',
            templateUrl: 'views/about/about-tmpl.html',
            controller: 'AboutCtrl'
        });
    })
    .controller('AboutCtrl', function ($scope) {
    	
    });