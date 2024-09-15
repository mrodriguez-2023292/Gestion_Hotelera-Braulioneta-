package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @NotBlank(message="El nombre de ususario es obligatorio")
    private String username;

    @NotBlank(message="La contrasena no puede ir vacia")
    private String password;
}
