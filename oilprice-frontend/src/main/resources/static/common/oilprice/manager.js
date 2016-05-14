'use strict';

angular
	.module('OilApp.oilPriceManager', [])
	.service('oilPriceMgr', function ($resource, oilPriceConverterSrv, oilPricesSrv) {
		
		var current = function(callback) {
			oilPricesSrv.current(function (responseData) {
				var converted = oilPriceConverterSrv.convert(responseData);
				callback(converted);
			});
		};
		
		var top = function(callback) {
			oilPricesSrv.top(function (responseData) {
				var converted = [];
				angular.forEach(responseData, function(value, key) {
					converted.push(
							oilPriceConverterSrv.convert(value)
					);
				});
				callback(converted);
			});
		};
	
		return {
			currentPrice: current,
			top: top
		}
	});
		
