package com.estacionamiento.user.controller;


import com.estacionamiento.user.dto.UserProfileRequestDto;
import com.estacionamiento.user.dto.UserProfileResponseDto;
import com.estacionamiento.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Tag(name = "Usuarios", description = "Gestion de perfiles de usuario")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UserProfileResponseDto>> findAll() {
        logger.info("Buscando todos los perfiles de usuario");
        return  ResponseEntity.ok(userProfileService.findAll());
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
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

    @Operation(summary = "Crear nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
    @PostMapping
    public ResponseEntity<UserProfileResponseDto> create(@Valid @RequestBody UserProfileRequestDto dto){
        logger.info("Creando usuario");
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.create(dto));
    }

    @Operation(summary = "Actualizar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado")
    @PutMapping
    public ResponseEntity<UserProfileResponseDto> update(@Valid @RequestBody UserProfileRequestDto dto){
        logger.info("Actualizando usuario");
        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @Operation(summary = "Eliminar usuario")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> deleteById(@PathVariable Long id){
        logger.info("Eliminando usuario");
        return userProfileService.deleteById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
