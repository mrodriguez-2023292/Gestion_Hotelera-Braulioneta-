package com.braulioneta.gestionHotelera.model;

import java.sql.Date;

import com.braulioneta.gestionHotelera.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @FutureOrPresent
    private Date startDate;

    @NotBlank
    @FutureOrPresent
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    /*Pendientes llaves foraneas
    Room_id
    User_id
     */
}
