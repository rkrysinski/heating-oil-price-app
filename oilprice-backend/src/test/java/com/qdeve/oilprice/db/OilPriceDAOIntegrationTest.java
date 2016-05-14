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

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qdeve.oilprice.common.BootstrapApplicationTest;
import com.qdeve.oilprice.util.MonetaryUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapApplicationTest
public class OilPriceDAOIntegrationTest
{
	@Autowired
	private OilPriceDAO daoUnderTest;

	@Autowired
	private OilPriceEntityDAO entityDAO;

	@Before
	public void initialize()
	{
		entityDAO.deleteAll();
	}

	@Test
	public void shouldRetrieveDataFromDb()
	{
		// given
		Instant now = Instant.now();
		entityDAO.save(OilPriceEntity.builder()
				.withId(123)
				.withDate(Date.from(now))
				.withPrice(1)
				.withPriceCurrency("USD")
				.withExcise(2)
				.withExciseCurrency("PLN")
				.withFuelSurcharge(3)
				.withFuelSurchargeCurrency("ZMW")
				.build()
				);

		// when
		List<OilPrice> oilPrices = daoUnderTest.getAll();

		// then
		assertThat(oilPrices.size(), equalTo(1));
		LocalDate nowDate = LocalDateTime.ofInstant(now, ZoneId.systemDefault()).toLocalDate();
		assertThat(oilPrices.get(0), equalTo(OilPrice.builder()
				.withDate(nowDate)
				.withPrice(MonetaryUtils.newMoneyFrom(1, "USD"))
				.withExcise(MonetaryUtils.newMoneyFrom(2, "PLN"))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(3, "ZMW"))
				.build()));
	}

	@Test
	public void shouldOverwriteEntitiesInDb()
	{
		// given
		LocalDate nowDate = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalDate();
		Instant today = nowDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		entityDAO.save(OilPriceEntity.builder()
				.withId(123)
				.withDate(Date.from(today))
				.withPrice(1)
				.withPriceCurrency("USD")
				.withExcise(2)
				.withExciseCurrency("PLN")
				.withFuelSurcharge(3)
				.withFuelSurchargeCurrency("ZMW")
				.build()
				);

		// when
		Instant tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate tomorrowDate = LocalDateTime.ofInstant(tomorrow, ZoneId.systemDefault()).toLocalDate();
		daoUnderTest.overwriteWith(Arrays.asList(OilPrice.builder()
				.withDate(tomorrowDate)
				.withPrice(MonetaryUtils.newMoneyFrom(10, "ZAR"))
				.withExcise(MonetaryUtils.newMoneyFrom(20, "EUR"))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(30, "ALL"))
				.build()
				));

		// then
		List<OilPriceEntity> dbEntities = entityDAO.findAll();
		assertThat(dbEntities.size(), equalTo(1));
		OilPriceEntity dbEntity = dbEntities.get(0);
		assertThat(dbEntity.getPrice().longValueExact(), equalTo(10L));
		assertThat(dbEntity.getPriceCurrency(), equalTo("ZAR"));
		assertThat(dbEntity.getExcise().longValueExact(), equalTo(20L));
		assertThat(dbEntity.getExciseCurrency(), equalTo("EUR"));
		assertThat(dbEntity.getFuelSurcharge().longValueExact(), equalTo(30L));
		assertThat(dbEntity.getFuelSurchargeCurrency(), equalTo("ALL"));
		LocalDate tomorrowDateEntity = LocalDateTime.ofInstant(dbEntity.getDate().toInstant(), ZoneId.systemDefault()).toLocalDate();
		assertThat(tomorrowDateEntity, equalTo(tomorrowDate));
	}

}
