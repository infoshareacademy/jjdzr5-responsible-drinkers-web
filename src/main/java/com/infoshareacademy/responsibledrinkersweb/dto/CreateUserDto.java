package com.infoshareacademy.responsibledrinkersweb.dto;

import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateUserDto {

    public CreateUserDto(String userName, Gender gender, String email, String password, LocalDate dateOfBirth, String role, Boolean isActive) {
        this.userName = userName;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.isActive = isActive;
    }

    private String userName;
    private Gender gender;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String role;
    private boolean isActive;

}
