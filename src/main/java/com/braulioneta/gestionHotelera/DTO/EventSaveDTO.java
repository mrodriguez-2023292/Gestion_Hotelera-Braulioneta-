package com.braulioneta.gestionHotelera.DTO;



import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventSaveDTO {
    @NotBlank(message = "El nombre del evento no puede ir vacio")
    private String name;
    @NotBlank(message = "La descripcion del evento no puede ir vacia")
    private String description;
    @NotNull(message = "La fecha de inicio no puede ir vacia")
    @FutureOrPresent(message = "La fecha no puede ser del pasado , tiene que ser presente o futura")
    private LocalDateTime start;
    @NotNull(message = "La fecha de finalizacion no puede ir vacia")
    @FutureOrPresent(message = "La fecha no puede ser del pasado , tiene que ser presente")
    private LocalDateTime end;
    @NotNull(message = "No hay hotel para reservar")
    private Long hotelId;

}
