package com.example.sapt.security;

import com.example.sapt.dto.UserAuthDTO;
import com.example.sapt.dto.UserReqDTO;
import com.example.sapt.entities.User;
import com.example.sapt.mapper.UserMapper;
import com.example.sapt.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.eclipse.jetty.util.security.Credential;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class LoginRegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${jwt.secret: }")
    String jwtSecret;

    @Value("${jwt.expiration.ms: }")
    String jwtExpiration;

    public LoginRegisterService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public String login(UserAuthDTO credentials) throws JoseException {
        String email = new String(Base64.getDecoder().decode(credentials.email()));
        String password = new String(Base64.getDecoder().decode(credentials.password()));

        String hashPassword = Credential.MD5.digest(password).replaceFirst("MD5:", "").toLowerCase();
        Optional<User> userdp = userRepository.findByEmail(email);

        User user = userdp.orElse(null);

        if(user!= null && (hashPassword.toLowerCase()).equals(user.getPassword())){
            return createJWToken(email);
        } else {
            return "401 : Unauthorized";
        }
    }

    public String register(UserReqDTO userReqDTO) throws JoseException {
        String email = new String(Base64.getDecoder().decode(userReqDTO.email())); //email
        String password = new String(Base64.getDecoder().decode(userReqDTO.password())); //parola

        String hashPassword = Credential.MD5.digest(password).replaceFirst("MD5:", "").toLowerCase();
        Optional<User> userdp = userRepository.findByEmail(email);

        if(userdp.isPresent()){
            return "400: Bad request";
        }

        User user = userMapper.toEntity(userReqDTO);
        user.setPassword(hashPassword);
        user.setEmail(email);
        userRepository.save(user);
        return createJWToken(email);
    }

    private String createJWToken(String email) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setIssuedAtToNow();
        claims.setExpirationTimeMinutesInTheFuture((float)
            Long.parseLong(jwtExpiration) / (1000 * 60));
        claims.setSubject(email);

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(new AesKey(jwtSecret.getBytes(StandardCharsets.UTF_8)));
        return jws.getCompactSerialization();
    }
}
