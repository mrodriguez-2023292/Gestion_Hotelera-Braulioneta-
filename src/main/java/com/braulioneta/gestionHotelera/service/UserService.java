package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.model.User;
import com.braulioneta.gestionHotelera.repository.UserRepository;
import com.braulioneta.gestionHotelera.service.IService.IUserService;
import com.braulioneta.gestionHotelera.utils.BCryptSecurity;

// Clase que implementa los servicios relacionados con los usuarios en el sistema
@Service
public class UserService implements IUserService {

    // Repositorio para acceder y manipular los datos de los usuarios
    @Autowired
    UserRepository userRepository;

    // Servicio para manejar la seguridad de las contraseñas
    @Autowired
    BCryptSecurity bCryptSecurity;

    // Devuelve una lista de todos los usuarios registrados en el sistema
    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    // Obtiene un usuario por su ID
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Registra un nuevo usuario en el sistema
    @Override
    public User register(User user) {
        if(user.getPassword() != null){
            // Encripta la contraseña antes de guardar el usuario
            user.setPassword(bCryptSecurity.encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    // Inicia sesión de un usuario con sus credenciales
    @Override
    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        
        if(user == null) {
            System.out.println("Usuario no encontrado.");
            return false; // Usuario no existe
        }
        
        if (!bCryptSecurity.checkPassword(password, user.getPassword())) {
            System.out.println("Contraseña incorrecta para el usuario: " + username);
            return false; // Contraseña incorrecta
        }
        
        System.out.println("Usuario autenticado correctamente.");
        return true; // Inicio de sesión exitoso
    }
}