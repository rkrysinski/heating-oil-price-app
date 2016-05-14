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

import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

/**
 * OilPrice application configuration properties.
 *
 * @author Roman Krysinski
 */
@Component
@ConfigurationProperties(prefix = "oilprice")
public class OilPriceProperties
{
	public static final long DEFAULT_TIMEOUT = TimeUnit.SECONDS.toMillis(10);

	@NotNull
	private String contentPath;
	private long connectionRequestTimeout = DEFAULT_TIMEOUT;
	private long connectionTimeout = DEFAULT_TIMEOUT;
	private long connectionReadTimeout = DEFAULT_TIMEOUT;
	private String proxyHost;
	private Integer proxyPort;
	@NestedConfigurationProperty
	private MailchimpProperties mailchimp;

	public MailchimpProperties getMailchimp()
	{
		return mailchimp;
	}

	public String getProxyHost()
	{
		return proxyHost;
	}

	public void setProxyHost(String proxyHost)
	{
		this.proxyHost = proxyHost;
	}

	public Integer getProxyPort()
	{
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort)
	{
		this.proxyPort = proxyPort;
	}

	public void setMailchimp(MailchimpProperties mailchimp)
	{
		this.mailchimp = mailchimp;
	}

	public int getConnectionRequestTimeout()
	{
		return (int) connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(long connectionRequestTimeout)
	{
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public int getConnectionTimeout()
	{
		return (int) connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
	}

	public int getConnectionReadTimeout()
	{
		return (int) connectionReadTimeout;
	}

	public void setConnectionReadTimeout(long connectionReadTimeout)
	{
		this.connectionReadTimeout = connectionReadTimeout;
	}

	public String getContentPath()
	{
		return contentPath;
	}

	/**
	 * @param contentPath
	 *            e.g. file://file/html or http://somesite/somefile.html
	 */
	public void setContentPath(String contentPath)
	{
		this.contentPath = contentPath;
	}

}
