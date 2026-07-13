package com.estacionamiento.auth.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    private static final String JWT_SECRET =
            "aJcgl4t5POsiRKyfM0vUa0jvlOB9BNPhulkCRItBocm+tluXDKLchR6lwTWYr5bCy9UmlKW3rRo8HHIp";

    private AuthService authService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        ReflectionTestUtils.setField(authService, "JWT_SECRET", JWT_SECRET);
        ReflectionTestUtils.setField(authService, "JWT_EXPIRATION", 864000000L);

        userDetails = new User("juan.perez", "password", Collections.emptyList());
    }

    @Test
    void generateToken_returnsNonEmptyJwt() {
        String token = authService.generateToken(userDetails);

        assertThat(token).isNotBlank();
        assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    void extractUsername_fromGeneratedToken_returnsSameUsername() {
        String token = authService.generateToken(userDetails);

        String username = authService.extractUsername(token);

        assertThat(username).isEqualTo("juan.perez");
    }

    @Test
    void isValidToken_matchingUserAndNotExpired_returnsTrue() {
        String token = authService.generateToken(userDetails);

        boolean valid = authService.isValidToken(token, userDetails);

        assertThat(valid).isTrue();
    }

    @Test
    void isValidToken_usernameMismatch_returnsFalse() {
        String token = authService.generateToken(userDetails);
        UserDetails otherUser = new User("otro.usuario", "password", Collections.emptyList());

        boolean valid = authService.isValidToken(token, otherUser);

        assertThat(valid).isFalse();
    }

    @Test
    void isExpiredToken_freshlyGeneratedToken_returnsFalse() {
        String token = authService.generateToken(userDetails);

        assertThat(authService.isExpiredToken(token)).isFalse();
    }

    @Test
    void isExpiredToken_tokenPastExpiration_throwsExpiredJwtException() {
        ReflectionTestUtils.setField(authService, "JWT_EXPIRATION", -1000L);
        String expiredToken = authService.generateToken(userDetails);

        assertThatThrownBy(() -> authService.isExpiredToken(expiredToken))
                .isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    void isValidToken_expiredToken_throwsExpiredJwtException() {
        ReflectionTestUtils.setField(authService, "JWT_EXPIRATION", -1000L);
        String expiredToken = authService.generateToken(userDetails);

        assertThatThrownBy(() -> authService.isValidToken(expiredToken, userDetails))
                .isInstanceOf(ExpiredJwtException.class);
    }
}
