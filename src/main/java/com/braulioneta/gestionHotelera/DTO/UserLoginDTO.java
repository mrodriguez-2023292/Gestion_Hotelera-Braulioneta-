package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class UserLoginDTO {

    // Nombre de usuario para autenticación
    @NotBlank(message = "El nombre de usuario no puede ir vacio")
    private String username;

    // Contraseña del usuario para autenticación
    @NotBlank(message = "La contraseña no puede ir vacia")
    private String password;
}