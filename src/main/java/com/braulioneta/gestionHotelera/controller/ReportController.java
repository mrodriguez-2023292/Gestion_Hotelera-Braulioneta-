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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.ReportDTO;
import com.braulioneta.gestionHotelera.model.Report;
import com.braulioneta.gestionHotelera.service.ReportService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

/**
 * La clase ReportController maneja las solicitudes HTTP relacionadas con los reportes.
 * Proporciona endpoints para listar, agregar, obtener y eliminar reportes.
 * 
 * @RestController: Indica que esta clase es un controlador REST que maneja solicitudes 
 * HTTP y devuelve respuestas.
 * 
 * @RequestMapping("/gestionHotelera/report"): Especifica la URL base para las 
 * solicitudes manejadas por este controlador.
 */
@RestController
@RequestMapping("/gestionHotelera/report")
public class ReportController {

    @Autowired
    ReportService reportService; // Servicio que maneja la lógica de negocio para los reportes.

    /**
     * Maneja la solicitud GET para obtener todos los reportes.
     * 
     * @return ResponseEntity con la lista de reportes o un mensaje de error si ocurre un problema.
     */
    @GetMapping()
    public ResponseEntity<?> getReport() {
        Map<String, Object> res = new HashMap<>();

        try {
            // Devuelve todos los reportes en formato JSON.
            return ResponseEntity.ok().body(reportService.listReports());
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
     * Maneja la solicitud POST para agregar un nuevo reporte.
     * 
     * @param report DTO que contiene los datos del reporte a agregar.
     * @param result Resultados de la validación de los datos de entrada.
     * @return ResponseEntity con un mensaje de éxito o de error si la validación falla o si ocurre un problema.
     */
    @PostMapping("/addReport")
    public ResponseEntity<?> addReport(
        @Valid @ModelAttribute ReportDTO report,
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
            // Agrega el reporte y devuelve un mensaje de éxito.
            Report reportAdd = reportService.addReport(report);
            res.put("message", "Reporte agregado con éxito");
            res.put("report", reportAdd);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            // Manejo de errores al intentar guardar el reporte.
            res.put("message", "Error al guardar el reporte, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /**
     * Maneja la solicitud GET para obtener un reporte específico por su ID.
     * 
     * @param id Identificador del reporte a obtener.
     * @return ResponseEntity con el reporte encontrado o un mensaje de error si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        
        try {
            // Devuelve el reporte específico.
            return ResponseEntity.ok().body(reportService.getReport(id));
        } catch (NoResultException err) {
            // Manejo de errores si el reporte no existe.
            res.put("message", "El reporte no existe");
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
     * Maneja la solicitud DELETE para eliminar un reporte específico por su ID.
     * 
     * @param id Identificador del reporte a eliminar.
     * @return ResponseEntity con un mensaje de éxito o de error si ocurre un problema.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        Report report = reportService.getReport(id);

        try {
            // Elimina el reporte y devuelve un mensaje de éxito.
            reportService.deleteReport(report);
            answer.put("Eliminado", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            // Manejo de errores si el reporte no existe.
            res.put("message", "El reporte no existe");
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