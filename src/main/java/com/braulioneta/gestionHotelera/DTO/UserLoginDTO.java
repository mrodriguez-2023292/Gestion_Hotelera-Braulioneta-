package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @NotBlank(message = "El nombre de usuario no puede ir vacio")
    private String username;
    @NotBlank(message = "La contraseña no puede ir vacia")
    private String password;


}


