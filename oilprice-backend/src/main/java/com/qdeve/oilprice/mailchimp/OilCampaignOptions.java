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

import com.ecwid.mailchimp.MailChimpObject;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignCreateMethod;

/**
 * Definition of options used in {@link CampaignCreateMethod#options}.
 *
 * @author Roman Krysinski
 */
public class OilCampaignOptions extends MailChimpObject
{
	@Field
	private String list_id;
	@Field
	private String subject;
	@Field
	private String from_email;
	@Field
	private String from_name;
	@Field
	private String title;
	@Field
	private String template_id;

	public OilCampaignOptions()
	{
	}

	public OilCampaignOptions(String list_id, String subject, String from_email, String from_name, String title,
			String template_id)
	{
		super();
		this.list_id = list_id;
		this.subject = subject;
		this.from_email = from_email;
		this.from_name = from_name;
		this.title = title;
		this.template_id = template_id;
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
}
