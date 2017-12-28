package com.example.gradleAndBoot.endpoint;

import com.example.gradleAndBoot.domain.representation.CreatedObjectDTO;
import com.example.gradleAndBoot.domain.representation.RequestDTO;
import lombok.extern.slf4j.Slf4j;
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

  @RequestMapping(value = "/{caseId}/request", method = RequestMethod.POST)
  public ResponseEntity<CreatedObjectDTO> testingPost(@PathVariable("caseId") final UUID caseId,
      @RequestBody @Valid final RequestDTO requestDTO,
      BindingResult bindingResult) {
    log.debug("Entering testingPost with caseId {} and requestDTO {}", caseId, requestDTO);

    // TODO Autowire a service and use here to process requestDTO
    CreatedObjectDTO mappedCreatedObject = new CreatedObjectDTO();

    String newResourceUrl = ServletUriComponentsBuilder
        .fromCurrentRequest().buildAndExpand(UUID.randomUUID()).toUri().toString();

    return ResponseEntity.created(URI.create(newResourceUrl)).body(mappedCreatedObject);
  }
}
