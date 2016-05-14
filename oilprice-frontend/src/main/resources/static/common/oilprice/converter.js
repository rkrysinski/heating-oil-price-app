'use strict';

angular
	.module('OilApp.oilPriceConverter', [])
	.service('oilPriceConverterSrv', function ($resource) {
		
		function getValueFor(oilPrice) {
			var sum = oilPrice.excise.value + oilPrice.fuelSurcharge.value + oilPrice.price.value;
			var quantityPricePerLiter = (sum / 1000);
			var pricePerLiterWithMargin = quantityPricePerLiter * 1.15;
			return Number((pricePerLiterWithMargin).toFixed(2));
		}
		
		function getDateFor(oilPrice) {
			var isoFormatInput = oilPrice.date.year + "-" + oilPrice.date.month + "-" + oilPrice.date.day;
			var date = new Date(isoFormatInput);
			return date.toLocaleDateString();
		}
		
		function getCurrentyOf(oilPrice) {
			return oilPrice.excise.currency;
		}
		
		var convert = function(oilPrice) {
			var converted = {};
			converted.value    = getValueFor(oilPrice);
			converted.date     = getDateFor(oilPrice);
			converted.currency = getCurrentyOf(oilPrice);
			return converted;
		};
	
		return {
			convert: convert
		}
	});
		
