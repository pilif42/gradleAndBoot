package com.example.gradleAndBoot.endpoint;

import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;
import com.example.gradleAndBoot.domain.representation.CreatedObjectDTO;
import com.example.gradleAndBoot.domain.representation.RequestDTO;
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

/**
 * This is where we define our integration tests that do involve Spring.
 *
 * Good reads are:
 *    - https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 *    - https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingControllerIntegrationTest {

  private static final String FORENAME = "Lionel";
  private static final String INT_TEST = "integration test";
  private static final String TEST_DESCRIPTION = "This is for an integration test.";
  private static final String TITLE = "Mr";

  private static final UUID CASE_ID = UUID.fromString("7bc5d41b-0549-40b3-ba76-42f6d4cf3fd1");

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private ProcessService processService;

	@Test
	public void testEndpointWithGet() {
		CreatedObject serviceResult = CreatedObject.builder().createdBy(INT_TEST).internalDescription(TEST_DESCRIPTION)
        .build();
		given(this.processService.process(null)).willReturn(serviceResult);

		ResponseEntity<CreatedObjectDTO> result = this.restTemplate.getForEntity("/testing/getme", CreatedObjectDTO.class);
		assertNotNull(result);
		assertTrue(result.getStatusCode().is2xxSuccessful());

		CreatedObjectDTO resultBody = result.getBody();
		assertNotNull(resultBody);
		assertEquals(INT_TEST, resultBody.getCreatedBy());
		assertEquals(TEST_DESCRIPTION, resultBody.getDescription());
	}

  /**
   * Test where we provide a valid RequestDTO.
   */
  @Test
  public void testEndpointWithPostPositiveScenario() {
    CreatedObject serviceResult = CreatedObject.builder().createdBy(INT_TEST).internalDescription(TEST_DESCRIPTION)
        .build();
    Request request = Request.builder().title(TITLE).forename(FORENAME).build();
    given(this.processService.process(eq(request))).willReturn(serviceResult);

    RequestDTO requestDTO = new RequestDTO(TITLE, FORENAME);
    ResponseEntity<CreatedObjectDTO> result = this.restTemplate.postForEntity(
    				String.format("/testing/%s/request", CASE_ID), requestDTO, CreatedObjectDTO.class);
    assertNotNull(result);
    assertTrue(result.getStatusCode().is2xxSuccessful());

    CreatedObjectDTO resultBody = result.getBody();
    assertNotNull(resultBody);
    assertEquals(INT_TEST, resultBody.getCreatedBy());
    assertEquals(TEST_DESCRIPTION, resultBody.getDescription());
  }

  /**
   * Test where we provide an invalid RequestDTO. See the empty forename.
   */
  @Test
  public void testEndpointWithPostNegativeScenario() {
    RequestDTO requestDTO = new RequestDTO(TITLE, "");
    ResponseEntity<CreatedObjectDTO> result = this.restTemplate.postForEntity(
        String.format("/testing/%s/request", CASE_ID), requestDTO, CreatedObjectDTO.class);
    assertNotNull(result);
    assertTrue(result.getStatusCode().is4xxClientError());
  }
}
