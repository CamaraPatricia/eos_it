package com.example.sapt.services;

import com.example.sapt.cutomDTO.UpdateRequest;
import com.example.sapt.cutomDTO.UserUpdateRequest;
import com.example.sapt.dto.AuthResponse;
import com.example.sapt.dto.UserAuthDTO;
import com.example.sapt.dto.UserDTO;
import com.example.sapt.dto.UserReqDTO;
import com.example.sapt.entities.User;
import com.example.sapt.mapper.UserMapper;
import com.example.sapt.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service pentru useri
 *
 * Operatii:
 *  - getUsers: returneaza toti userii
 *  - update --> orice update trebuie sa modifice last_update_date ( by ramane default)
 *    * username
 *    * is internal
 *   -delete
 *      * unul singur by id
 */

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTO> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    public UserDTO getUserById(Long id) {
        log.info("Getting user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }

    public AuthResponse getAuthUser(UserAuthDTO authDTO) {
        Optional<User> tryUser = userRepository.findByEmail(authDTO.email());

        if(tryUser.isPresent()){
            User user = tryUser.get();

            if(authDTO.password().equals(user.getPassword())){
                return AuthResponse.builder()
                        .user(userMapper.toDTO(user))
                        .message("OK")
                        .build();
            } else {
                return AuthResponse.builder()
                        .user(null)
                        .message("Password is incorrect")
                        .build();
            }
        } else {
            return AuthResponse.builder()
                    .user(null)
                    .message("User not found!")
                    .build();
        }
    }

    public UserDTO updateUser(long id, UserUpdateRequest updateRequest) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found with id: " + id));

        user.setUsername(updateRequest.username());
        user.setInternal(updateRequest.isInternal());
        user.setLastUpdateDate(LocalDateTime.now());
        return userMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
        log.info("Sucssessfully deleted!");
    }

    public UserDTO createUser(UserReqDTO userDTO) {
        log.info("Creating user: {}", userDTO);
        User user = userMapper.toEntity(userDTO);

//        user.setCreationDate(LocalDateTime.now());
//        user.setCreatedBy("USER");
//        user.setLastUpdatedBy("USER");
//        user.setLastUpdateDate(LocalDateTime.now());
//
//        user.setUsername(userDTO.username());
//        user.setBirthDate(userDTO.birthDate());
//        user.setInternal(false);
//        user.setEmail(userDTO.email());
//        user.setPassword(userDTO.password());

        return userMapper.toDTO(userRepository.save(user));
    }
}
