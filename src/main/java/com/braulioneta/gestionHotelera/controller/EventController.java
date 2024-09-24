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

/**
 * La clase EventController maneja las solicitudes HTTP relacionadas con los eventos.
 * Proporciona endpoints para listar, agregar, obtener, editar y cancelar eventos.
 * 
 * @RestController: Indica que esta clase es un controlador REST que maneja solicitudes 
 * HTTP y devuelve respuestas.
 * 
 * @RequestMapping("/gestionHotelera/event"): Especifica la URL base para las 
 * solicitudes manejadas por este controlador.
 */
@RestController
@RequestMapping("/gestionHotelera/event")
public class EventController {

    @Autowired
    EventService eventService; // Servicio que maneja la lógica de negocio para los eventos.

    /**
     * Maneja la solicitud GET para obtener todos los eventos.
     * 
     * @return ResponseEntity con la lista de eventos o un mensaje de error si ocurre un problema.
     */
    @GetMapping()
    public ResponseEntity<?> getEvents() {
        Map<String, Object> res = new HashMap<>();
        
        try {
            // Devuelve todos los eventos en formato JSON.
            return ResponseEntity.ok().body(eventService.listEvents());
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

    /**
     * Maneja la solicitud POST para agregar un nuevo evento.
     * 
     * @param eventDTO DTO que contiene los datos del evento a agregar.
     * @param result Resultados de la validación de los datos de entrada.
     * @return ResponseEntity con un mensaje de éxito o de error si la validación falla o si ocurre un problema.
     */
    @PostMapping("/addEvent")
    public ResponseEntity<?> addEvent(
        @Valid @ModelAttribute EventSaveDTO eventDTO,
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
            // Agrega el evento y devuelve un mensaje de éxito.
            Event event = eventService.addEvent(eventDTO);
            res.put("message", "El evento se reservó correctamente");
            res.put("event", event);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            // Manejo de errores al intentar guardar el evento.
            res.put("message", "Error al guardar el evento, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /**
     * Maneja la solicitud GET para obtener un evento específico por su ID.
     * 
     * @param id Identificador del evento a obtener.
     * @return ResponseEntity con el evento encontrado o un mensaje de error si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        
        try {
            // Devuelve el evento específico.
            return ResponseEntity.ok().body(eventService.getEvent(id));
        } catch (NoResultException err) {
            // Manejo de errores si el evento no existe.
            res.put("message", "El evento no existe.");
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

    /**
     * Maneja la solicitud PUT para editar un evento existente por su ID.
     * 
     * @param id Identificador del evento a editar.
     * @param eventDTO DTO que contiene los nuevos datos del evento.
     * @param result Resultados de la validación de los datos de entrada.
     * @return ResponseEntity con un mensaje de éxito o de error si la validación falla o si ocurre un problema.
     */
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editEvent(
            @PathVariable Long id, 
            @Valid @RequestBody EventSaveDTO eventDTO, 
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
            // Actualizar el evento usando el servicio
            Event updatedEvent = eventService.updateEvent(id, eventDTO);
            res.put("message", "El evento se actualizó correctamente");
            res.put("event", updatedEvent);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            // Manejo de errores si el ID no es válido.
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res);
        } catch (Exception e) {
            // Manejo de errores generales.
            res.put("message", "Error general al actualizar el evento");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /**
     * Maneja la solicitud DELETE para cancelar un evento específico por su ID.
     * 
     * @param id Identificador del evento a cancelar.
     * @return ResponseEntity con un mensaje de éxito o de error si ocurre un problema.
     */
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelEvent(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        
        // Busca el evento por su ID
        Event event = eventService.getEvent(id);

        try {
            // Cancela el evento y devuelve un mensaje de éxito.
            eventService.cancelEvent(event);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            // Manejo de errores si el evento no existe.
            res.put("message", "El evento con el ID brindado no existe");
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
}
