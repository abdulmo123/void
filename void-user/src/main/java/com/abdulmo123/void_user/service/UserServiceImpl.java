package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.UserException;
import com.abdulmo123.void_user.config.AppConfig;
import com.abdulmo123.void_user.dto.ChangePasswordRequestDto;
import com.abdulmo123.void_user.dto.UpdateMyProfileDto;
import com.abdulmo123.void_user.dto.UserMeProfileDto;
import com.abdulmo123.void_user.dto.UserProfileDto;
import com.abdulmo123.void_user.model.User;
import com.abdulmo123.void_user.repository.UserRepository;
import com.abdulmo123.void_user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileDto getUserProfile(Long id) {
        User user = userRepository.getUserProfileById(id)
                .orElseThrow(() -> new UserException("User with id {" + id + "} not found"));

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(user.getId());
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setCrtTs(user.getCrtTs());

        return userProfileDto;
    }

    @Override
    public UserMeProfileDto getMyUserProfile(String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserException("User not found!"));

        UserMeProfileDto userMeProfileDto = new UserMeProfileDto();
        userMeProfileDto.setId(user.getId());
        userMeProfileDto.setFirstName(user.getFirstName());
        userMeProfileDto.setLastName(user.getLastName());
        userMeProfileDto.setUsername(user.getUsername());
        userMeProfileDto.setEmail(user.getEmail());
        userMeProfileDto.setCrtTs(user.getCrtTs());

        return userMeProfileDto;
    }

    @Override
    public UpdateMyProfileDto updateMyUserProfile(String authHeader, UserMeProfileDto userMeProfileDto) {
        String token = authHeader.substring(7);

        String username = jwtUtil.extractUsername(token);


        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserException("User not found!"));

        // check username is not already taken by another user
        if (userMeProfileDto.getUsername() != null &&
                !userMeProfileDto.getUsername().equals(user.getUsername())) {
            userRepository.findByUsernameOrEmail(userMeProfileDto.getUsername())
                    .ifPresent(u -> { throw new UserException("Username already taken"); });
        }

        // check email is not already taken by another user
        if (userMeProfileDto.getEmail() != null &&
                !userMeProfileDto.getEmail().equals(user.getEmail())) {
            userRepository.findByUsernameOrEmail(userMeProfileDto.getEmail())
                    .ifPresent(u -> { throw new UserException("Email already taken"); });
        }

        // only update fields that are provided
        if (userMeProfileDto.getUsername() != null) user.setUsername(userMeProfileDto.getUsername());
        if (userMeProfileDto.getFirstName() != null) user.setFirstName(userMeProfileDto.getFirstName());
        if (userMeProfileDto.getLastName() != null) user.setLastName(userMeProfileDto.getLastName());
        if (userMeProfileDto.getEmail() != null) user.setEmail(userMeProfileDto.getEmail());

        User savedUser = userRepository.save(user);

        UpdateMyProfileDto dto = new UpdateMyProfileDto();
        dto.setFirstName(savedUser.getFirstName());
        dto.setLastName(savedUser.getLastName());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        dto.setToken(jwtUtil.generateToken(savedUser));

        return dto;
    }

    @Override
    public String updateMyUserPassword(String authHeader, ChangePasswordRequestDto changePasswordRequestDto) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        User user = userRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UserException("User not found!"));

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new UserException("Current password is incorrect!");
        }

        // TODO: maybe don't need to do this because I can simply disable whatever form exists on the frontend when both formControl values are not the same
        /*if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmNewPassword())) {
            throw new UserException("New passwords do not match!");
        }*/

        user.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(user);

        return "Password updated successfully for user: " + user.getUsername();
    }
}
