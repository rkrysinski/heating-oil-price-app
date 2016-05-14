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

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessageThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qdeve.oilprice.FileUtils;
import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.common.BootstrapApplicationTest;
import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.util.MonetaryUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapApplicationTest
public class ContentParserTest
{
	@Autowired
	private ContentParser parserUnderTest;

	@Autowired
	private FileUtils fUtils;

	@Test
	public void shouldProperlyParseTableWithOneElement() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:table_with_one_element.html");

		// when
		List<OilPrice> oilPrices = parserUnderTest.parse(html);

		// then
		assertThat(oilPrices.size(), equalTo(1));
		assertThat(oilPrices.get(0),
				equalTo(OilPrice.builder()
						.withDate(LocalDate.from(ContentParser.DATE_FORMATTER.parse("2016-04-12")))
						.withPrice(MonetaryUtils.newMoneyFrom(1719, ContentParser.DEFAULT_CURRENCY))
						.withExcise(MonetaryUtils.newMoneyFrom(232, ContentParser.DEFAULT_CURRENCY))
						.withFuelSurcharge(MonetaryUtils.newMoneyFrom(0, ContentParser.DEFAULT_CURRENCY))
						.build())
				);
	}

	@Test
	public void shouldProperlyParsePriceWithSpaces() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:price_with_spaces.html");

		// when
		List<OilPrice> oilPrices = parserUnderTest.parse(html);

		// then
		assertThat(oilPrices.size(), equalTo(1));
		assertThat(oilPrices.get(0).getPrice(),
				equalTo(MonetaryUtils.newMoneyFrom(1719, ContentParser.DEFAULT_CURRENCY))
				);
	}

	@Test
	public void shouldFailWhenDateIsInvalid() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:invalid_date.html");

		// when
		catchException(parserUnderTest).parse(html);

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("Text '2016-' could not be parsed")));
	}

	@Test
	public void shouldFailWhenPriceIsInvalid() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:invalid_price.html");

		// when
		catchException(parserUnderTest).parse(html);

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("Unparseable number: \"xt\"")));
	}

	@Test
	public void shouldFailWhenExciseIsInvalid() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:invalid_excise.html");

		// when
		catchException(parserUnderTest).parse(html);

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("Unparseable number: \"asdf\"")));
	}

	@Test
	public void shouldFailWhenFuelSurchargeIsInvalid() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:invalid_fuel_surcharge.html");

		// when
		catchException(parserUnderTest).parse(html);

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("Unparseable number: \"sdf\"")));
	}

	@Test
	public void shouldHandleParsingRealWorldExample() throws IOException
	{
		// given
		String html = fUtils.getFileContentAsString("classpath:archive.html");

		// when
		List<OilPrice> oilPrices = parserUnderTest.parse(html);

		// then
		assertThat(oilPrices.size(), equalTo(2914));
	}

}
