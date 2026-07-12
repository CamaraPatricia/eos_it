package com.example.sapt.mapper;

import com.example.sapt.dto.UserDTO;
import com.example.sapt.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user){
        return UserDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .birthDate(user.getBirthDate())
                .isInternal(user.isInternal())
                .creationDate(user.getCreationDate())
                .lastUpdateDate(user.getLastUpdateDate())
                .build();
    }

    public User toEntity(UserDTO userDTO){
        return User.builder()
                .username(userDTO.username())
                .birthDate(userDTO.birthDate())
                .isInternal(userDTO.isInternal())
                .creationDate(userDTO.creationDate())
                .lastUpdateDate(userDTO.lastUpdateDate())
                .build();
    }
}
