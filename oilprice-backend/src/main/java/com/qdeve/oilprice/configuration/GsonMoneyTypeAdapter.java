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

package com.qdeve.oilprice.configuration;

import java.io.IOException;

import org.javamoney.moneta.Money;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.qdeve.oilprice.util.MonetaryUtils;

/**
 * Custom GSON adapter for {@link Money} type.
 *
 * @author Roman Krysinski
 */
public class GsonMoneyTypeAdapter extends TypeAdapter<Money>
{
	public static final String CURRENTY_TAG = "currency";
	public static final String PRICE_TAG = "value";

	@Override
	public void write(JsonWriter out, Money value) throws IOException
	{
		out.beginObject();
		out.name(CURRENTY_TAG).value(MonetaryUtils.getCurrencyFrom(value));
		out.name(PRICE_TAG).value(MonetaryUtils.getBigDecimalFrom(value));
		out.endObject();
	}

	@Override
	public Money read(JsonReader in) throws IOException
	{
		String currency = null;
		Double price = null;
		in.beginObject();
		while (in.hasNext())
		{
			switch (in.nextName())
			{
			case CURRENTY_TAG:
				currency = in.nextString();
				break;
			case PRICE_TAG:
				price = in.nextDouble();
				break;
			}
		}
		in.endObject();
		return MonetaryUtils.newMoneyFrom(price, currency);
	}

}
