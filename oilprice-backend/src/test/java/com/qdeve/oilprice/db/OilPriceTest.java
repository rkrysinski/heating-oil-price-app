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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.Test;

import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.db.OilPriceEntity;
import com.qdeve.oilprice.util.MonetaryUtils;

public class OilPriceTest
{
	@Test
	public void shouldPropertlyConvertFromEntity()
	{
		// given
		Instant now = Instant.now();
		OilPriceEntity dbEntity = new OilPriceEntity();
		dbEntity.setId(123);
		dbEntity.setDate(Date.from(now));
		dbEntity.setPrice(new BigDecimal(1));
		dbEntity.setPriceCurrency("USD");
		dbEntity.setExcise(new BigDecimal(2));
		dbEntity.setExciseCurrency("PLN");
		dbEntity.setFuelSurcharge(new BigDecimal(3));
		dbEntity.setFuelSurchargeCurrency("ZMW");

		// when
		OilPrice oilPrice = OilPrice.fromEntity(dbEntity);

		// then
		LocalDate nowDate = LocalDateTime.ofInstant(now, ZoneId.systemDefault()).toLocalDate();
		assertThat(oilPrice, equalTo(OilPrice.builder()
				.withDate(nowDate)
				.withPrice(MonetaryUtils.newMoneyFrom(1, "USD"))
				.withExcise(MonetaryUtils.newMoneyFrom(2, "PLN"))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(3, "ZMW"))
				.build()));
		assertThat(oilPrice.getId(), equalTo(123L));
	}

	@Test
	public void shouldPropertlyConvertToEntity()
	{
		// given
		OilPrice oilPrice = OilPrice.builder()
				.withId(15)
				.withDate(LocalDate.now())
				.withPrice(MonetaryUtils.newMoneyFrom(10, "ZAR"))
				.withExcise(MonetaryUtils.newMoneyFrom(20, "EUR"))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(30, "ALL"))
				.build();

		// when
		OilPriceEntity entity = oilPrice.toEntity();

		// then
		Instant nowDate = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		assertThat(entity.getId(), equalTo(15L));
		assertThat(entity.getDate(), equalTo(Date.from(nowDate)));
		assertThat(entity.getPrice(), equalTo(new BigDecimal(10)));
		assertThat(entity.getPriceCurrency(), equalTo("ZAR"));
		assertThat(entity.getExcise(), equalTo(new BigDecimal(20)));
		assertThat(entity.getExciseCurrency(), equalTo("EUR"));
		assertThat(entity.getFuelSurcharge(), equalTo(new BigDecimal(30)));
		assertThat(entity.getFuelSurchargeCurrency(), equalTo("ALL"));
	}

}
