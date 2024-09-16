package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdditionalServiceDTO {
    @NotBlank(message = "El nombre del servicio no puede ir vacio")
    private String service_name;

    @NotBlank(message = "La descripcion del servicio no puede ir vacio")
    private String description;

    @NotNull(message = "El precio del servicio no puede ir vacio")
    private float price;

    @NotNull(message = "No hay evento para reservar")
    private Long event;

    @NotNull(message = "No hay habitacion para reservar")
    private Long room;
}
