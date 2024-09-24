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

/**
 * La clase Event representa un evento en el sistema de gestión hotelera.
 * Contiene información sobre el nombre, la descripción y las fechas 
 * del evento, así como la relación con el hotel correspondiente.
 *
 * La clase está mapeada a una tabla en la base de datos a través de la anotación 
 * @Entity, y utiliza Lombok para generar automáticamente los métodos 
 * getter, setter y otros como equals y hashCode.
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
public class Event {

    /**
     * Identificador único del evento.
     * Es una clave primaria generada automáticamente con la estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del evento.
     * No puede estar en blanco.
     */
    @NotBlank
    private String name;

    /**
     * Descripción del evento.
     * No puede estar en blanco.
     */
    @NotBlank
    private String description;

    /**
     * Fecha y hora de inicio del evento.
     * Debe ser una fecha presente o futura.
     */
    @NotNull
    @FutureOrPresent
    private Timestamp startDate;

    /**
     * Fecha y hora de finalización del evento.
     * Debe ser una fecha presente o futura.
     */
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;

    /**
     * Relación con la entidad Hotel, representando el hotel donde se realiza el evento.
     * Se utiliza la anotación @ManyToOne para indicar la relación de muchos a uno.
     */
    @NotNull
    @ManyToOne
    private Hotel hotelId;
}