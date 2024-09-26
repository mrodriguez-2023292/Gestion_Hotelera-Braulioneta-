package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.model.User;

public interface IUserService {

    // Lista todos los usuarios registrados en el sistema
    List<User> listUsers();

    // Obtiene un usuario por su ID
    User getUser(Long id);

    // Registra un nuevo usuario en el sistema
    User register(User user);

    // Inicia sesión de un usuario con sus credenciales
    boolean login(String username, String password);
}