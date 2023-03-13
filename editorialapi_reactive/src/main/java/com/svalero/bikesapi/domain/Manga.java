package com.svalero.bikesapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.svalero.bikesapi.exception.MangaNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "mangas")
public class Manga {

    @Id
    private String id;
    
    @Field
    private String genero;
    
    @Field
    private String frecPublicacion;
    
    
    @Field
    private String  Anime;
    
    @Field
    public String codManga;
    
    @Field
    public String estiloDibujo;

    public Mono<Manga> onErrorReturn(Manga manga) throws MangaNotFoundException {
       throw new MangaNotFoundException("No existe el manga con Id : " + manga.getId());
    }
    

}
