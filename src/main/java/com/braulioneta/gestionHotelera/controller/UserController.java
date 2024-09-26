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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulioneta.gestionHotelera.DTO.UserLoginDTO;
import com.braulioneta.gestionHotelera.DTO.UserRegisterDTO;
import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.service.UserService;

import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestionHotelera/user")
public class UserController {

    @Autowired
    UserService userService;

    // Método para listar todos los usuarios
    @GetMapping()
    public ResponseEntity<?> getMethodName() {
        Map<String, Object> res = new HashMap<>();
        try { 
            return ResponseEntity.ok().body(userService.listUsers());
        } catch (CannotCreateTransactionException err) {
            // Error de conexión a la BD
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            // Error de consulta a la BD
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            // Error general
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Método para registrar un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(
        @Valid @ModelAttribute UserRegisterDTO user,
        BindingResult result
        ) {
            Map<String,Object> res = new HashMap<>();
            if(result.hasErrors()){
                // Manejo de errores de validación
                List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage()).collect(Collectors.toList());
                res.put("message", "Error con las validaciones, por favor ingresa todos los campos");
                res.put("Errors",errors);
                return ResponseEntity.badRequest().body(res);
            }
            try {
                Long id = null; // ID del nuevo usuario
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
                // Manejo de errores al guardar el usuario
                res.put("message", "Error al guardar el usuario, intentelo en otro momento");
                res.put("Error", e);
                return ResponseEntity.internalServerError().body(res);
            }
    }
    
    // Método para iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            System.out.println(user.getPassword());
            if(userService.login(user.getUsername(), user.getPassword())){
                res.put("message", "Usuario logeado satisfactoriamente");
                return ResponseEntity.ok(res);
            } else {
                res.put("message", "Credenciales inválidas");
                return ResponseEntity.status(401).body(res);
            }
        } catch (Exception err) {
            // Error general al iniciar sesión
            res.put("message", "Error general al iniciar sesión");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Método para editar un usuario existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User user) {
        Map<String, Object> res = new HashMap<>();
        User userEdit = userService.getUser(id);

        // Actualizar los atributos del usuario
        userEdit.setName(user.getName());
        userEdit.setSurname(user.getSurname());
        userEdit.setUsername(user.getUsername());
        userEdit.setEmail(user.getEmail());
        userEdit.setPassword(user.getPassword());
        userEdit.setPhone(user.getPhone());
        userEdit.setUserType(user.getUserType());

        try {
            return ResponseEntity.ok().body(userService.register(userEdit));
        } catch (NoResultException err) {
            // Usuario no encontrado
            res.put("message", "El usuario con el ID proporcionado no existe");
            return ResponseEntity.status(503).body(res);
        } catch (CannotCreateTransactionException err) {
            // Error de conexión a la BD
            res.put("message", "Error al momento de conectarse a la BD");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            // Error de consulta a la BD
            res.put("message", "Error al momento de consultar a la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            // Error general
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        } 
    }
}