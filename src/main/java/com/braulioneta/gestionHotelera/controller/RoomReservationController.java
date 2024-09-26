package com.braulioneta.gestionHotelera.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.RoomReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.RoomReservationSaveDTO;
import com.braulioneta.gestionHotelera.model.RoomReservation;
import com.braulioneta.gestionHotelera.service.RoomReservationService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador REST para manejar las reservas de habitaciones
@RestController
@RequestMapping("/gestionHotelera/roomReservation") // Ruta base para las reservas de habitaciones
public class RoomReservationController {
    
    @Autowired
    RoomReservationService reservationService; // Servicio de reservas inyectado

    // Método para obtener todas las reservas
    @GetMapping()
    public ResponseEntity<?> getReservations() {
        Map<String, Object> res = new HashMap<>();
        try {
            // Retorna la lista de reservas
            return ResponseEntity.ok().body(reservationService.listReservations());
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Retorna un error 503 si no se puede conectar
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Retorna un error 503 para problemas de acceso a datos
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Retorna un error interno del servidor
        }
    }

    // Método para obtener las reservas de un usuario específico
    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<?> myReservations(@PathVariable Long userId) {
        Map<String, Object> res = new HashMap<>();
        try {
            // Busca las reservas del usuario
            List<RoomReservationResponseDTO> reservations = reservationService.myReservations(userId);
            if (reservations == null || reservations.isEmpty()) {
                res.put("message", "Aún no tienes reservaciones creadas");
                return ResponseEntity.status(404).body(res); // Retorna 404 si no hay reservas
            } else {
                return ResponseEntity.ok(reservations); // Retorna las reservas encontradas
            }
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res); // Retorna un error interno del servidor
        }
    }

    // Método para agregar una nueva reserva
    @PostMapping("/addReservation")
    public ResponseEntity<?> addReservation(
        @Valid @ModelAttribute RoomReservationSaveDTO reservationDTO,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        // Verifica si hay errores de validación
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res); // Retorna un error 400 si hay errores de validación
        }
        try {
            // Agrega la reserva utilizando el servicio
            RoomReservation reservation = reservationService.addRoomReservation(reservationDTO);
            res.put("message", "Reservacion guardada exitosamente");
            res.put("reservation", reservation);
            return ResponseEntity.ok(res); // Retorna la reserva creada
        } catch (Exception err) {
            res.put("message", "Error al guardar la reservacion, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Retorna un error interno del servidor
        }
    }

    // Método para editar una reserva existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editReservation(
        @PathVariable Long id,
        @Valid @ModelAttribute RoomReservationSaveDTO reservationDTO,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        // Verifica si hay errores de validación
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res); // Retorna un error 400 si hay errores de validación
        }
        try {
            // Actualiza la reserva utilizando el servicio
            RoomReservation updReservation = reservationService.updateRoomReservation(id, reservationDTO);
            res.put("message", "La reservación se actualizó correctamente");
            res.put("reservation", updReservation);
            return ResponseEntity.ok(res); // Retorna la reserva actualizada
        } catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res); // Retorna 404 si no se encuentra la reserva
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el evento");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Retorna un error interno del servidor
        }
    }

    // Método para eliminar una reserva
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        // Obtiene la reserva a eliminar
        RoomReservation reservation = reservationService.getReservation(id);

        try {
            // Elimina la reserva utilizando el servicio
            reservationService.deleteReservation(reservation);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer); // Retorna confirmación de eliminación
        } catch (NoResultException err) {
            res.put("message", "La reservacion con el ID brindado no existe");
            return ResponseEntity.status(503).body(res); // Retorna 503 si la reserva no existe
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Retorna un error 503 si no se puede conectar
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Retorna un error 503 para problemas de acceso a datos
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Retorna un error interno del servidor
        }
    }
}