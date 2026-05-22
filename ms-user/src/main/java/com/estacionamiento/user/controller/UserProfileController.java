package com.estacionamiento.user.controller;


import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;
import com.estacionamiento.user.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<UserProfileResponseDto>> findAll() {
        logger.info("Buscando todos los perfiles de usuario");
        return  ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> findById(@PathVariable Long id){
        logger.info("Buscando por id de usuario");
        try {
            UserProfileResponseDto user = userProfileService.findById(id);
            if (user == null)return ResponseEntity.notFound().build();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> create(@Valid @RequestBody UserProfileRequestDto dto){
        logger.info("Creando usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.create(dto));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> update(@Valid @RequestBody UserProfileRequestDto dto){
        logger.info("Actualizando usuario");
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> deleteById(@PathVariable Long id){
        logger.info("Eliminando usuario");
        return userProfileService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
