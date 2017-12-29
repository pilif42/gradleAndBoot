package com.example.gradleAndBoot.service.impl;

import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;
import com.example.gradleAndBoot.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessServiceImpl implements ProcessService {

  public static final String DESCRIPTION = "some description";
  public static final String SYSTEM = "system";

  @Override
  public CreatedObject process(Request request) {
    log.debug("Entering process with request {}", request);
    return CreatedObject.builder().createdBy(SYSTEM).internalDescription(DESCRIPTION).build();
  }
}
