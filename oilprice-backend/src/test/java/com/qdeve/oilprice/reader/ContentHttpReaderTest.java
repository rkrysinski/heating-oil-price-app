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
import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.hasMessageThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.common.BootstrapApplicationTest;
import com.qdeve.oilprice.configuration.OilPriceProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapApplicationTest
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class ContentHttpReaderTest
{
	@Autowired
	@InjectMocks
	private ContentReader readerUnderTest;
	@Mock
	private OilPriceProperties properties;

	@Value("${local.server.port}")
	private int port;

	@Before
	public void initMock()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldProperlyReadContentFromServer()
	{
		// given
		given(properties.getContentPath()).willReturn("http://localhost:" + port + SomeRestController.REQUEST_MAPPING + SomeRestController.OK);

		// when
		String content = readerUnderTest.get();

		// then
		assertThat(content, equalTo("OK response"));
	}

	@Test
	public void shouldFailWhenPageNotFoundOnServer()
	{
		// given
		given(properties.getContentPath()).willReturn("http://localhost:" + port + "/non_existing_path");

		// when
		catchException(readerUnderTest).get();

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("404 Not Found")));
	}
}
