package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class HotelDTO {

    // Nombre del hotel. No puede estar vacío
    @NotBlank(message = "El nombre del hotel no puede ir vacío")
    private String name;
    
    // Dirección del hotel. No puede estar vacía
    @NotBlank(message = "La dirección no puede ir vacía")
    private String address;
    
    // Categoría del hotel. No puede estar vacía
    @NotBlank(message = "La categoría no puede ir vacía")
    private String category;
    
    // Teléfono del hotel. No puede estar vacío
    @NotBlank(message = "El teléfono no puede ir vacío")
    private String phone;
    
    // Correo electrónico del hotel. Debe ser un correo válido y no puede estar vacío
    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo no puede ir vacío")
    private String emailContact;
}
