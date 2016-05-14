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

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ecwid.mailchimp.connection.MailChimpConnectionManager;

/**
 * Implementation of {@link MailChimpConnectionManager} which utilizes Spring
 * {@link RestTemplate}.
 *
 * @author Roman Krysinski
 */
public class MailChimpHttpConnectionManager implements MailChimpConnectionManager
{
	private RestTemplate restTemplate;

	public MailChimpHttpConnectionManager(RestTemplate restTemplate)
	{
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public String post(String url, String payload) throws IOException
	{
		HttpEntity<String> entity = new HttpEntity<String>(payload);
		ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		return result.getBody();
	}

	@Override
	public void close() throws IOException
	{
		// not used
	}
}
