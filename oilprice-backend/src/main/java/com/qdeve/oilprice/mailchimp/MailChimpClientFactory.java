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
package com.qdeve.oilprice.mailchimp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ecwid.mailchimp.MailChimpClient;

/**
 * Factory of {@link MailChimpClient}. It configures the MailChimp client to use
 * {@link MailChimpHttpConnectionManager} instead of default implementation.
 *
 * @author Roman Krysinski
 */
@Configuration
public class MailChimpClientFactory
{
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public MailChimpClient campaignCreateMethod()
	{
		MailChimpHttpConnectionManager connectionMgr = new MailChimpHttpConnectionManager(restTemplate);
		MailChimpClient mailChimpClient = new MailChimpClient(connectionMgr);
		return mailChimpClient;
	}

}
