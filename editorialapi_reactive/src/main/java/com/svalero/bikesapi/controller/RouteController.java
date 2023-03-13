package com.svalero.bikesapi.controller;


import com.svalero.bikesapi.domain.Manga;

import com.svalero.bikesapi.exception.ErrorResponse;
import com.svalero.bikesapi.exception.MangaNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import com.svalero.bikesapi.service.MangaService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import reactor.core.publisher.Flux;

@RestController
public class RouteController {

    private final Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private MangaService mangaService;

     @GetMapping("/mangas")
    public ResponseEntity<Flux<Manga>> getMangaByCodManga(@RequestParam(name = "codManga", defaultValue = "0") String codManga) {
        Flux<Manga> mangas;

        if (codManga == null) {
            mangas = mangaService.findAll();
        } else {
            mangas = mangaService.findByCodManga(codManga);
        }
        return ResponseEntity.ok(mangas);
    }


    @DeleteMapping("/mangas/{id}")
    public ResponseEntity<Mono<Manga>> removeManga(@PathVariable int id) throws MangaNotFoundException {
        Mono<Manga> manga = mangaService.deleteManga(id);
        return ResponseEntity.ok(manga);
    }

    @PostMapping("/mangas")
    public ResponseEntity<Mono<Manga>> addManga(@Valid @RequestBody Manga manga) {
        Mono<Manga> newManga = mangaService.addManga(manga);
        return ResponseEntity.ok(newManga);
    }

    @PutMapping("/mangas/{id}")
    public ResponseEntity<Mono<Manga>> modifyManga(@RequestBody Manga manga, @PathVariable  int id) throws MangaNotFoundException {
        // TODO Falta controlar algún error 400
        Mono<Manga> newManga = mangaService.modifyManga(id, manga);
        return ResponseEntity.ok(newManga);
    }

    @ExceptionHandler(MangaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMangaNotFoundException(MangaNotFoundException bnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, bnfe.getMessage());
        logger.error(bnfe.getMessage(), bnfe);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // TODO Más tipos de excepciones que puedan generar errores

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = ErrorResponse.generalError(999, "Internal server error");
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }
}
