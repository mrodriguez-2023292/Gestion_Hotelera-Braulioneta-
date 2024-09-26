package com.braulioneta.gestionHotelera.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class InvoiceEvent {

    // Identificador único para cada factura de evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Monto de la factura, debe ser un valor no nulo
    @NotNull
    private Long amount;

    // Descripción de la factura, no puede estar en blanco
    @NotBlank
    private String description;

    // Evento asociado a la factura, debe ser un evento válido y no nulo
    @NotNull
    @ManyToOne
    private Event eventId;
}