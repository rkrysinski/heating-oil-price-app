'use strict';

angular
    .module('OilApp.chart', [])
    .config(function ($stateProvider) {
        $stateProvider.state('chart', {
            url: '/chart',
            templateUrl: 'views/chart/chart-tmpl.html',
            controller: 'ChartCtrl'
        });
    })
    .controller('ChartCtrl', function ($scope, oilPriceMgr) {
    	
    	oilPriceMgr.all(function(chartRows) {
        	$scope.myChartObject = {};
            $scope.myChartObject.type = "AnnotationChart";
            $scope.myChartObject.data = {
            		"cols": [
            		         {id: "month", label: "Month", type: "date"},
            		         {id: "oilprice-data", label: "Oil price", type: "number"}
            		 ], 
            		 "rows": chartRows
            };
            

            $scope.myChartObject.options = {
                displayAnnotations: false
            };
    	});
    });