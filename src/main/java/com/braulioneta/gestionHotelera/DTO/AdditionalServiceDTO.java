package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class AdditionalServiceDTO {
    
    // Nombre del servicio adicional. Este campo no puede estar vacío.
    @NotBlank(message = "El nombre del servicio no puede ir vacío")
    private String name;

    // Descripción del servicio adicional. Este campo no puede estar vacío.
    @NotBlank(message = "La descripción del servicio no puede ir vacío")
    private String description;

    // Precio del servicio adicional. Este campo no puede ser nulo.
    @NotNull(message = "El precio del servicio no puede ir vacío")
    private float price;

    // Identificador del evento asociado al servicio adicional. Este campo no puede ser nulo.
    @NotNull(message = "No hay evento para reservar")
    private Long eventId;

    // Identificador de la habitación asociada al servicio adicional. Este campo no puede ser nulo.
    @NotNull(message = "No hay habitación para reservar")
    private Long roomId;
}