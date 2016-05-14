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
package com.qdeve.oilprice.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qdeve.oilprice.common.BootstrapApplicationTest;
import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.util.MonetaryUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapApplicationTest
@TestPropertySource(locations = "classpath:price_reader_test.properties")
public class OilPriceReaderTest
{
	@Autowired
	private OilPriceReader reader;

	@Test
	public void shouldProperlyFetchDataFromConfiguredContentPath()
	{
		// given configuration in price_reader_test.properties

		// when
		List<OilPrice> oilPrices = reader.fetch();

		// then
		assertThat(oilPrices.size(), equalTo(1));
		assertThat(oilPrices.get(0),
				equalTo(OilPrice.builder()
						.withDate(LocalDate.from(ContentParser.DATE_FORMATTER.parse("2016-04-12")))
						.withPrice(MonetaryUtils.newMoneyFrom(100, ContentParser.DEFAULT_CURRENCY))
						.withExcise(MonetaryUtils.newMoneyFrom(200, ContentParser.DEFAULT_CURRENCY))
						.withFuelSurcharge(MonetaryUtils.newMoneyFrom(20, ContentParser.DEFAULT_CURRENCY))
						.build())
				);
	}

}
