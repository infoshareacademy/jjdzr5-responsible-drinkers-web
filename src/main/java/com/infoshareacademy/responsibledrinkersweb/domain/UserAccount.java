package com.infoshareacademy.responsibledrinkersweb.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class UserAccount {

    @Size(min = 4, max = 25, message = "{validation.userName}")
    private String userName;

    private String gender;

    @Email(message = "{validation.email}")
    @NotBlank(message = "{validation.blank}")
    private String email;

    @Size(min = 8, max = 25, message = "{validation.password}")
    private String password;

    @Size(min = 8, max = 25, message = "{validation.password}")
    private String repeatPassword;

    @Past(message = "{validation.pastDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{validation.null.date}")
    private LocalDate dateOfBirth;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(userName, that.userName) && Objects.equals(gender, that.gender) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(repeatPassword, that.repeatPassword) && Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, gender, email, password, repeatPassword, dateOfBirth);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "userName='" + userName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repeatPassword='" + repeatPassword + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
