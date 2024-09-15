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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.UserLoginDTO;
import com.braulioneta.gestionHotelera.DTO.UserRegisterDTO;
import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.service.UserService;

import jakarta.validation.Valid;



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

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @Valid @ModelAttribute UserRegisterDTO user,
        BindingResult result
        ) {
            Map<String,Object> res = new HashMap<>();
            if(result.hasErrors()){
                List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
                res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
                res.put("Errors",errors);
                return ResponseEntity.badRequest().body(res);
            }
            try {
                Long id = null;
                User newUser = new User(
                    id,
                    user.getName(),
                    user.getSurname(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getPhone(),
                    user.getUserType()
                );
                userService.register(newUser);
                res.put("message", "Usuario guardado exitosamente");
                return ResponseEntity.ok(res);
            } catch (Exception e) {
                res.put("message", "Error al guardar el usuario, intentelo en otro momento0");
                res.put("Error", e);
                return ResponseEntity.internalServerError().body(res);
            }
        
            
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            System.out.println(user.getPassword());
            if(userService.login(user.getUsername(), user.getPassword())){
                res.put("message", "Usuario logeado satisfactoriamente");
                return ResponseEntity.ok(res);
            }else{
                res.put("message", "Credenciales inválidas");
                return ResponseEntity.status(401).body(res);
            }
        } catch (Exception err) {
            res.put("message", "Error general al iniciar sesión");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }
    

}
