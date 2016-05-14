'use strict';

angular
    .module('OilApp.home', [])
    .config(function ($stateProvider) {
        $stateProvider.state('home', {
            url: '/home',
            templateUrl: 'views/home/home-tmpl.html',
            controller: 'HomeCtrl'
        });
    })
    .controller('HomeCtrl', function ($scope, oilPriceMgr) {
    	oilPriceMgr.currentPrice(function(price) {
    		$scope.current = price;
    	});
    	$scope.top = oilPriceMgr.top(function(topPrices) {
    		$scope.top = topPrices;
    	});
    });