package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.UserException;
import com.abdulmo123.void_user.dto.UserMeProfileDto;
import com.abdulmo123.void_user.dto.UserProfileDto;
import com.abdulmo123.void_user.model.User;
import com.abdulmo123.void_user.repository.UserRepository;
import com.abdulmo123.void_user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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
}
