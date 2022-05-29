package com.infoshareacademy.responsibledrinkersweb.entity;

import com.infoshareacademy.drinkers.domain.drink.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "drinks")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private int idDrink;
    @Size(min = 2, max = 35, message = "{walidacja.nazwa}")
    private String strDrink;
    @Size(min = 2, max = 25, message = "{walidacja.kategoria}")
    private String strCategory;
    private String strAlcoholic;
    @Size(min = 2, max = 25, message = "{walidacja.nazwa}")
    private String strGlass;
    @Size(min = 2, max = 500, message = "{walidacja.przepis}")
    private String strInstructions;
    private String strDrinkThumb;
    @Size(min = 2, max = 25, message = "{walidacja.skladnik}")
    private String strIngredient1;

    @Size(min = 2, max = 25, message = "{walidacja.skladnik}")
    private String strIngredient2;

    private String strIngredient3;
    private String strIngredient4;
    private String strIngredient5;

    @PastOrPresent
    private LocalDateTime dateModified;

    private Status status;

}
