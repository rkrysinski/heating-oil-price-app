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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.db.OilPriceDAO;
import com.qdeve.oilprice.reader.OilPriceReader;

/**
 * Import service that does fetches data from external system and loads
 * information to database on daily basis.
 *
 * @author Roman Krysinski
 */
@Component
public class ImportService
{
	private static final Logger LOG = LoggerFactory.getLogger(ImportService.class);

	@Autowired
	private OilPriceReader reader;
	@Autowired
	private OilPriceDAO dao;

	/**
	 * Perform import at 2:00 AM each day.
	 */
	@Scheduled(cron = "0 2 * * * *")
	public void doImport()
	{
		LOG.info("Fetching information from external system.");
		List<OilPrice> entries = reader.fetch();
		dao.overwriteWith(entries);
		LOG.info("System loaded with Oil Price information.");
	}
}
