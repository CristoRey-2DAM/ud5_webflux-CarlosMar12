package com.svalero.bikesapi.controller;

import com.svalero.bikesapi.domain.Editorial;
import com.svalero.bikesapi.domain.Manga;

import com.svalero.bikesapi.exception.EditorialNotFoundException;
import com.svalero.bikesapi.exception.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;

import java.util.Map;


import com.svalero.bikesapi.service.EditorialService;
import com.svalero.bikesapi.service.MangaService;

@RestController
public class EditorialController {

    private final Logger logger = LoggerFactory.getLogger(EditorialController.class);

    @Autowired
    private EditorialService editorialService;


    @GetMapping("/editoriales")
    public ResponseEntity<Flux<Editorial>> getEditorialByCodEditor(@RequestParam(name = "codEditor", defaultValue = "0") String codEditor) {
        Flux<Editorial> editoriales;

        if (codEditor == null) {
            editoriales = editorialService.findAll();
        } else {
            editoriales = editorialService.findByCodEditor(codEditor);
        }
        return ResponseEntity.ok(editoriales);
    }


    @DeleteMapping("/editoriales/{id}")
    public ResponseEntity<Mono<Editorial>> removeEditorial(@PathVariable String id) throws EditorialNotFoundException {
        Mono<Editorial> editorial = editorialService.deleteEditorial(id);
        return ResponseEntity.ok(editorial);
    }

    @PostMapping("/editoriales")
    public ResponseEntity<Mono<Editorial>> addEditorial(@Valid @RequestBody Editorial editorial) {
        Mono<Editorial> newEditorial = editorialService.addEditorial(editorial);
        return ResponseEntity.ok(newEditorial);
    }

    @PutMapping("/editoriales/{id}")
    public ResponseEntity<Mono<Editorial>> modifyEditorial(@RequestBody Editorial editorial, @PathVariable String id) throws EditorialNotFoundException {
        // TODO Falta controlar algún error 400
        Mono<Editorial> newEditorial = editorialService.modifyEditorial(id, editorial);
        return ResponseEntity.ok(newEditorial);
    }

    @ExceptionHandler(EditorialNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEditorialNotFoundException(EditorialNotFoundException bnfe) {
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
