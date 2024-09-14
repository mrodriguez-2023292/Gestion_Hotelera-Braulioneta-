package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    public User findByUsername(String username);
}
