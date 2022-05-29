package com.infoshareacademy.responsibledrinkersweb.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 25, message = "{validation.userName}")
    @NotBlank(message = "{validation.blank}")
    private String userName;

    private String gender;

    @Email(message = "{validation.email}")
    @NotBlank(message = "{validation.blank}")
    private String email;

    @Size(min = 8, max = 25, message = "{validation.password}")
    private String password;

    @Past(message = "{validation.pastDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.null.date}")
    private LocalDate dateOfBirth;
}
