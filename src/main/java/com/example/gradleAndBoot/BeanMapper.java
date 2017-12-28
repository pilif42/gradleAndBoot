package com.example.gradleAndBoot;

import com.example.gradleAndBoot.domain.model.CreatedObject;
import com.example.gradleAndBoot.domain.model.Request;
import com.example.gradleAndBoot.domain.representation.CreatedObjectDTO;
import com.example.gradleAndBoot.domain.representation.RequestDTO;
import org.springframework.stereotype.Component;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

/**
 * The bean mapper that maps to/from DTOs and JPA entity types.
 *
 */
@Component
public class BeanMapper extends ConfigurableMapper {

  /**
   * Setup the mapper for all of our beans. Only fields having non identical names need mapping if we also use
   * byDefault() following.
   *
   * @param factory the factory to which we add our mappings
   */
  protected final void configure(final MapperFactory factory) {

    factory
        .classMap(Request.class, RequestDTO.class)
        .byDefault()
        .register();

    factory
        .classMap(CreatedObject.class, CreatedObjectDTO.class)
        .field("internalDescription", "description")
        .byDefault()
        .register();
  }
}