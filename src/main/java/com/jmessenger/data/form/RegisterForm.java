package com.jmessenger.data.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class RegisterForm {

    @NotEmpty(message = "Full name can not be blank.")
    private String fullName;

    @Email(message = "Email is not a valid email address.")
    @NotEmpty(message = "Email can not be blank.")
    private String email;

    @NotEmpty(message = "Country can not be blank.")
    private String country;

    @NotEmpty(message = "Date of birth can not be blank.")
    private String dob;

    @Size(min = 6, message = "Password is shorter.Minimum 6 symbol.")
    private String password;

    @NotEmpty(message = "Confirm Password can not be blank.")
    private String confirmPassword;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
