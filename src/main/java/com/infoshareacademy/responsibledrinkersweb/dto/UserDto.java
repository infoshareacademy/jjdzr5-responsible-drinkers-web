package com.infoshareacademy.responsibledrinkersweb.dto;

import com.infoshareacademy.responsibledrinkersweb.domain.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDto {

    private UUID id;
    private String userName;
    private Gender gender;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String role;
}
