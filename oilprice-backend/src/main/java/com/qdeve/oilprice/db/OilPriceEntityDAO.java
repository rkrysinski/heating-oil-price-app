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

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Provides access to low level {@link OilPriceEntity}s stored in database.
 *
 * @author Roman Krysinski
 */
@Repository
interface OilPriceEntityDAO extends CrudRepository<OilPriceEntity, Long>
{
	@Override
	List<OilPriceEntity> findAll();

	List<OilPriceEntity> findTop15ByOrderByDateDesc();

	OilPriceEntity findFirstByOrderByDateDesc();

	@Override
	@Lock(LockModeType.OPTIMISTIC)
	<S extends OilPriceEntity> List<S> save(Iterable<S> items);
}
