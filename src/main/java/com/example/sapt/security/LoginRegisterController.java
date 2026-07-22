package com.example.sapt.security;

import com.example.sapt.dto.UserAuthDTO;
import com.example.sapt.dto.UserReqDTO;
import lombok.RequiredArgsConstructor;
import org.jose4j.lang.JoseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginRegisterController {
    private final LoginRegisterService loginRegisterService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAuthDTO credentials) throws JoseException {
        String response = loginRegisterService.login(credentials);
        if(response.contains("401")){
            return ResponseEntity.status(401).body("401  Unauthorized");
        } else {
            return ResponseEntity.ok(response);
        }
    }

//    tema 9
//    add user(register), tot flow-ul cu Controller, Service, Respository
//    verificare email-unic
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserReqDTO userReqDTO) throws JoseException {
        String response = loginRegisterService.register(userReqDTO);
        if(response.contains("400")) {
            return ResponseEntity.status(400).body("400 Bad request");
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
