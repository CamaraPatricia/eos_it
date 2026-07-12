package com.example.sapt.services;

import com.example.sapt.cutomDTO.UpdateRequest;
import com.example.sapt.cutomDTO.UserUpdateRequest;
import com.example.sapt.dto.UserDTO;
import com.example.sapt.entities.User;
import com.example.sapt.mapper.UserMapper;
import com.example.sapt.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
