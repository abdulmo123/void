package com.abdulmo123.void_user.dto;

import lombok.Data;

@Data
public class UpdateMyProfileDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String token;
}
