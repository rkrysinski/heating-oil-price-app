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

package com.qdeve.oilprice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.qdeve.oilprice.db.OilPrice;
import com.qdeve.oilprice.db.OilPriceDAO;

/**
 * REST API that exposes Oil Prices related information to front-end.
 *
 * @author Roman Krysinski
 */
@RestController
@RequestMapping(OilPriceController.REQUEST_MAPPING)
public class OilPriceController
{
	public static final String REQUEST_MAPPING = "/oilprices";

	@Autowired
	private OilPriceDAO dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	ResponseEntity<List<OilPrice>> getAllItems()
	{
		List<OilPrice> items = dao.getAll();
		return ResponseEntity.ok(items);
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET, produces = "application/json")
	ResponseEntity<OilPrice> current()
	{
		OilPrice item = dao.getCurrent();
		return ResponseEntity.ok(item);
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET, produces = "application/json")
	ResponseEntity<List<OilPrice>> top()
	{
		List<OilPrice> items = dao.findTop();
		return ResponseEntity.ok(items);
	}
}
