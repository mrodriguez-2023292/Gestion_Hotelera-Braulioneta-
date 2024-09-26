package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class HotelSaveDTO {
    
    // Nombre del hotel. Este campo no puede estar vacío.
    @NotBlank(message = "El nombre del hotel no puede ir vacío")
    private String name;
    
    // Dirección del hotel. Este campo no puede estar vacío.
    @NotBlank(message = "La dirección no puede ir vacía")
    private String address;
    
    // Categoría del hotel (por ejemplo, 3 estrellas, lujo, etc.). Este campo no puede estar vacío.
    @NotBlank(message = "La categoría no puede ir vacía")
    private String category;
    
    // Teléfono de contacto del hotel. Este campo no puede estar vacío.
    @NotBlank(message = "El teléfono no puede ir vacío")
    private String phone;
    
    // Correo electrónico de contacto del hotel. Este campo no puede estar vacío y debe ser un correo válido.
    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo no puede ir vacío")
    private String emailContact;
}