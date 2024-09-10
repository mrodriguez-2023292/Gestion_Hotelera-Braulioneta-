package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.utils.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomDTO {
    @NotBlank(message = "El tipo de la habitacion no puede ir vacío")
    private String type;
    
    @NotBlank(message = "La capacidad no puede ir vacía")
    private Integer capacity;
    
    @NotBlank(message = "El precio no puede ir vacío")
    private Long price;
    
    @NotBlank(message = "Seleccione una opción, si no se quedará como disponible.")
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @NotBlank(message = "La relacion no puede ir vacía")
    @ManyToOne
    private Hotel hotel;
}
