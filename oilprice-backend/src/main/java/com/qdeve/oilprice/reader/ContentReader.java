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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.configuration.OilPriceProperties;

/**
 * Gets the content of resource defined in
 * {@link OilPriceProperties#getContentPath()}, which can points to e.g.
 * file://file/html or http://somesite/somefile.html
 *
 * @author Roman Krysinski
 */
@Component
class ContentReader
{
	private static final String DEFAULT_ENCODING = "UTF-8";

	@Autowired
	private OilPriceProperties properties;
	@Autowired
	private ApplicationContext applCtx;
	@Autowired
	private RestTemplate restTemplate;

	public String get()
	{
		String body = null;
		if (properties.getContentPath().startsWith("http"))
		{
			body = getContentViaHttpClient();
		} else
		{
			body = getContentFromFile();
		}
		return body;
	}

	private String getContentFromFile()
	{
		String body = null;
		Resource resource = applCtx.getResource(properties.getContentPath());
		InputStream inputStream;
		try
		{
			inputStream = resource.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, DEFAULT_ENCODING);
			body = writer.toString();
		} catch (IOException e)
		{
			throw new ProcessingException("Failed to retrieve content from " + properties.getContentPath()
					+ ", reason: " + e.getMessage(), e);
		}
		return body;
	}

	private String getContentViaHttpClient()
	{
		String body = null;
		try
		{
			body = restTemplate.getForObject(properties.getContentPath(), String.class);
		} catch (RestClientException e)
		{
			throw new ProcessingException("Failed to retrieve content from " + properties.getContentPath()
					+ ", reason: " + e.getMessage(), e);
		}

		return body;
	}
}
