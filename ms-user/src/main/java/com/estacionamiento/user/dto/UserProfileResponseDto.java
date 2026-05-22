package com.estacionamiento.user.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileResponseDto {
    @NotNull
    Long id;
    String username;
    String email;
    String fullname;
    String phone;
    String role;

}
