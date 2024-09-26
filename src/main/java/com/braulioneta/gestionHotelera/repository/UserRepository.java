package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.User;

// Esta interfaz maneja las operaciones con la base de datos para la entidad User
public interface UserRepository extends JpaRepository<User, Long> {

    // Encuentra un usuario por su nombre de usuario
    public User findByUsername(String username);
}