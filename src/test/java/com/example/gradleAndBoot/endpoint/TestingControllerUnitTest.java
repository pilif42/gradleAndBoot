package com.example.gradleAndBoot.endpoint;

import com.example.gradleAndBoot.BeanMapper;
import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;
import com.example.gradleAndBoot.error.CTPException;
import com.example.gradleAndBoot.error.RestExceptionHandler;
import com.example.gradleAndBoot.service.ProcessService;
import com.example.gradleAndBoot.utility.CustomObjectMapper;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.UUID;

import static com.example.gradleAndBoot.service.impl.ProcessServiceImpl.DESCRIPTION;
import static com.example.gradleAndBoot.service.impl.ProcessServiceImpl.SYSTEM;
import static com.example.gradleAndBoot.utility.MockMvcControllerAdviceHelper.mockAdviceFor;
import static com.example.gradleAndBoot.utility.MvcHelper.postJson;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestingControllerUnitTest {

  private static final UUID CASE_ID = UUID.fromString("7bc5d41b-0549-40b3-ba76-42f6d4cf3fd1");
  private static final String METHOD_UNDER_TEST = "testingPost";

  @InjectMocks
  private TestingController testingController;

  @Mock
  private ProcessService processService;

  @Spy
  private MapperFacade mapperFacade = new BeanMapper();

  private MockMvc mockMvc;

  /**
   * Set up of tests
   * @throws Exception exception thrown
   */
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = MockMvcBuilders
        .standaloneSetup(testingController)
        .setHandlerExceptionResolvers(mockAdviceFor(RestExceptionHandler.class))
        .setMessageConverters(new MappingJackson2HttpMessageConverter(new CustomObjectMapper()))
        .build();

    // Line below if you need to load a list of Cases for a service mocked response for instance.
    // You would have a file called TestingControllerUnitTest.Case.json under resources/com/example/gradleAndBoot/endpoint
    // in which you would define in json all the Cases you want to load.
//    this.caseResults = FixtureHelper.loadClassFixtures(Case[].class);
  }

  /**
   * a test providing bad json
   * @throws Exception if the postJson fails
   */
  @Test
  public void testingPostBadJson() throws Exception {
    ResultActions actions = mockMvc.perform(postJson(String.format("/testing/%s/request", CASE_ID),
        getFileContent("com/example/gradleAndBoot/endpoint/testingPostBad.json")));

    actions.andExpect(status().isBadRequest());
    actions.andExpect(handler().handlerType(TestingController.class));
    actions.andExpect(handler().methodName(METHOD_UNDER_TEST));
    actions.andExpect(jsonPath("$.error.code", is(CTPException.Fault.VALIDATION_FAILED.name())));
    actions.andExpect(jsonPath("$.error.message", isA(String.class)));
    actions.andExpect(jsonPath("$.error.timestamp", isA(String.class)));
  }

  /**
   * a test providing good json
   * @throws Exception if the postJson fails
   */
  @Test
  public void testingPostGoodJson() throws Exception {
    CreatedObject result = CreatedObject.builder().createdBy(SYSTEM).internalDescription(DESCRIPTION).build();
    when(processService.process(any(Request.class))).thenReturn(result);

    ResultActions actions = mockMvc.perform(postJson(String.format("/testing/%s/request", CASE_ID),
        getFileContent("com/example/gradleAndBoot/endpoint/testingPostGood.json")));

    actions.andExpect(status().is2xxSuccessful());
    actions.andExpect(handler().handlerType(TestingController.class));
    actions.andExpect(handler().methodName(METHOD_UNDER_TEST));
    actions.andExpect(jsonPath("$.createdBy", is(SYSTEM)));
    actions.andExpect(jsonPath("$.description", is(DESCRIPTION)));
  }

  private String getFileContent(String fileName) {
    String result = "";

    ClassLoader classLoader = getClass().getClassLoader();
    try {
      result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result;
  }
}
