package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Crea un constructor sin parámetros
@AllArgsConstructor // Crea un constructor con todos los atributos
public class InvoiceRoomSaveDTO {

    // Fecha de la factura. Este campo no puede estar vacío y debe ser la fecha actual o futura.
    @NotNull(message = "La fecha no puede ir vacía")
    @FutureOrPresent // Valida que la fecha sea presente o futura
    private LocalDateTime date;

    // Total de la factura. Este campo no puede estar vacío.
    @NotNull(message = "El total no puede ir vacío")
    private Long total;

    // Método de pago utilizado para la factura. Este campo no puede estar vacío.
    @NotBlank(message = "El método de pago no puede ir vacío")
    private String paymentMethod;

    // Identificador de la habitación asociada a la factura. Este campo no puede estar vacío.
    @NotNull(message = "No puede ir vacía la habitación")
    private Long roomId;
}