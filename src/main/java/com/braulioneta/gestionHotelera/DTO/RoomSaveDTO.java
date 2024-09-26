package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.utils.RoomStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class RoomSaveDTO {
    
    // Tipo de la habitación. No puede estar vacío
    @NotBlank(message = "El tipo de la habitación no puede ir vacío")
    private String type;
    
    // Capacidad de la habitación. No puede ser nulo
    @NotNull(message = "La capacidad no puede ir vacía")
    private Integer capacity;
    
    // Precio de la habitación. No puede ser nulo
    @NotNull(message = "El precio no puede ir vacío")
    private Float price;
    
    // Estado de la habitación. No puede ser nulo. Se usa una enumeración StatusRoom
    @NotNull(message = "Seleccione una opción, si no se quedará como disponible.")
    @Enumerated(EnumType.STRING) // Almacena el estado como una cadena en la base de datos
    private RoomStatus status = RoomStatus.AVAILABLE; // Valor por defecto: DISPONIBLE

    @NotNull(message = "No puede ir sin relacion")
    private Long hotelId;
}
