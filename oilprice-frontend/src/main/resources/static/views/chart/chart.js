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
    .controller('ChartCtrl', function ($rootScope, oilPriceMgr) {
    	if ($rootScope.myChartObject === undefined) {
    		
    		oilPriceMgr.all(function(chartRows) {
    			
    			$rootScope.myChartObject = $rootScope.myChartObject || {
    				type: "AnnotationChart",
    				data: {
    					"cols": [
    					         {id: "month", label: "Month", type: "date"},
    					         {id: "oilprice-data", label: "Oil price", type: "number"}
    					         ], 
    					         "rows": chartRows
    				},
    				options: {
    					displayAnnotations: false
    				}
    			};
    			
    		});
    	}
    });