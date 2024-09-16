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

@RestController
@RequestMapping("/gestionHotelera/additionalService")
public class AdditionalServiceController {
    
    @Autowired
    AdditionalServicesService additionalServicesService;

    @GetMapping()
    public ResponseEntity<?> getAdditionalService(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(additionalServicesService.listAdditionalServices());
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la DB");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping("/addService")
    public ResponseEntity<?> listAdditionalService(
        @Valid @RequestBody AdditionalServiceDTO serviceDTO,
        BindingResult result
    ){

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
            AdditionalService additionalService = additionalServicesService.addAdditionalService(serviceDTO);
            res.put("message", "El Servicio se reservo correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al guardar, intente de nuevo más tarde");
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> getAdditionalService(@PathVariable Long id){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(additionalServicesService.getAdditionalService(id));
        } catch (NoResultException e) {
            res.put("message", "El evento no existe.");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateService(
            @PathVariable Long id, 
            @Valid @RequestBody AdditionalServiceDTO serviceDTO, 
            BindingResult result) {
        
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
            AdditionalService updatedAdditionalService = additionalServicesService.updateService(id, serviceDTO);
            res.put("message", "El Servicio se actualizó correctamente");
            res.put("service", updatedAdditionalService);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            res.put("message", e.getMessage());
            return ResponseEntity.status(404).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el Servicio");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> deletedService(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        AdditionalService additionalService = additionalServicesService.getAdditionalService(id);

        try{
            additionalServicesService.deleteService(additionalService);
            answer.put("Cancelado", true);
            return ResponseEntity.ok(answer);

        }catch (NoResultException e) {
            res.put("message", "El Servicio con el ID brindado no existe");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } 
    }
}
