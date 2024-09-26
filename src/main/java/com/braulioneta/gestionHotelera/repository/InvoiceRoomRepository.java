package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.InvoiceRoom;

// Esta interfaz maneja las operaciones con la base de datos para la entidad invoiceRoom
public interface InvoiceRoomRepository extends JpaRepository <InvoiceRoom, Long>{

    // Repositorio para la entidad InvoiceRoom, proporciona operaciones CRUD básicas
}
