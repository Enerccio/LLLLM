package io.github.enerccio.llllm.model.service.impl;

import io.github.enerccio.llllm.model.domain.Persona;
import io.github.enerccio.llllm.model.service.PersonaService;

public class PersonaServiceImpl extends ExtendedContentServiceImpl<Persona> implements PersonaService {

    @Override
    protected Class<Persona> getEntityClass() {
        return Persona.class;
    }

}
