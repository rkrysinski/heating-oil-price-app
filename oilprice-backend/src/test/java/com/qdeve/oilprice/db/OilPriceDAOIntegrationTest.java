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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	public void shouldNotRemoveEntitiesFromDb()
	{
		// given
		LocalDate nowDate = LocalDate.now();
		Instant today = nowDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
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
		daoUnderTest.save(Arrays.asList(OilPrice.builder()
				.withDate(tomorrowDate)
				.withPrice(MonetaryUtils.newMoneyFrom(10, "ZAR"))
				.withExcise(MonetaryUtils.newMoneyFrom(20, "EUR"))
				.withFuelSurcharge(MonetaryUtils.newMoneyFrom(30, "ALL"))
				.build())
		);

		// then
		List<OilPriceEntity> dbEntities = entityDAO.findAll();
		assertThat(dbEntities.size(), equalTo(2));

		Optional<OilPriceEntity> tomorrowDbEntity = dbEntities.stream()
				.filter(entity -> entity.isDateEqualTo(tomorrow)).findAny();
		assertThat(tomorrowDbEntity.isPresent(), equalTo(true));
		tomorrowDbEntity.ifPresent(dbEntity -> {
			OilPriceEntityAssertion.assertThat(dbEntity)
				.withPriceValue(10L)
				.withPriceCurrency("ZAR")
				.withExciseValue(20L)
				.withExciseCurrency("EUR")
				.withFuelSurchargeValue(30L)
				.withFuelSurchargeCurrency("ALL");
		});

		Optional<OilPriceEntity> todayDbEntity = dbEntities.stream()
				.filter(entity -> entity.isDateEqualTo(today)).findAny();
		assertThat(todayDbEntity.isPresent(), equalTo(true));
		todayDbEntity.ifPresent(dbEntity -> {
			OilPriceEntityAssertion.assertThat(dbEntity)
				.withPriceValue(1L)
				.withPriceCurrency("USD")
				.withExciseValue(2L)
				.withExciseCurrency("PLN")
				.withFuelSurchargeValue(3L)
				.withFuelSurchargeCurrency("ZMW");
		});
	}

	@Test
	public void shouldNotDuplicateEntriesInDbForTheSameDate()
	{
		// given
		LocalDate nowDate = LocalDate.now();
		Instant today = nowDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		entityDAO.save(OilPriceEntity.builder()
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
		daoUnderTest.save(Arrays.asList(
				OilPrice.builder()
					.withDate(nowDate)
					.withPrice(MonetaryUtils.newMoneyFrom(1, "USD"))
					.withExcise(MonetaryUtils.newMoneyFrom(2, "PLN"))
					.withFuelSurcharge(MonetaryUtils.newMoneyFrom(3, "ZMW"))
					.build(),
				OilPrice.builder()
					.withDate(tomorrowDate)
					.withPrice(MonetaryUtils.newMoneyFrom(10, "ZAR"))
					.withExcise(MonetaryUtils.newMoneyFrom(20, "EUR"))
					.withFuelSurcharge(MonetaryUtils.newMoneyFrom(30, "ALL"))
					.build()
				)
		);

		// then
		List<OilPriceEntity> dbEntities = entityDAO.findAll();
		assertThat(dbEntities.size(), equalTo(2));

		Optional<OilPriceEntity> todayDbEntity = dbEntities.stream()
				.filter(entity -> entity.isDateEqualTo(today)).findAny();
		assertThat(todayDbEntity.isPresent(), equalTo(true));
		todayDbEntity.ifPresent(dbEntity -> {
			OilPriceEntityAssertion.assertThat(dbEntity)
				.withPriceValue(1L)
				.withPriceCurrency("USD")
				.withExciseValue(2L)
				.withExciseCurrency("PLN")
				.withFuelSurchargeValue(3L)
				.withFuelSurchargeCurrency("ZMW");
		});

		Optional<OilPriceEntity> tomorrowDbEntity = dbEntities.stream()
				.filter(entity -> entity.isDateEqualTo(tomorrow)).findAny();
		assertThat(tomorrowDbEntity.isPresent(), equalTo(true));
		tomorrowDbEntity.ifPresent(dbEntity -> {
			OilPriceEntityAssertion.assertThat(dbEntity)
				.withPriceValue(10L)
				.withPriceCurrency("ZAR")
				.withExciseValue(20L)
				.withExciseCurrency("EUR")
				.withFuelSurchargeValue(30L)
				.withFuelSurchargeCurrency("ALL");
		});
	}

}