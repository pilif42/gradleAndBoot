package com.example.gradleAndBoot.service;

import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;

public interface ProcessService {
  CreatedObject process(Request request);
}
