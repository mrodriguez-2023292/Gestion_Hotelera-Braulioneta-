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

import com.braulioneta.gestionHotelera.DTO.RoomSaveDTO;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.service.RoomService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador para manejar las operaciones relacionadas con las habitaciones en el sistema de gestión hotelera.
// Este controlador expone endpoints para crear, obtener, actualizar y eliminar habitaciones.
@RestController
@RequestMapping("/gestionHotelera/room")
public class RoomController {

    @Autowired
    RoomService roomService;

    // Obtiene la lista de todas las habitaciones.
    // @return ResponseEntity con la lista de habitaciones si la operación es exitosa.
    // Si ocurre un error, devuelve una respuesta con un mensaje de error adecuado.
    @GetMapping()
    public ResponseEntity<?> getRooms() {
        Map<String, Object> res = new HashMap<>();
        
        try {
            // Devuelve todas las habitaciones en formato JSON.
            return ResponseEntity.ok().body(roomService.listRooms());
        } catch (CannotCreateTransactionException err) {
            // Manejo de errores al conectarse a la base de datos.
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            // Manejo de errores al consultar la base de datos.
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            // Manejo de errores generales.
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Obtiene una habitación específica por su ID.
    // @param id el ID de la habitación a obtener.
    // @return ResponseEntity con la habitación si la operación es exitosa.
    // Si la habitación no existe, se devuelve un mensaje de error.
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        
        try {
            // Devuelve la habitación específica.
            return ResponseEntity.ok().body(roomService.getRoom(id));
        } catch (NoResultException err) {
            // Manejo de errores si la habitación no existe.
            res.put("message", "La habitación no existe.");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException err) {
            // Manejo de errores al conectarse a la base de datos.
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            // Manejo de errores al consultar la base de datos.
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            // Manejo de errores generales.
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Agrega una nueva habitación.
    // @param roomDto DTO que contiene los datos de la habitación a agregar.
    // @param result objeto BindingResult que contiene los resultados de la validación.
    // @return ResponseEntity con un mensaje de éxito y la habitación agregada, o mensajes de error en caso de validación.
    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(
        @Valid @ModelAttribute RoomSaveDTO roomDto,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        
        if (result.hasErrors()) {
            // Si hay errores de validación, devolverá los mensajes de error.
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res);
        }
        
        try {
            // Agrega la habitación y devuelve un mensaje de éxito.
            Room room = roomService.addRoom(roomDto);
            res.put("message", "La habitación se reservó correctamente");
            res.put("room", room);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            // Manejo de errores al intentar guardar la habitación.
            res.put("message", "Error al guardar la habitación, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Actualiza una habitación existente.
    // @param id el ID de la habitación a actualizar.
    // @param roomDto DTO que contiene los nuevos datos de la habitación.
    // @param result objeto BindingResult que contiene los resultados de la validación.
    // @return ResponseEntity con un mensaje de éxito y la habitación actualizada, o mensajes de error en caso de validación.
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editRoom(
            @PathVariable Long id, 
            @Valid @RequestBody RoomSaveDTO roomDto, 
            BindingResult result) {
        
        Map<String, Object> res = new HashMap<>();
        
        if (result.hasErrors()) {
            // Si hay errores de validación, devolverá los mensajes de error.
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res);
        }
        
        try {
            // Actualizar la habitación usando el servicio.
            Room updatedRoom = roomService.updateRoom(id, roomDto);
            res.put("message", "La habitación se actualizó correctamente");
            res.put("room", updatedRoom);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            // Manejo de errores si el ID no es válido.
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res);
        } catch (Exception e) {
            // Manejo de errores generales.
            res.put("message", "Error general al actualizar la habitación");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Elimina una habitación existente.
    // @param id el ID de la habitación a eliminar.
    // @return ResponseEntity con un mensaje de éxito si la eliminación es exitosa, o un mensaje de error si la habitación no existe.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminateRoom(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        
        // Busca la habitación por su ID
        Room room = roomService.getRoom(id);

        try {
            // Elimina la habitación y devuelve un mensaje de éxito.
            roomService.deleteRoom(room);
            answer.put("Eliminado exitosamente", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            // Manejo de errores si la habitación no existe.
            res.put("message", "La habitación con el ID brindado no existe");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException err) {
            // Manejo de errores al conectarse a la base de datos.
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            // Manejo de errores al consultar la base de datos.
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            // Manejo de errores generales.
            res.put("message", "Error general al eliminar la habitación");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
}