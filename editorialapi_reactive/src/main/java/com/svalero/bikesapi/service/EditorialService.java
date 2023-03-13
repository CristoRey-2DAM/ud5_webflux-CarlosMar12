package com.svalero.bikesapi.service;

import com.svalero.bikesapi.domain.Editorial;
import com.svalero.bikesapi.exception.EditorialNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EditorialService {

    Flux<Editorial> findAll();
    Flux<Editorial> findByCodEditor(String codEditor);
    Mono<Editorial> findEditorial(String id) throws EditorialNotFoundException;
    Mono<Editorial> addEditorial(Editorial editorial);
    Mono<Editorial> deleteEditorial(String id) throws EditorialNotFoundException;
    Mono<Editorial> modifyEditorial(String id, Editorial editorial) throws EditorialNotFoundException;
}
