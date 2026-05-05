package com.abdulmo123.void_user.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}
