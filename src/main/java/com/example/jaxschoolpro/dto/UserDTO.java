package com.example.jaxschoolpro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotNull
    @Size(min=3,max=20)
    private String username;

    @Pattern(regexp = "^(?=.*?[a-z])(?=.*[A-Z])(?=.*?\\d)(.{8,})$",
            message ="Password must meet specific requirements")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
