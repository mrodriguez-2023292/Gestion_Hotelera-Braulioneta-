package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.utils.Roles;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @NotBlank(message="El nombre del usuario no puede ir vacio")
    private String name;

    @NotBlank(message="El apellido del usuario no puede ir vacio")
    private String surname;

    @NotBlank(message="El nombre del usuario no puede ir vacio")
    private String username;

    @Email
    @NotBlank(message="El correo no puede ir vacio")
    private String email;

    @NotBlank(message="La contrasena no pude ir vacia")
    private String password;
    
    @NotBlank(message="El numero telefonico no puede ir vacio")
    private String phone; 

    @Enumerated(EnumType.STRING)
    private Roles userType;
}
