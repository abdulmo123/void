package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.dto.UpdateMyProfileDto;
import com.abdulmo123.void_user.dto.UserMeProfileDto;
import com.abdulmo123.void_user.dto.UserProfileDto;

public interface UserService {
    UserProfileDto getUserProfile(Long id);

    UserMeProfileDto getMyUserProfile(String authHeader);

    UpdateMyProfileDto updateMyUserProfile(String authHeader, UserMeProfileDto userMeProfileDto);
}
