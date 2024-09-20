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

import com.braulioneta.gestionHotelera.DTO.RoomDTO;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.service.RoomService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestionHotelera/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping()
    public ResponseEntity<?> getMethodId() {
        Map<String, Object> res = new HashMap<>();
        try { 
            return ResponseEntity.ok().body(roomService.listRooms());
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

    @PostMapping("/addRoom")
    public ResponseEntity<?> register(
        @Valid @ModelAttribute RoomDTO roomDTO,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        
        // Verificar si hay errores de validación
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("errors", errors);
            return ResponseEntity.badRequest().body(res);
        }
        
        try {
            // Llamar al método del servicio para registrar la habitación
            Room room = roomService.reserved(roomDTO);
            
            res.put("message", "Habitación guardada correctamente");
            res.put("roomId", room.getId());  // O cualquier otro dato que quieras devolver
            return ResponseEntity.ok(res);
            
        } catch (Exception err) {
            res.put("message", "Error al guardar la habitación, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomId(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(roomService.getRoom(id));
            } catch (NoResultException err) {
                res.put("message", "La habitación con el ID proporcionado no existe");
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
    

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        Map<String, Object> res = new HashMap<>();

        try {
            // Llamar al método del servicio para actualizar la habitación
            Room updatedRoom = roomService.update(id, roomDTO);
            
            res.put("message", "Habitación actualizada correctamente");
            res.put("room", updatedRoom);
            return ResponseEntity.ok(res);
            
        } catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar la habitacion");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id){
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Room room = roomService.getRoom(id);

        try { 
            roomService.eliminate(room);
            answer.put("Eliminado", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            res.put("message", "La habitación con el ID proporcionado no existe");
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
