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

import com.braulioneta.gestionHotelera.DTO.EventSaveDTO;
import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.service.EventService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador REST para manejar las solicitudes relacionadas con los eventos
@RestController
@RequestMapping("/gestionHotelera/event") // Mapea las solicitudes a esta URL base
public class EventController {

    @Autowired
    EventService eventService; // Servicio para manejar la lógica de negocio de eventos

    // Obtiene la lista de todos los eventos
    @GetMapping()
    public ResponseEntity<?> getEvents() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(eventService.listEvents());
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        }
    }

    // Obtiene un evento específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(eventService.getEvent(id));
        } catch (NoResultException err) {
            res.put("message", "El evento no existe.");
            return ResponseEntity.status(503).body(res); // Evento no encontrado
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        }
    }
        
    // Agrega un nuevo evento
    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(
        @Valid @ModelAttribute EventSaveDTO eventDTO,
        BindingResult result
    ) {
        Map<String, Object> res = new HashMap<>();
        
        // Verifica si hay errores de validación en el DTO
        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res); // Respuesta con errores de validación
        }
        
        try {
            Event event = eventService.addEvent(eventDTO);
            res.put("message", "El evento se reservó correctamente");
            res.put("event", event);
            return ResponseEntity.ok(res); // Respuesta exitosa con el evento agregado
        } catch (Exception err) {
            res.put("message", "Error al guardar el evento, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno al guardar el evento
        }
    }

    // Actualiza un evento existente por su ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editEvent(
            @PathVariable Long id, 
            @Valid @RequestBody EventSaveDTO eventDTO, 
            BindingResult result) {
        
        Map<String, Object> res = new HashMap<>();
        
        // Verifica si hay errores de validación en el DTO
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res); // Respuesta con errores de validación
        }
        
        try {
            // Actualiza el evento usando el servicio
            Event updatedEvent = eventService.updateEvent(id, eventDTO);
            res.put("message", "El evento se actualizó correctamente");
            res.put("event", updatedEvent);
            return ResponseEntity.ok(res); // Respuesta exitosa con el evento actualizado
        } catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res); // Evento no encontrado para actualización
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el evento");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno al actualizar el evento
        }
    }

    // Elimina un evento por su ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> cancelEvent(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        
        Event event = eventService.getEvent(id);

        try{
            eventService.deleteEvent(event);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer); // Respuesta exitosa indicando que se ha cancelado el evento

        }catch (NoResultException err) {
            res.put("message", "El evento con el ID brindado no existe");
            return ResponseEntity.status(503).body(res); // Evento no encontrado para eliminar
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        } 
    }
}