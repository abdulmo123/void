package com.abdulmo123.void_user.controller;

import com.abdulmo123.void_user.dto.ChangePasswordRequestDto;
import com.abdulmo123.void_user.dto.UpdateMyProfileDto;
import com.abdulmo123.void_user.dto.UserMeProfileDto;
import com.abdulmo123.void_user.dto.UserProfileDto;
import com.abdulmo123.void_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/find/{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfile(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeProfileDto> getMyUserProfile(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyUserProfile(authHeader));
    }

    @PutMapping("/me")
    public ResponseEntity<UpdateMyProfileDto> updateMyUserProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserMeProfileDto userMeProfileDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateMyUserProfile(authHeader, userMeProfileDto));
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> updateMyUserPassword(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateMyUserPassword(authHeader, changePasswordRequestDto));
    }
}
