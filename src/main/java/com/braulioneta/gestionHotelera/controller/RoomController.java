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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.RoomDTO;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.service.RoomService;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @Valid @ModelAttribute RoomDTO room,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }
        try {
            Long id = null;
            Room newroom = new Room(
                id,
                room.getType(),
                room.getCapacity(),
                room.getPrice(),
                room.getStatus(),
                room.getHotel()
            );
            roomService.register(newroom);
            res.put("message", "Habitacion guardada correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la habitacion, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
