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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.ReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.ReservationSaveDTO;
import com.braulioneta.gestionHotelera.model.Reservation;
import com.braulioneta.gestionHotelera.service.ReservationService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestionHotelera/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping()
    public ResponseEntity<?> getReservations(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(reservationService.listReservations());
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/findByUser/{userId}")
    public ResponseEntity<?> myReservations(@PathVariable Long userId) {
        Map<String, Object> res = new HashMap<>();
        try{
            List<ReservationResponseDTO> reservations = reservationService.myReservations(userId);
            if(reservations == null || reservations.isEmpty()){
                res.put("message", "Aún no tienes reservaciones creadas");
                return ResponseEntity.status(404).body(res);
            }else{
                return ResponseEntity.ok(reservations);
            }
        }catch(Exception err){
            res.put("message", "Error general al obtener los datos");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> saveReservation(
        @Valid @ModelAttribute ReservationSaveDTO reservationDTO,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }
        try {
            Reservation reservation = reservationService.save(reservationDTO);
            res.put("message", "Reservacion guaradada exitosamente");
            res.put("reservation", reservation);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la reservacion, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/editReservation/{id}")
    public  ResponseEntity<?> editReservation(
        @PathVariable Long id,
        @Valid @ModelAttribute ReservationSaveDTO reservationDTO,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res);
        }
        try {
            //Actualizar usando el metodo del servicio
            Reservation updReservation = reservationService.update(id, reservationDTO);
            res.put("message", "La reservación se actualizo correctamente");
            res.put("reservation", updReservation);
            return ResponseEntity.ok(res);
        }catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el evento");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @DeleteMapping("/cancelReservation/{id}")
    public ResponseEntity<?> cacelReservation(@PathVariable Long id){
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Reservation reservation = reservationService.getReservation(id);

        try {
            reservationService.cancelReservation(reservation);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            res.put("message", "La reservacion con el ID brindado no existe");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } 
    }


}
