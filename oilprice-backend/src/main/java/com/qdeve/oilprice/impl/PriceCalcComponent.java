/**************************************************************************
 * (C) Copyright 2016 QDEVE Roman Krysinski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **************************************************************************/
package com.qdeve.oilprice.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.money.MonetaryAmount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.db.OilPriceDAO;

/**
 * Calculates actual price(s) that are exposed to subscribers.
 *
 * @author Roman Krysinski
 */
@Component
public class PriceCalcComponent
{
	private static final double MARGIN_FACTOR = 1.15;
	@Autowired
	private OilPriceDAO oilPriceDAO;

	public Price getCurrentPrice()
	{
		OilPrice current = oilPriceDAO.getCurrent();
		return calculateFrom(current);
	}

	public List<Price> getTopHistoricalPrices()
	{
		return oilPriceDAO.findTop()
				.stream()
				.map(oilPrice -> calculateFrom(oilPrice))
				.collect(Collectors.toList());
	}

	private Price calculateFrom(OilPrice oilPrice)
	{
		MonetaryAmount sum = oilPrice.getPrice().add(oilPrice.getExcise()).add(oilPrice.getFuelSurcharge());
		MonetaryAmount pricePerLiterWithMargin = sum.divide(1000).multiply(MARGIN_FACTOR);
		return Price.builder()
				.withPrice(pricePerLiterWithMargin)
				.withDate(oilPrice.getDate())
				.build();
	}

}
