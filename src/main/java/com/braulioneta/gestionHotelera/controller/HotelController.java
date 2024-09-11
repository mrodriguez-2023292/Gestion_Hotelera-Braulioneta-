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

import com.braulioneta.gestionHotelera.DTO.HotelDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.service.HotelService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestionHotelera/hotel")
public class HotelController {
    
    @Autowired
    HotelService hotelService;

    @GetMapping()
    public ResponseEntity<?> getHotels() {
        Map<String, Object> res = new HashMap<>();
        try { 
            return ResponseEntity.ok().body(hotelService.listHotels());
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
    
    @PostMapping("/save")
    public ResponseEntity<?> saveHotel(
        @Valid @ModelAttribute HotelDTO hotel,
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
            Hotel newhotel = new Hotel(
                id,
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCategory(),
                hotel.getPhone(),
                hotel.getEmail_contact()
            );
            hotelService.saveHotel(newhotel);
            res.put("message", "Hotel guardado correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar el hotel, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelId(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(hotelService.getHotel(id));
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
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
    public ResponseEntity<?> editHotel(@PathVariable Long id, @RequestBody Hotel received){
        Map<String, Object> res = new HashMap<>();
        Hotel hotel = hotelService.getHotel(id);

        hotel.setName(received.getName());
        hotel.setAddress(received.getAddress());
        hotel.setCategory(received.getCategory());
        hotel.setPhone(received.getPhone());

        try { 
            return ResponseEntity.ok().body(hotelService.saveHotel(hotel));
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
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
    public ResponseEntity<?> deleteHotel(@PathVariable Long id){
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Hotel hotel = hotelService.getHotel(id);

        try { 
            hotelService.eliminateHotel(hotel);
            answer.put("Eliminado", true);
            return ResponseEntity.ok(answer);
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
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
