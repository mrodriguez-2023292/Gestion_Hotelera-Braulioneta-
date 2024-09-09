package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelDTO {
    @NotBlank(message = "El nombre del hotel no puede ir vacío")
    private String name;
    
    @NotBlank(message = "La dirección no puede ir vacía")
    private String address;
    
    @NotBlank(message = "La categoría no puede ir vacía")
    private String category;
    
    @NotBlank(message = "El teléfono no puede ir vacío")
    private String phone;
    
    @Email(message = "Debe proporcionar un correo válido")
    @NotBlank(message = "El correo no puede ir vacío")
    private String email_contact;
}
