package com.svalero.bikesapi.repository;

import com.svalero.bikesapi.domain.Editorial;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EditorialRepository extends ReactiveMongoRepository<Editorial, String> {

    Flux<Editorial> findAll();
    Editorial findByName(String name);
    Flux<Editorial> findByCodEditor(String codEditor);
    
}
