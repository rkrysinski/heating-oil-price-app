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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qdeve.oilprice.db.OilPrice;

/**
 * Retrieve OilPrice information from external system.
 *
 * @author Roman Krysinski
 */
@Component
public class OilPriceReader
{
	@Autowired
	private ContentReader contentReader;
	@Autowired
	private ContentParser bodyParser;

	public List<OilPrice> fetch()
	{
		String html = contentReader.get();
		return bodyParser.parse(html);
	}

}
