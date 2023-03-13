package com.svalero.bikesapi.service;

import com.svalero.bikesapi.domain.Editorial;
import com.svalero.bikesapi.exception.EditorialNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.svalero.bikesapi.repository.EditorialRepository;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private EditorialRepository editorialRepository;

    @Override
    public Flux<Editorial> findAll() {
       return editorialRepository.findAll();
    }

    @Override
    public Flux<Editorial> findByCodEditor(String codEditor) {
        return editorialRepository.findByCodEditor(codEditor);
    }

    @Override
    public Mono<Editorial> findEditorial(String id) throws EditorialNotFoundException {
        return editorialRepository.findById(id).onErrorReturn(new Editorial());
    }

    @Override
    public Mono<Editorial> addEditorial(Editorial editorial) {
         return editorialRepository.save(editorial);
    }

    @Override
    public Mono<Editorial> deleteEditorial(String id) throws EditorialNotFoundException {
        Mono<Editorial> editorial = editorialRepository.findById(id).onErrorReturn(new Editorial());
        editorialRepository.delete(editorial.block());
        return editorial;
    }

    @Override
    public Mono<Editorial> modifyEditorial(String id, Editorial editorial) throws EditorialNotFoundException {
        Mono<Editorial> monoEditorial = editorialRepository.findById(id).onErrorReturn(new Editorial());

        Editorial editorials = monoEditorial.block();
        editorials.setId(editorial.getId());
        editorials.setName(editorial.getName());
        editorials.setNumEmpleados(editorial.getNumEmpleados());
        editorials.setNumSeries(editorial.getNumSeries());
        editorials.setNumSucursales(editorial.getNumSucursales());
        editorials.setCodEditor(editorial.getCodEditor());
        
        return editorialRepository.save(editorials);
    }
}
