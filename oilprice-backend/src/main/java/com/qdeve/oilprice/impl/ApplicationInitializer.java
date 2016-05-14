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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Perform import of oil price during application startup. Applicable only for
 * production.
 *
 * @author Roman Krysinski
 *
 */
@Component
@Profile(ApplicationInitializer.INITIALIZER)
public class ApplicationInitializer implements ApplicationRunner
{
	public static final String INITIALIZER = "initializer";

	@Autowired
	private ImportService importService;

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		importService.doImport();
	}
}
