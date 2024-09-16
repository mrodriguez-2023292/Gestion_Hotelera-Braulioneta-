package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.utils.StatusRoom;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class RoomDTO {
    
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
    private StatusRoom status = StatusRoom.AVAILABLE; // Valor por defecto: DISPONIBLE

    @NotBlank(message = "No puede ir sin relacion")
    @ManyToOne //Por defecto tiene un Eager (población de datos)
    private Hotel hotel;
}
