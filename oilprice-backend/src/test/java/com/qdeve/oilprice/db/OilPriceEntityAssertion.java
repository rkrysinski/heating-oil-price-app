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
package com.qdeve.oilprice.db;

import static org.hamcrest.core.IsEqual.equalTo;

import org.hamcrest.MatcherAssert;

public class OilPriceEntityAssertion
{
	private OilPriceEntity entityUnderTest;

	public OilPriceEntityAssertion(OilPriceEntity entityUnderTest)
	{
		this.entityUnderTest = entityUnderTest;
	}

	public static OilPriceEntityAssertion assertThat(OilPriceEntity entity)
	{
		return new OilPriceEntityAssertion(entity);
	}

	public OilPriceEntityAssertion withPriceValue(Long value)
	{
		MatcherAssert.assertThat(entityUnderTest.getPrice().longValueExact(), equalTo(value));
		return this;
	}

	public OilPriceEntityAssertion withPriceCurrency(String currency)
	{
		MatcherAssert.assertThat(entityUnderTest.getPriceCurrency(), equalTo(currency));
		return this;
	}

	public OilPriceEntityAssertion withExciseValue(Long value)
	{
		MatcherAssert.assertThat(entityUnderTest.getExcise().longValueExact(), equalTo(value));
		return this;
	}

	public OilPriceEntityAssertion withExciseCurrency(String currency)
	{
		MatcherAssert.assertThat(entityUnderTest.getExciseCurrency(), equalTo(currency));
		return this;
	}

	public OilPriceEntityAssertion withFuelSurchargeValue(Long value)
	{
		MatcherAssert.assertThat(entityUnderTest.getFuelSurcharge().longValueExact(), equalTo(value));
		return this;
	}

	public OilPriceEntityAssertion withFuelSurchargeCurrency(String currency)
	{
		MatcherAssert.assertThat(entityUnderTest.getFuelSurchargeCurrency(), equalTo(currency));
		return this;
	}
}
