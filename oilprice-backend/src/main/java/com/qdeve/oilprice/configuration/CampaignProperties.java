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

import com.qdeve.oilprice.mailchimp.OilCampaignOptions;

/**
 * MailChimp related campaign properties.
 *
 * @author Roman Krysinski
 */
public class CampaignProperties
{
	private String list_id;
	private String subject;
	private String from_email;
	private String from_name;
	private String title;
	private String template_id;
	private Boolean authenticate;

	public OilCampaignOptions toCampaignOptions()
	{
		OilCampaignOptions opts = new OilCampaignOptions(
				getList_id(),
				getSubject(),
				getFrom_email(),
				getFrom_name(),
				getTitle(),
				getTemplate_id()
		);
		return opts;
	}

	public String getList_id()
	{
		return list_id;
	}

	public void setList_id(String list_id)
	{
		this.list_id = list_id;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getFrom_email()
	{
		return from_email;
	}

	public void setFrom_email(String from_email)
	{
		this.from_email = from_email;
	}

	public String getFrom_name()
	{
		return from_name;
	}

	public void setFrom_name(String from_name)
	{
		this.from_name = from_name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTemplate_id()
	{
		return template_id;
	}

	public void setTemplate_id(String template_id)
	{
		this.template_id = template_id;
	}

	public Boolean getAuthenticate()
	{
		return authenticate;
	}

	public void setAuthenticate(Boolean authenticate)
	{
		this.authenticate = authenticate;
	}
}
