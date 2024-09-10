package com.braulioneta.gestionHotelera.DTO;


import com.braulioneta.gestionHotelera.utils.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomDTO {
    @NotBlank(message = "El tipo de la habitacion no puede ir vacío")
    private String type;
    
    @NotNull(message = "La capacidad no puede ir vacía")
    private Integer capacity;
    
    @NotNull(message = "El precio no puede ir vacío")
    private Float price;
    
    @NotNull(message = "Seleccione una opción, si no se quedará como disponible.")
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;
}
