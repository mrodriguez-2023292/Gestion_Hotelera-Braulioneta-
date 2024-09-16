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
import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.ReportDTO;
import com.braulioneta.gestionHotelera.model.Report;
import com.braulioneta.gestionHotelera.service.ReportService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestionHotelera/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping()
    public ResponseEntity<?> getReport(){
        Map<String, Object> res = new HashMap<>();

        try{
            return ResponseEntity.ok().body(reportService.listReports());
        }catch (CannotCreateTransactionException err) {
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

    @PostMapping("/addReport")
    public ResponseEntity<?>  addReport(
        @Valid @ModelAttribute ReportDTO report,
        BindingResult result
    ){
        Map<String, Object>  res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }
        try{
           Long id = null;
           Report newreport = new Report(
            id,
            report.getHotel_name(),
            report.getReport_date(),
            report.getTotal_reservations(),
            report.getTotal_rooms(),
            report.getOccupied_rooms(),
            report.getOccupancy_rate(),
            report.getMost_requested_hotel()
           );
           reportService.addReport(newreport);
           res.put("message", "Reporte  agregado con exito");
            return ResponseEntity.ok(res);
        }catch (Exception err) {
            res.put("message", "Error al guardar la habitacion, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try{
            return ResponseEntity.ok().body(reportService.getReport(id));
        }catch (NoResultException err) {
            res.put("message", "El reporte no existe");
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
    public ResponseEntity<?> editReport(@PathVariable Long id, @RequestBody Report report) {
        Map<String, Object> res = new HashMap<>();
        Report report2 = reportService.getReport(id);

        report2.setHotel_name(report.getHotel_name());
        report2.setReport_date(report.getReport_date());
        report2.setTotal_reservations(report.getTotal_reservations());
        report2.setTotal_rooms(report.getTotal_rooms());
        report2.setOccupied_rooms(report.getOccupied_rooms());
        report2.setOccupancy_rate(report.getOccupancy_rate());
        report2.setMost_requested_hotel(report.getMost_requested_hotel());

        try{
            return ResponseEntity.ok().body(reportService.addReport(report2));
        }catch (NoResultException err) {
            res.put("message", "El reporte no existe");
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();

        Report report = reportService.getReport(id);

        try{
            reportService.deleteReport(report);
            answer.put("Eliminado", true);
            return ResponseEntity.ok(answer);
        }catch (NoResultException err) {
            res.put("message", "El reporte no existe");
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
