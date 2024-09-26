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

import com.braulioneta.gestionHotelera.DTO.HotelSaveDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.service.HotelService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

// Controlador REST para manejar las solicitudes relacionadas con los hoteles
@RestController
@RequestMapping("/gestionHotelera/hotel") // Mapea las solicitudes a esta URL base
public class HotelController {
    
    @Autowired
    HotelService hotelService; // Servicio para manejar la lógica de negocio de hoteles

    // Obtiene la lista de todos los hoteles.
    // @return ResponseEntity con la lista de hoteles si la operación es exitosa. Si ocurre un error, devuelve una respuesta con un mensaje de error adecuado.
    @GetMapping()
    public ResponseEntity<?> getHotels() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(hotelService.listHotels()); // Devuelve la lista de hoteles
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

    // Obtiene un hotel por su ID.
    // @param id ID del hotel que se desea obtener.
    // @return ResponseEntity con el hotel si se encuentra o un mensaje de error si no existe.
    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelId(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(hotelService.getHotel(id)); // Devuelve el hotel solicitado
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
            return ResponseEntity.status(503).body(res); // Hotel no encontrado
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

    // Guarda un nuevo hotel en la base de datos.
    // @param hotel DTO que contiene los datos del hotel a guardar.
    // @param result Resultados de la validación del DTO.
    // @return ResponseEntity con un mensaje de éxito si la operación es exitosa, o un mensaje de error si ocurre un problema durante la validación o el guardado.
    @PostMapping("/addHotel")
    public ResponseEntity<?> addHotel(
        @Valid @ModelAttribute HotelSaveDTO hotel, // DTO de entrada para la creación de un hotel
        BindingResult result // Resultado de la validación
    ) {
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){ // Verifica si hay errores de validación
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
            res.put("Errors", errors);
            return ResponseEntity.badRequest().body(res); // Respuesta con errores de validación
        }
        try {
            Long id = null; // ID por defecto, se generará automáticamente
            Hotel newhotel = new Hotel(
                id,
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCategory(),
                hotel.getPhone(),
                hotel.getEmailContact()
            );
            hotelService.addHotel(newhotel); // Llama al servicio para agregar el nuevo hotel
            res.put("message", "Hotel guardado correctamente");
            return ResponseEntity.ok(res); // Respuesta exitosa
        } catch (Exception err) {
            res.put("message", "Error al guardar el hotel, intente de nuevo más tarde");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res); // Error interno al guardar el hotel
        }
    }

    // Edita un hotel existente.
    // @param id ID del hotel que se desea editar.
    // @param received Objeto Hotel con los datos actualizados.
    // @return ResponseEntity con el hotel actualizado si la operación es exitosa, o un mensaje de error si ocurre un problema.
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editHotel(@PathVariable Long id, @RequestBody Hotel received) {
        Map<String, Object> res = new HashMap<>();
        Hotel hotel = hotelService.getHotel(id); // Obtiene el hotel a editar

        // Actualiza los campos del hotel
        hotel.setName(received.getName());
        hotel.setAddress(received.getAddress());
        hotel.setCategory(received.getCategory());
        hotel.setPhone(received.getPhone());

        try {
            return ResponseEntity.ok().body(hotelService.addHotel(hotel)); // Llama al servicio para actualizar el hotel
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
            return ResponseEntity.status(503).body(res); // Hotel no encontrado para actualización
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
            return ResponseEntity.internalServerError().body(res); // Error interno al actualizar el hotel
        }
    }

    // Elimina un hotel por su ID.
    // @param id ID del hotel que se desea eliminar.
    // @return ResponseEntity con un mensaje de confirmación si la operación es exitosa, o un mensaje de error si ocurre un problema.
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        Map<String, Boolean> answer = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        Hotel hotel = hotelService.getHotel(id); // Obtiene el hotel a eliminar

        try {
            hotelService.deleteHotel(hotel); // Llama al servicio para eliminar el hotel
            answer.put("Eliminado", true);
            return ResponseEntity.ok(answer); // Respuesta exitosa
        } catch (NoResultException err) {
            res.put("message", "El hotel con el ID proporcionado no existe");
            return ResponseEntity.status(503).body(res); // Hotel no encontrado para eliminar
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
            return ResponseEntity.internalServerError().body(res); // Error interno al eliminar el hotel
        }
    }
}