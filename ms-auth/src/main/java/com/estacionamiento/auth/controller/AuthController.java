package com.estacionamiento.auth.controller;

import com.estacionamiento.auth.dto.LoginRequestDTO;
import com.estacionamiento.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Autenticación", description = "Endpoints de autenticación de usuarios")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final AuthService authService;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authManager,
                          AuthService authService,
                          UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.authService = authService;
        this.userDetailsService = userDetailsService;
    }

    @Operation(summary = "Autentica usuario y genera token JWT")
    @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token generado")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO requestDTO) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword()));

            UserDetails usuario = userDetailsService.loadUserByUsername(requestDTO.getUsername());
            String token = authService.generateToken(usuario);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales no validas"));
        }
    }
}
