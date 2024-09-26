package com.braulioneta.gestionHotelera.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Crea un constructor sin parámetros
@AllArgsConstructor // Crea un constructor con todos los atributos
public class InvoiceEventSaveDTO {

    // Cantidad asociada a la factura del evento. Este campo no puede estar vacío.
    @NotNull(message = "La cantidad no puede ir vacía")
    private Long amount;

    // Descripción de la factura. Este campo no puede estar vacío.
    @NotBlank(message = "La descripción no puede ir vacía")
    private String description;

    // Identificador del evento asociado a la factura. Este campo no puede estar vacío.
    @NotNull(message = "No puede ir vacío el evento")
    private Long eventId;
}