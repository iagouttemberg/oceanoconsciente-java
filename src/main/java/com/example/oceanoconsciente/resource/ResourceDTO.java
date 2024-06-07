package com.example.oceanoconsciente.resource;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface ResourceDTO<Request, Response> {

    ResponseEntity<EntityModel<Response>> findById(Long id);

    ResponseEntity<EntityModel<Response>> save(Request r);
}
