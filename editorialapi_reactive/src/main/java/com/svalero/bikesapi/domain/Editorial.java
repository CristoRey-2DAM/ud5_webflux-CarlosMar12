package com.svalero.bikesapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import reactor.core.publisher.Mono;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "editoriales")
public class Editorial {

    @Id
    private String id;
    
    @Field
    private String name;
    
    @Field
    @NotNull
    @PositiveOrZero
    private int numEmpleados;
    
    @Field
    @NotNull
    private int numSucursales;
    
    @Field
    private int numSeries;
    
    @Field
    private String codEditor;






}
