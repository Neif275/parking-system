package com.estacionamiento.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileRequestDto {

    Long id;
    @NotBlank(message = "El usuario no puede estar en blanco")
    String username;

    @Email(message = "El corre debe tener un formato valido")
    @NotBlank(message = "El correo no puede estar en blanco")
    String email;

    @NotBlank(message = "El nombre completo no puede estar en blanco")
    String fullname;

    @NotBlank(message = "El telefono no puede estar en blanco")
    String phone;

    @NotBlank(message = "El rol no puede estar en blanco")
    String role;
}
