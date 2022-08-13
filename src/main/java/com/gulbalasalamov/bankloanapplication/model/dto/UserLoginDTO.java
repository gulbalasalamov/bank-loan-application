package com.gulbalasalamov.bankloanapplication.model.dto;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserLoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
