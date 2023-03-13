package com.svalero.bikesapi.service;




import com.svalero.bikesapi.domain.Manga;
import com.svalero.bikesapi.exception.MangaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.svalero.bikesapi.repository.MangaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MangaServiceImpl implements MangaService {

    
    @Autowired
    private MangaRepository mangaRepository;
    
    
    @Override
    public Flux<Manga> findAll() {
        return mangaRepository.findAll();
    }

    @Override
    public Flux<Manga> findByCodManga(String codManga) {
        return (Flux<Manga>) mangaRepository.findByCodManga(codManga);
    }

    @Override
    public Mono<Manga> findManga(int id) throws MangaNotFoundException {
        return mangaRepository.findById(id).onErrorReturn(new Manga());
    }

    @Override
    public Mono<Manga> addManga(Manga manga) {
          return mangaRepository.save(manga);
    }

    @Override
    public Mono<Manga> deleteManga(int id) throws MangaNotFoundException {
          Mono<Manga> manga = mangaRepository.findById(id).onErrorReturn(new Manga());
        mangaRepository.delete(manga.block());
        return manga;
    }

    @Override
    public Mono<Manga> modifyManga(int id, Manga manga) throws MangaNotFoundException {
          Mono<Manga> monoEditorial = mangaRepository.findById(id).onErrorReturn(new Manga());

        Manga mangas = monoEditorial.block();
        mangas.setId(manga.getId());
        mangas.setGenero(manga.getGenero());
        mangas.setFrecPublicacion(manga.getFrecPublicacion());
        mangas.setAnime(manga.getAnime());
        mangas.setCodManga(manga.getCodManga());
        mangas.setEstiloDibujo(manga.getEstiloDibujo());
        
        return mangaRepository.save(mangas);
    }




   

}
