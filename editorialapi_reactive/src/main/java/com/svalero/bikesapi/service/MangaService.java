package com.svalero.bikesapi.service;


import com.svalero.bikesapi.domain.Manga;
import com.svalero.bikesapi.exception.MangaNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MangaService {

    Flux<Manga> findAll();
    Flux<Manga> findByCodManga(String codManga);
    Mono<Manga> findManga(int id) throws MangaNotFoundException;
    Mono<Manga> addManga(Manga manga);
    Mono<Manga> deleteManga(int id) throws MangaNotFoundException;
    Mono<Manga> modifyManga(int id, Manga manga) throws MangaNotFoundException;
}
