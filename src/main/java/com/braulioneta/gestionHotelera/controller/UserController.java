package com.braulioneta.gestionHotelera.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.service.UserService;


@RestController
@RequestMapping("/gestionHotelera/User")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getMethodName() {
        Map<String, Object> res = new HashMap<>();
        try { 
            return ResponseEntity.ok().body(userService.listUsers());
            //Error de conexión a la BD
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
            //Error de consulta a la BD
        } catch (DataAccessException err) {
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
            //Error general o genérico
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
