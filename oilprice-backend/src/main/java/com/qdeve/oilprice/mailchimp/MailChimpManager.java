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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecwid.mailchimp.MailChimpClient;
import com.ecwid.mailchimp.MailChimpException;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignCreateMethod;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignSendNowMethod;
import com.ecwid.mailchimp.method.v1_3.campaign.CampaignType;
import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.configuration.OilPriceProperties;

/**
 * Integration with MailChimp. All interactions from application to external
 * mailing system goes through this class.
 *
 * @author Roman Krysinski
 */
@Component
public class MailChimpManager
{
	@Autowired
	private OilPriceProperties appProperties;
	@Autowired
	private MailChimpClient mailChimpClient;

	public boolean send(String currentPrice, String history) throws ProcessingException
	{
		try
		{
			String cid = createCampaign(currentPrice, history);
			return sendCampaign(cid);
		} catch (IOException | MailChimpException e)
		{
			throw new ProcessingException("Failed to notify subscribers about oil price, reason: "
					+ e.getMessage(), e);
		}
	}

	private String createCampaign(String currentPrice, String history) throws IOException, MailChimpException
	{
		CampaignCreateMethod campaignCreate = new CampaignCreateMethod();
		campaignCreate.apikey = appProperties.getMailchimp().getApiKey();
		campaignCreate.type = CampaignType.regular;
		campaignCreate.options = appProperties.getMailchimp().getCampaign().toCampaignOptions();
		campaignCreate.content = OilCampaignContent.builder()
				.withCurrentPrice(currentPrice)
				.withHistory(history)
				.buildContent();

		return mailChimpClient.execute(campaignCreate);

	}

	private boolean sendCampaign(String cid) throws IOException, MailChimpException
	{
		CampaignSendNowMethod campaignSendNow = new CampaignSendNowMethod();
		campaignSendNow.apikey = appProperties.getMailchimp().getApiKey();
		campaignSendNow.cid = cid;

		return mailChimpClient.execute(campaignSendNow);
	}
}
