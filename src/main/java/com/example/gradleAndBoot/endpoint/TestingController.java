package com.example.gradleAndBoot.endpoint;

import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;
import com.example.gradleAndBoot.domain.representation.CreatedObjectDTO;
import com.example.gradleAndBoot.domain.representation.RequestDTO;
import com.example.gradleAndBoot.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/testing", consumes = "application/json", produces = "application/json")
public class TestingController {

  @Autowired
  private ProcessService processService;

  @Qualifier("beanMapper")
  @Autowired
  private MapperFacade mapperFacade;

  @RequestMapping(value = "/{caseId}/request", method = RequestMethod.POST)
  public ResponseEntity<CreatedObjectDTO> testingPost(@PathVariable("caseId") final UUID caseId,
      @RequestBody @Valid final RequestDTO requestDTO,
      BindingResult bindingResult) {
    log.debug("Entering testingPost with caseId {} and requestDTO {}", caseId, requestDTO);

    Request request = mapperFacade.map(requestDTO, Request.class);
    CreatedObject createdObject = processService.process(request);
    CreatedObjectDTO mappedCreatedObject = mapperFacade.map(createdObject, CreatedObjectDTO.class);

    String newResourceUrl = ServletUriComponentsBuilder
        .fromCurrentRequest().buildAndExpand(caseId).toUri().toString();
    return ResponseEntity.created(URI.create(newResourceUrl)).body(mappedCreatedObject);
  }
}
