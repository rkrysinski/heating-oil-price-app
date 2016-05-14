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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.StopWatch;

import com.qdeve.oilprice.ProcessingException;
import com.qdeve.oilprice.common.BootstrapApplicationTest;
import com.qdeve.oilprice.configuration.OilPriceProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@BootstrapApplicationTest
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@TestPropertySource(locations = "classpath:timeout.properties")
public class ContentHttpReaderTimeoutTest
{
	@Autowired
	@InjectMocks
	private ContentReader readerUnderTest;
	@Mock
	private OilPriceProperties properties;

	@Value("${local.server.port}")
	private int port;
	@Value("${oilprice.connectionTimeout}")
	private long connectionTimeout;
	@Value("${oilprice.connectionReadTimeout}")
	private long connectionReadTimeout;


	@Before
	public void initMock()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldFailWhenConnectionTimeoutOccurs()
	{
		// given 10.255.255.1:8080 which is an unroutable address (to yield a connection timeout)
		given(properties.getContentPath()).willReturn("http://10.255.255.1:8080");
		StopWatch watch = new StopWatch();

		// when
		watch.start();
		catchException(readerUnderTest).get();
		watch.stop();

		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("connect timed out")));
		assertThat(watch.getTotalTimeMillis(), greaterThanOrEqualTo(connectionTimeout));
	}

	@Test
	public void shouldFailWhenStaleConnection()
	{
		// given
		given(properties.getContentPath()).willReturn("http://localhost:" + port
				+ SomeRestController.REQUEST_MAPPING + SomeRestController.READ_TIMEOUT);
		StopWatch watch = new StopWatch();

		// when
		watch.start();
		catchException(readerUnderTest).get();
		watch.stop();

		// then
		assertThat(caughtException(), instanceOf(ProcessingException.class));
		assertThat(caughtException(), hasMessageThat(containsString("Read timed out")));
		assertThat(watch.getTotalTimeMillis(), greaterThanOrEqualTo(connectionReadTimeout));
	}

}
