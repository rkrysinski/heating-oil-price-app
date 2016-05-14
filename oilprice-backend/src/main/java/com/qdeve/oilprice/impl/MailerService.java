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
package com.qdeve.oilprice.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.mailchimp.MailChimpManager;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * Triggers email notifications to subscribers on daily basis, excluding weekends.
 *
 * @author Roman Krysinski
 */
@Component
public class MailerService
{
	private static final Logger LOG = LoggerFactory.getLogger(MailerService.class);

	@Autowired
	private MailChimpManager mailManager;
	@Autowired
	private Configuration templateConfig;
	@Autowired
	private PriceCalcComponent calcComponent;

	/**
	 * Send notification at 8:00 AM MON-FRI.
	 */
	@Scheduled(cron = "0 9 * * * MON-FRI")
	public void sendNotificationToSubscribers()
	{
		try
		{
			mailManager.send(getCurrentPriceStr(), getHistoryHtmlTable());
		} catch (ProcessingException | IOException | TemplateException e)
		{
			LOG.error("Failed to send notification to subscribers, reason:" + e.getMessage(), e);
		}
	}

	private String getHistoryHtmlTable() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("prices", calcComponent.getTopHistoricalPrices());

		Template template = templateConfig.getTemplate("history_table.ftl");

		StringWriter out = new StringWriter(1024);
		template.process(input, out);
		return out.toString();
	}

	private String getCurrentPriceStr()
	{

		return calcComponent.getCurrentPrice().toString();
	}
}
