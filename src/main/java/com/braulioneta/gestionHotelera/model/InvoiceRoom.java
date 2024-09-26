package com.braulioneta.gestionHotelera.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class InvoiceRoom {

    // Identificador único para cada factura de habitación
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de la factura, debe ser en el presente o futuro
    @NotNull
    @FutureOrPresent
    private Timestamp date;

    // Total de la factura, debe ser un valor no nulo
    @NotNull
    private Long total;

    // Método de pago utilizado, no puede estar en blanco
    @NotBlank
    private String paymentMethod;

    // Habitación asociada a la factura, debe ser una habitación válida y no nula
    @NotNull
    @ManyToOne
    private Room roomId;
}
