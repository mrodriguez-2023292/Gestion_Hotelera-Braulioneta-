package com.braulioneta.gestionHotelera.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Crea un constructor sin parámetros
@AllArgsConstructor // Crea un constructor con todos los atributos
public class UserClearDTO {

    // Nombre del usuario
    private String name;

    // Apellido del usuario
    private String surname;

    // Nombre de usuario para autenticación
    private String username; 
}