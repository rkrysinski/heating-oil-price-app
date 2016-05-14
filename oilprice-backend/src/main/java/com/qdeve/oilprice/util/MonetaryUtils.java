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
package com.qdeve.oilprice.util;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;

/**
 * Handy methods to operate with {@link MonetaryAmount}.
 *
 * @author Roman Krysinski
 */
public final class MonetaryUtils
{
	public static BigDecimal getBigDecimalFrom(MonetaryAmount amount)
	{
		return amount == null ? null : amount.getNumber().numberValueExact(BigDecimal.class);
	}

	public static String getCurrencyFrom(MonetaryAmount amount)
	{
		return amount == null ? null : amount.getCurrency().getCurrencyCode();
	}

	public static MonetaryAmount newMoneyFrom(int number, String currency)
	{
		return Money.of(number, currency);
	}

	public static MonetaryAmount newMoneyFrom(BigDecimal number, String currency)
	{
		return Money.of(number, currency);
	}

	public static Money newMoneyFrom(Double price, String currency)
	{
		return Money.of(price, currency);
	}
}
