package com.braulioneta.gestionHotelera.DTO;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventDTO {
    @NotBlank(message = "El nombre del evento no puede ir vacio")
    private String name;
    @NotBlank(message = "La descripcion del evento no puede ir vacia")
    private String description;
    @NotBlank(message = "La fecha de inicio no puede ir vacia")
    private Date start;
    @NotBlank(message = "La fecha de finalizacion no puede ir vacia")
    private Date end;


}
