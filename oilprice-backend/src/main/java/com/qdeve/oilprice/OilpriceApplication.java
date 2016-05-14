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
package com.qdeve.oilprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.qdeve.oilprice.impl.ApplicationInitializer;

/**
 * Application starter.
 *
 * @author Roman Krysinski
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class OilpriceApplication
{
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(OilpriceApplication.class);
		app.setAdditionalProfiles(ApplicationInitializer.INITIALIZER);
		app.run(args);
	}
}
