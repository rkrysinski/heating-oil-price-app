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

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Configuration of Apache HTTP Client implementation to be used in the
 * application. It configures also the connection timeout policy.
 *
 * @author Roman Krysinski
 */
@Configuration
public class HttpConfiguration
{
	@Autowired
	private OilPriceProperties appPropertries;

	@Bean
	public ClientHttpRequestFactory httpRequestsFactory()
	{
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpclient());
		factory.setConnectionRequestTimeout(appPropertries.getConnectionRequestTimeout());
		factory.setConnectTimeout(appPropertries.getConnectionTimeout());
		factory.setReadTimeout(appPropertries.getConnectionReadTimeout());
		return factory;
	}

	private HttpClient httpclient()
	{
		HttpClientBuilder builder = HttpClientBuilder.create()
				.useSystemProperties();
		if (appPropertries.getProxyHost() != null && appPropertries.getProxyPort() != null)
		{
			builder.setProxy(new HttpHost(
					appPropertries.getProxyHost(),
					appPropertries.getProxyPort())
			);
		}
		return builder.build();
	}
}
