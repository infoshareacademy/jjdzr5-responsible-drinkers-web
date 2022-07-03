package com.infoshareacademy.responsibledrinkersweb.entity;

import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = UserDAO.TABLE_NAME)
public class UserDAO {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_PREFIX = "u_";

    public UserDAO(String userName, Gender gender, String email, String password, LocalDate dateOfBirth, String role) {
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.active = 1;
    }

    @Id
    @GeneratedValue
    @org.hibernate.annotations.Type(type = "uuid-char")
    @Column(name = COLUMN_PREFIX + "id")
    private UUID id;

    @Size(min = 3, max = 25, message = "{validation.userName}")
    @NotBlank(message = "{validation.blank}")
    @Column(name = COLUMN_PREFIX + "username", unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_PREFIX + "gender")
    private Gender gender;

    @Email(message = "{validation.email}")
    @NotBlank(message = "{validation.blank}")
    @Column(name = COLUMN_PREFIX + "email", unique = true)
    private String email;

    @Size(min = 4, max = 25, message = "{validation.password}")
    @Column(name = COLUMN_PREFIX + "password", nullable = false)
    private String password;

    @Past(message = "{validation.pastDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.null.date}")
    @Column(name = COLUMN_PREFIX + "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "userDAO", cascade = CascadeType.ALL)
    private List<DrinkDAO> drinkDAO = new ArrayList<>();

    @Column(name = COLUMN_PREFIX + "role")
    private String role;

    @Column(name = COLUMN_PREFIX + "active")
    private int active;

    public List<String> getRolesList() {
        if (role.length()>0) {
            return Arrays.asList(role.split(","));
        }
        return new ArrayList<>();
    }

}
