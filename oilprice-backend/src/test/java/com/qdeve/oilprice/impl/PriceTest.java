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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import org.javamoney.moneta.Money;
import org.junit.Test;

import com.qdeve.oilprice.util.MonetaryUtils;

public class PriceTest
{
	@Test
	public void shouldRoundValue()
	{
		// given
		Money priceMoney = MonetaryUtils.newMoneyFrom(1.222222, "PLN");

		// when
		Price price = Price.builder()
				.withPrice(priceMoney)
				.withRounding()
				.build();
		// then
		assertThat(MonetaryUtils.getDoubleFrom(price.getValue()), equalTo(1.22));
	}

	@Test
	public void shouldNotRoundValue()
	{
		// given
		Money priceMoney = MonetaryUtils.newMoneyFrom(1.222222, "PLN");

		// when
		Price price = Price.builder()
				.withPrice(priceMoney)
				.build();

		// then
		assertThat(MonetaryUtils.getDoubleFrom(price.getValue()), equalTo(1.222222));
	}

}
