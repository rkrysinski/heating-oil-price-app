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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.CharMatcher;
import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.db.OilPrice;

/**
 * HTML content parser. Finds all tables in provided html, get's all rows and
 * tries to parse each row to {@link OilPrice}.
 *
 * @author Roman Krysinski
 */
@Component
class ContentParser
{
	private static final Logger LOG = LoggerFactory.getLogger(ContentParser.class);
	private static final int DEFAULT_OIL_PRICE_COLUMN_SIZE = 4;

	public static final String DEFAULT_CURRENCY = "PLN";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private DecimalFormat decimalFormat;

	@PostConstruct
	public void initialize()
	{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		String pattern = "##0.0#";
		decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
	}

	public List<OilPrice> parse(String html) throws ProcessingException
	{
		Document doc = Jsoup.parse(html);
		Elements tableElements = doc.select("table");
		Elements tableRowElements = tableElements.select(":not(thead) tr");

		List<OilPrice> oilPrices = new ArrayList<>(tableRowElements.size());
		tableRowElements.stream().forEach(rowElement ->
		{
			Elements rowItems = rowElement.select("td");
			parseRow(rowItems).ifPresent(oilPrice ->
			{
				oilPrices.add(oilPrice);
			});
		});
		return oilPrices;
	}

	private Optional<OilPrice> parseRow(Elements rowItems) throws ProcessingException
	{
		OilPrice parsedOilPrice = null;

		if (rowItems.size() == DEFAULT_OIL_PRICE_COLUMN_SIZE)
		{
			try
			{
				parsedOilPrice =  OilPrice.builder()
						.withDate(parseDate(rowItems.get(0).text()))
						.withPrice(parseMoney(rowItems.get(1).text()))
						.withExcise(parseMoney(rowItems.get(2).text()))
						.withFuelSurcharge(parseMoney(rowItems.get(3).text()))
						.build();
			} catch (ParseException | DateTimeParseException e)
			{
				throw new ProcessingException("Failed to parse row, reason: " + e.getMessage(), e);
			}
		} else
		{
			LOG.warn("Incorrect number of elements in table, was {}, but expecting {}",
					rowItems.size(), DEFAULT_OIL_PRICE_COLUMN_SIZE);
		}

		return Optional.ofNullable(parsedOilPrice);
	}

	private LocalDate parseDate(String text)
	{
		return LocalDate.from(DATE_FORMATTER.parse(text));
	}

	private MonetaryAmount parseMoney(String price) throws ParseException
	{
		String normalizedPrice = normalizePriceString(price);
		return Monetary.getDefaultAmountFactory()
				.setNumber(decimalFormat.parse(normalizedPrice))
				.setCurrency(DEFAULT_CURRENCY)
				.create();
	}

	/**
	 * Remove all white space characters.
	 */
	private String normalizePriceString(String price)
	{
		return CharMatcher.WHITESPACE.removeFrom(price);
	}
}
