package com.springboot.bankbackend.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserRequest {
    @NotNull
    @NotBlank(message = "Username must not be blank")
    private String username;
    @NotNull
    @NotBlank(message = "Password must not be blank")
    private String password;

    public CreateUserRequest() {
        // Default constructor
    }


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
