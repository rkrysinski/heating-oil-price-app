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
 * Definition of content used in {@link CampaignCreateMethod#content}.
 *
 * @author Roman Krysinski
 */
public class OilCampaignContent extends MailChimpObject
{
	@Field
	public String html_currentprice;
	@Field
	public String html_history;

	public OilCampaignContent()
	{
	}

	public String getHtml_currentprice()
	{
		return html_currentprice;
	}

	public void setHtml_currentprice(String html_currentprice)
	{
		this.html_currentprice = html_currentprice;
	}

	public String getHtml_history()
	{
		return html_history;
	}

	public void setHtml_history(String html_history)
	{
		this.html_history = html_history;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{
		private String html_currentprice;
		private String html_history;

		public Builder withCurrentPrice(String html_currentprice)
		{
			this.html_currentprice = html_currentprice;
			return this;
		}

		public Builder withHistory(String html_history)
		{
			this.html_history = html_history;
			return this;
		}

		public MailChimpObject buildContent()
		{
			OilCampaignContent content = new OilCampaignContent();
			content.setHtml_currentprice(html_currentprice);
			content.setHtml_history(html_history);
			return content;
		}
	}
}
