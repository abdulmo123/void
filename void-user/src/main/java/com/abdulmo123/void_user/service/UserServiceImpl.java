package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.UserException;
import com.abdulmo123.void_user.dto.UserProfileDto;
import com.abdulmo123.void_user.model.User;
import com.abdulmo123.void_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserProfileDto getUserProfile(Long id) {
        User user = userRepository.findByUserId(id)
                .orElseThrow(() -> new UserException("User with id {" + id + "} not found"));

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(user.getId());
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setCrtTs(user.getCrtTs());

        return userProfileDto;
    }
}
