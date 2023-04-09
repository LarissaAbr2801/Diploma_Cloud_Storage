package com.example.diploma_cloud_storage.security.service;

import com.example.diploma_cloud_storage.entity.User;
import com.example.diploma_cloud_storage.model.Login;
import com.example.diploma_cloud_storage.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthorizationServer implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationServer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true) //метод только для чтения и не изменяет данные в базе данных.
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin("Ivan-1111");
        return
                user.map(user1 -> new User(user1.getId(), user1.getLogin(),
                        user1.getPassword(), user1.getAuthToken()))
                .orElseThrow(() -> new BadCredentialsException("Пользователь с " + login + " не найден!"));
    }

    public Login login(User user, String login) {
        UserDetails userDetails = loadUserByUsername(login);

        if (!userDetails.getPassword().equals(user.getPassword()))
            log.info("Пользователь с логином" + user.getLogin() + " успешно прошел аутентификацию!");

        var authToken = generateToken(user);

        log.info("Пользователь с логином" + user.getLogin() + " успешно прошел авторизацию!");

        return new Login(authToken);
    }

    private String generateToken(User user) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusHours(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return token;
    }

    public void logout(String authToken) {

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

}

