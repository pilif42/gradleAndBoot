package com.example.gradleAndBoot;

import com.example.gradleAndBoot.domain.representation.CreatedObjectDTO;
import com.example.gradleAndBoot.error.CTPException;
import com.example.gradleAndBoot.service.ProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static com.example.gradleAndBoot.error.RestExceptionHandler.INVALID_JSON;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This is where we define our integration tests that do involve Spring.
 *
 * Good reads are:
 *    - https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 *    - https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradleAndBootApplicationTests {

	private static final UUID CASE_ID = UUID.fromString("7bc5d41b-0549-40b3-ba76-42f6d4cf3fd1");

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private ProcessService processService;

	@Test
	public void testEndpointWithBadJson() {
		ResponseEntity<CTPException> result = this.restTemplate.postForEntity(
				String.format("/testing/%s/request", CASE_ID), "{\"description\":\"a\",\"category\":\"BAD_CAT\",\"createdBy\":\"u\"}", CTPException.class);
		assertNotNull(result);
		assertTrue(result.getStatusCode().is4xxClientError());
		CTPException exceptionThrown = result.getBody();
		assertNotNull(exceptionThrown);
		assertEquals(CTPException.Fault.VALIDATION_FAILED, exceptionThrown.getFault());
		assertEquals(INVALID_JSON, exceptionThrown.getMessage());
	}

	@Test
	public void testEndpointWithGoodJson() throws Exception {
		// TODO
//		given(this.processService.someCall()).willReturn("mock");
//		this.restTemplate.postForEntity(
//				String.format("/testing/%s/request", CASE_ID), "{\"description\":\"a\",\"category\":\"BAD_CAT\",\"createdBy\":\"u\"}", CreatedObjectDTO.class);
		assertTrue(true);
	}
}
