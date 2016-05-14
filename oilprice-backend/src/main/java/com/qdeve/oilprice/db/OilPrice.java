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

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.money.MonetaryAmount;

import com.qdeve.oilprice.util.MonetaryUtils;

/**
 * Entity that represents the Oil Price. Rich model representation exposed to
 * application.
 *
 * NOTE: {@link OilPrice#id} is not taken into account in
 * {@link OilPrice#equals(Object)}.
 *
 * @author Roman Krysinski
 */
public class OilPrice
{
	private long id;
	private LocalDate date;
	private MonetaryAmount price;
	private MonetaryAmount excise;
	private MonetaryAmount fuelSurcharge;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public void setDate(LocalDate date)
	{
		this.date = date;
	}

	public MonetaryAmount getPrice()
	{
		return price;
	}

	public void setPrice(MonetaryAmount price)
	{
		this.price = price;
	}

	public MonetaryAmount getExcise()
	{
		return excise;
	}

	public void setExcise(MonetaryAmount excise)
	{
		this.excise = excise;
	}

	public MonetaryAmount getFuelSurcharge()
	{
		return fuelSurcharge;
	}

	public void setFuelSurcharge(MonetaryAmount fuelSurcharge)
	{
		this.fuelSurcharge = fuelSurcharge;
	}

	/**
	 * Obtains an instance of OilPrice from OilPriceEntity object.
	 */
	public static OilPrice fromEntity(OilPriceEntity entity)
	{
		Instant instant = Instant.ofEpochMilli(entity.getDate().getTime());
		LocalDate date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return builder()
				.withId(entity.getId())
				.withDate(date)
				.withExcise(MonetaryUtils.newMoneyFrom(
						entity.getExcise(), entity.getExciseCurrency()))
				.withPrice(MonetaryUtils.newMoneyFrom(
						entity.getPrice(), entity.getPriceCurrency()))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(
						entity.getFuelSurcharge(), entity.getFuelSurchargeCurrency()))
				.build();
	}

	/**
	 * Converts this instance to {@link OilPriceEntity}.
	 */
	public OilPriceEntity toEntity()
	{
		Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		return OilPriceEntity.builder()
				.withId(id)
				.withDate(Date.from(instant))
				.withPrice(MonetaryUtils.getBigDecimalFrom(price))
				.withPriceCurrency(MonetaryUtils.getCurrencyFrom(price))
				.withExcise(MonetaryUtils.getBigDecimalFrom(excise))
				.withExciseCurrency(MonetaryUtils.getCurrencyFrom(excise))
				.withFuelSurcharge(MonetaryUtils.getBigDecimalFrom(fuelSurcharge))
				.withFuelSurchargeCurrency(MonetaryUtils.getCurrencyFrom(fuelSurcharge))
				.build();
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private long id;
		private LocalDate date;
		private MonetaryAmount price;
		private MonetaryAmount excise;
		private MonetaryAmount fuelSurcharge;

		public Builder withId(long id)
		{
			this.id = id;
			return this;
		}
		public Builder withDate(LocalDate date)
		{
			this.date = date;
			return this;
		}

		public Builder withPrice(MonetaryAmount price)
		{
			this.price = price;
			return this;
		}

		public Builder withExcise(MonetaryAmount excise)
		{
			this.excise = excise;
			return this;
		}

		public Builder withFuelSurcharge(MonetaryAmount fuelSurcharge)
		{
			this.fuelSurcharge = fuelSurcharge;
			return this;
		}

		public OilPrice build()
		{
			OilPrice oilPrice = new OilPrice();
			oilPrice.setId(id);
			oilPrice.setDate(date);
			oilPrice.setPrice(price);
			oilPrice.setExcise(excise);
			oilPrice.setFuelSurcharge(fuelSurcharge);
			return oilPrice;
		}
	}

	@Override
	public String toString()
	{
		return "OilPrice [id=" + id + ", date=" + date + ", price=" + price + ", excise=" + excise + ", fuelSurcharge="
				+ fuelSurcharge + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((excise == null) ? 0 : excise.hashCode());
		result = prime * result + ((fuelSurcharge == null) ? 0 : fuelSurcharge.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OilPrice other = (OilPrice) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (excise == null)
		{
			if (other.excise != null)
				return false;
		} else if (!excise.equals(other.excise))
			return false;
		if (fuelSurcharge == null)
		{
			if (other.fuelSurcharge != null)
				return false;
		} else if (!fuelSurcharge.equals(other.fuelSurcharge))
			return false;
		if (price == null)
		{
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}
}
