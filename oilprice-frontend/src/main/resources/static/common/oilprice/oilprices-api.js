'use strict';

angular
	.module('OilApp.oilpricesApi', ['ngResource'])
	.service('oilPricesSrv', function ($resource) {
		
		var current = function(callback) {
			return $resource('/oilprices/current').get(callback);
		};
		
		var top = function(callback) {
			return $resource('/oilprices/top', {}, {
		        getTop: {
		            method: 'GET',
		            isArray: true
		        }
		    }).getTop(callback);
		};
		
		var getAll = function(callback) {
			return $resource('/oilprices', {}, {
		        all: {
		            method: 'GET',
		            isArray: true
		        }
		    }).all(callback);
		};
		
		return {
			current: current,
			top: top,
			all: getAll
		}
	});
		
