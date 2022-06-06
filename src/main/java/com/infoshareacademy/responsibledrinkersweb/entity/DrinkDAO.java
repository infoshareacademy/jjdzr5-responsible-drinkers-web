package com.infoshareacademy.responsibledrinkersweb.entity;

import com.infoshareacademy.drinkers.domain.drink.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = DrinkDAO.TABLE_NAME)
public class DrinkDAO {

    public static final String TABLE_NAME = "drink";
    public static final String COLUMN_PREFIX = "d_";

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    @Column(name = COLUMN_PREFIX + "id")
    private UUID id;

    @NotNull
    @Positive
    @Column(name = COLUMN_PREFIX + "no")
    private int idDrink;

    @Size(min = 2, max = 35, message = "{walidacja.nazwa}")
    @Column(name = COLUMN_PREFIX + "name")
    private String strDrink;

    @Size(min = 2, max = 25, message = "{walidacja.kategoria}")
    @Column(name = COLUMN_PREFIX + "category")
    private String strCategory;

    @Column(name = COLUMN_PREFIX + "alcoholic")
    private String strAlcoholic;


    @Size(min = 2, max = 25, message = "{walidacja.nazwa}")
    @Column(name = COLUMN_PREFIX + "glass")
    private String strGlass;

    @Size(min = 2, max = 500, message = "{walidacja.przepis}")
    @Column(name = COLUMN_PREFIX + "instruction")
    private String strInstructions;

    @Column(name = COLUMN_PREFIX + "thumb")
    private String strDrinkThumb;

    @Size(min = 2, max = 500, message = "{walidacja.skladnik}")
    @Column(name = COLUMN_PREFIX + "ingredients")
    private String strIngredient;


    @PastOrPresent
    @Column(name = COLUMN_PREFIX + "date_modified")
    private LocalDateTime dateModified;

    @Column(name = COLUMN_PREFIX + "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = UserDAO.COLUMN_PREFIX + "id")
    private UserDAO userDAO;

}
