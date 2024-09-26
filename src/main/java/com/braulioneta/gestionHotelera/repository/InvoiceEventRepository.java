package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.InvoiceEvent;

// Esta interfaz maneja las operaciones con la base de datos para la entidad invoiceEvent
public interface InvoiceEventRepository extends JpaRepository<InvoiceEvent, Long> {
    
    // Repositorio para la entidad InvoiceEvent, proporciona operaciones CRUD básicas
}