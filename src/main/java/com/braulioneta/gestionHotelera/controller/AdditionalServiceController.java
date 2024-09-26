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

import com.braulioneta.gestionHotelera.DTO.AdditionalServiceDTO;
import com.braulioneta.gestionHotelera.model.AdditionalService;
import com.braulioneta.gestionHotelera.service.AdditionalServicesService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador REST para manejar las solicitudes relacionadas con los servicios adicionales
@RestController
@RequestMapping("/gestionHotelera/additionalService") // Mapea las solicitudes a esta URL base
public class AdditionalServiceController {
    
    @Autowired
    AdditionalServicesService additionalServicesService; // Servicio para manejar la lógica de negocio de servicios adicionales

    // Obtiene la lista de todos los servicios adicionales
    @GetMapping()
    public ResponseEntity<?> getAdditionalService() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(additionalServicesService.listAdditionalServices());
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        }
    }

    // Obtiene un servicio adicional específico por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdditionalService(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        
        try {
            return ResponseEntity.ok().body(additionalServicesService.getAdditionalService(id));
        } catch (NoResultException e) {
            res.put("message", "El evento no existe.");
            return ResponseEntity.status(503).body(res); // Servicio no encontrado
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        }
    }

    // Agrega un nuevo servicio adicional
    @PostMapping("/addService")
    public ResponseEntity<?> listAdditionalService(
        @Valid @ModelAttribute AdditionalServiceDTO serviceDTO,
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
            AdditionalService additionalService = additionalServicesService.addAdditionalService(serviceDTO);
            res.put("message", "El Servicio se reservó correctamente");
            res.put("service", additionalService);
            return ResponseEntity.ok(res); // Respuesta exitosa con el servicio agregado
        } catch (Exception e) {
            res.put("message", "Error al guardar, intente de nuevo más tarde");
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno al guardar el servicio
        }
    }

    // Actualiza un servicio adicional existente por su ID
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Long id, 
            @Valid @RequestBody AdditionalServiceDTO serviceDTO, 
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
            AdditionalService updatedAdditionalService = additionalServicesService.updateService(id, serviceDTO);
            res.put("message", "El Servicio se actualizó correctamente");
            res.put("service", updatedAdditionalService);
            return ResponseEntity.ok(res); // Respuesta exitosa con el servicio actualizado
        } catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res); // Servicio no encontrado para actualización
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el Servicio");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno al actualizar el servicio
        }
    }

    // Elimina un servicio adicional por su ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletedService(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        
        AdditionalService additionalService = additionalServicesService.getAdditionalService(id);

        try{
            additionalServicesService.deleteService(additionalService);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer); // Respuesta exitosa indicando que se ha cancelado el servicio

        }catch (NoResultException e) {
            res.put("message", "El Servicio con el ID brindado no existe");
            return ResponseEntity.status(503).body(res); // Servicio no encontrado para eliminar
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error de conexión a la base de datos
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res); // Error al consultar la base de datos
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno del servidor
        } 
    }
}