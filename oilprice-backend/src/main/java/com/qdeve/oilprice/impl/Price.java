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

import java.time.LocalDate;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;

/**
 * Holds calculated price and date.
 *
 * @author Roman Krysinski
 */
public class Price
{
	private LocalDate date;
	private MonetaryAmount value;

	public LocalDate getDate()
	{
		return date;
	}

	public void setDate(LocalDate date)
	{
		this.date = date;
	}

	public MonetaryAmount getValue()
	{
		return value;
	}

	public void setValue(MonetaryAmount value)
	{
		this.value = value;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private LocalDate date;
		private MonetaryAmount value;
		private boolean isRounding;

		public Builder withDate(LocalDate date)
		{
			this.date = date;
			return this;
		}

		public Builder withPrice(MonetaryAmount value)
		{
			this.value = value;
			return this;
		}

		public Price build()
		{
			Price price = new Price();
			price.setDate(date);
			if (isRounding)
			{
				MonetaryRounding rounding = Monetary.getRounding(value.getCurrency());
				value = value.with(rounding);
			}
			price.setValue(value);
			return price;
		}

		public Builder withRounding()
		{
			isRounding = true;
			return this;
		}
	}

	@Override
	public String toString()
	{
		return value.toString();
	}
}
