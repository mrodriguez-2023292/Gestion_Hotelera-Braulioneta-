package com.braulioneta.gestionHotelera.model;

import java.sql.Date;
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


/**
 * La clase Report representa un informe en el sistema de gestión hotelera. 
 * Contiene información sobre las reservas, ocupación y datos del hotel 
 * en un momento específico.
 *
 * La clase está mapeada a una tabla en la base de datos a través de la anotación 
 * @Entity, y utiliza Lombok para generar automáticamente los métodos 
 * getter, setter, y otros como equals y hashCode.
 * 
 * @Data: Genera automáticamente getters y setters.
 * @Entity: Marca la clase como una entidad de JPA para el mapeo de base de datos.
 * @AllArgsConstructor: Genera un constructor con todos los campos.
 * @NoArgsConstructor: Genera un constructor vacío.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    /**
     * Identificador único del informe.
     * Es una clave primaria generada automáticamente con la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del hotel al que pertenece el informe.
     * No puede estar en blanco.
     */
    @NotBlank
    private String hotelName;

    /**
     * Fecha en la que se generó el informe.
     * Debe ser una fecha presente o futura.
     */
    @NotNull
    @FutureOrPresent
    private Timestamp reportDate;

    /**
     * Total de reservas realizadas en el hotel.
     * No puede ser nulo.
     */
    @NotNull
    private Long totalReservations;

    /**
     * Total de habitaciones en el hotel.
     * No puede ser nulo.
     */
    @NotNull
    private Long totalRooms;

    /**
     * Total de habitaciones ocupadas en el hotel.
     * No puede ser nulo.
     */
    @NotNull
    private Long occupiedRooms;

    /**
     * Tasa de ocupación del hotel, expresada como un valor flotante.
     * No puede ser nulo.
     */
    @NotNull
    private Float occupancyRate;

    /**
     * Nombre del hotel más solicitado en el informe.
     * No puede estar en blanco.
     */
    @NotBlank
    private String mostRequestedHotel;

    /**
     * Relación con la entidad Hotel, representando el hotel al que pertenece el informe.
     * Se utiliza la anotación @ManyToOne para indicar la relación de muchos a uno.
     * Eager Fetch es el comportamiento predeterminado para cargar automáticamente 
     * la información del hotel al cargar el informe.
     */
    @NotBlank
    @ManyToOne
    private Hotel hotelId;
}