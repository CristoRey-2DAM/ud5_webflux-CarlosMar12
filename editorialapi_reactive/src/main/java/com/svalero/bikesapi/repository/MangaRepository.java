package com.svalero.bikesapi.repository;

import com.svalero.bikesapi.domain.Editorial;
import com.svalero.bikesapi.domain.Manga;
import java.util.Set;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MangaRepository extends ReactiveMongoRepository<Manga, String> {

    Flux<Manga> findAll();
    Manga findByGenero(String genero);
    Set<Manga> findByCodManga(String codManga);
    Manga findById(int id);
}
