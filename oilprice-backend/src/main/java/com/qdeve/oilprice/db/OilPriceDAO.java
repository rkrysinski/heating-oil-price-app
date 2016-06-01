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

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provides access to application high level {@link OilPrice} entities.
 *
 * @author Roman Krysinski
 */
@Component
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OilPriceDAO
{
	@Autowired
	private OilPriceEntityDAO entityDAO;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(List<OilPrice> prices)
	{
		if (!prices.isEmpty())
		{
			Set<Date> dbEntitiesDates = entityDAO.findAll().stream()
					.map(OilPriceEntity::getDate)
					.collect(Collectors.toSet());
			List<OilPriceEntity> toSave = prices.stream()
					.map(price -> price.toEntity())
					.filter(priceEntity -> {
						return !dbEntitiesDates.contains(priceEntity.getDate());
					})
					.collect(Collectors.toList());
			entityDAO.save(toSave);
		}
	}

	public List<OilPrice> getAll()
	{
		return entityDAO.findAll().stream()
				.map(OilPrice::fromEntity)
				.collect(Collectors.toList());
	}

	public List<OilPrice> findTop()
	{
		return entityDAO.findTop15ByOrderByDateDesc().stream()
				.map(OilPrice::fromEntity)
				.collect(Collectors.toList());
	}

	public OilPrice getCurrent()
	{
		return OilPrice.fromEntity(
				entityDAO.findFirstByOrderByDateDesc()
		);
	}

}
