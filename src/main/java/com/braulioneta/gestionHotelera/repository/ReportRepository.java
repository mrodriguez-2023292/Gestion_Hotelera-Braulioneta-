package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Report;

// Esta interfaz maneja las operaciones con la base de datos para la entidad report
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    // Repositorio para la entidad Report, proporciona operaciones CRUD básicas
}