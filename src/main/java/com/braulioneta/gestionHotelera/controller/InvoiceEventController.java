package com.braulioneta.gestionHotelera.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.InvoiceEventSaveDTO;
import com.braulioneta.gestionHotelera.model.InvoiceEvent;
import com.braulioneta.gestionHotelera.service.InvoiceEventService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador REST para manejar las operaciones relacionadas con las facturas de eventos
@RestController
@RequestMapping("/gestionHotelera/invoiceEvent")
public class InvoiceEventController {

    @Autowired
    InvoiceEventService  invoiceEventService;

    // Obtiene todas las facturas de eventos
    @GetMapping()
    public ResponseEntity<?>  getInvoiceEvent(){
        Map<String, Object> res  = new HashMap<>();
        try {
            return ResponseEntity.ok().body(invoiceEventService.listInvoiceEvents());
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

    // Obtiene una factura de evento específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable Long id){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(invoiceEventService.getInvoiceEvent(id));
        } catch (NoResultException err) {
            res.put("message", "La factura no existe.");
            return ResponseEntity.status(404).body(res);
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
    
    // Genera una nueva factura de evento
    @PostMapping("/generateInvoice")
    public ResponseEntity<?> generateInvoiceEvent(
        @Valid @ModelAttribute InvoiceEventSaveDTO  invoiceEventSaveDTO,
        BindingResult result
    ){
        Map<String, Object> res  = new HashMap<>();
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
            InvoiceEvent invoice = invoiceEventService.generateInvoice(invoiceEventSaveDTO);
            res.put("message", "Factura generada correctamente");
            res.put("invoice", invoice);
            return ResponseEntity.ok(res);
        }  catch (Exception err) {
            res.put("message", "Error al guardar la Factura, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
}