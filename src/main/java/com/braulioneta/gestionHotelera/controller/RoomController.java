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

    // Obtiene la lista de todas las habitaciones.
    // @return ResponseEntity con la lista de habitaciones si la operación es exitosa. Si ocurre un error, devuelve una respuesta con un mensaje de error adecuado.
    @GetMapping
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

    // Guarda una nueva habitación en la base de datos.
    // @param room DTO que contiene los datos de la habitación a guardar.
    // @param result Resultados de la validación del DTO.
    // @return ResponseEntity con un mensaje de éxito si la operación es exitosa, o un mensaje de error si ocurre un problema durante la validación o el guardado.
    @PostMapping("/save")
    public ResponseEntity<?> saveRoom(
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
                room.getStatus()
            );
            roomService.saveRoom(newroom);
            res.put("message", "Habitación guardada correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la habitación, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Obtiene una habitación por su ID.
    // @param id ID de la habitación que se desea obtener.
    // @return ResponseEntity con la habitación si se encuentra o un mensaje de error si no existe.
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

    // Edita una habitación existente.
    // @param id ID de la habitación que se desea editar.
    // @param received Objeto Room con los datos actualizados.
    // @return ResponseEntity con la habitación actualizada si la operación es exitosa, o un mensaje de error si ocurre un problema.
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRoom(@PathVariable Long id, @RequestBody Room received) {
        Map<String, Object> res = new HashMap<>();
        Room room = roomService.getRoom(id);

        room.setType(received.getType());
        room.setCapacity(received.getCapacity());
        room.setPrice(received.getPrice());
        room.setStatus(received.getStatus());

        try { 
            return ResponseEntity.ok().body(roomService.saveRoom(room));
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

    // Elimina una habitación por su ID.
    // @param id ID de la habitación que se desea eliminar.
    // @return ResponseEntity con un mensaje de confirmación si la operación es exitosa, o un mensaje de error si ocurre un problema.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Room room = roomService.getRoom(id);

        try { 
            roomService.eliminateRoom(room);
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
