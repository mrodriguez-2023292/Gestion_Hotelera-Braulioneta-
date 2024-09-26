package com.braulioneta.gestionHotelera.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.InvoiceRoomSaveDTO;
import com.braulioneta.gestionHotelera.model.InvoiceRoom;
import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.repository.InvoiceRoomRepository;
import com.braulioneta.gestionHotelera.service.IService.IInvoiceRoomService;

// Clase que implementa los servicios relacionados con las facturas de habitaciones en el sistema
@Service
public class InvoiceRoomService implements IInvoiceRoomService {

    // Repositorio para acceder y manipular los datos de las facturas de habitaciones
    @Autowired
    InvoiceRoomRepository invoiceRoomRepository;

    // Servicio que maneja las operaciones relacionadas con habitaciones
    @Autowired
    RoomService roomService;

    // Lista todas las facturas de habitaciones disponibles en el sistema
    @Override
    public List<InvoiceRoom> listInvoiceRooms() {
        return invoiceRoomRepository.findAll();        
    }

    // Obtiene una factura de habitación por su ID
    @Override
    public InvoiceRoom getInvoiceRoom(Long id) {
       return invoiceRoomRepository.findById(id).orElse(null);
    }

    // Genera una nueva factura de habitación utilizando un DTO
    @Override
    public InvoiceRoom generateInvoice(InvoiceRoomSaveDTO invoiceRoomSaveDTO) {
        try {
            // Convertir de tipo String a Timestamp para la fecha de la factura
            Timestamp date = Timestamp.valueOf(invoiceRoomSaveDTO.getDate());
            // Obtener la habitación asociada a la factura utilizando su ID
            Room room = roomService.getRoom(invoiceRoomSaveDTO.getRoomId());
            
            // Crear una nueva instancia de InvoiceRoom con los datos del DTO
            InvoiceRoom invoice = new InvoiceRoom(
                null,
                date,
                invoiceRoomSaveDTO.getTotal(),
                invoiceRoomSaveDTO.getPaymentMethod(),
                room
            );
            return invoiceRoomRepository.save(invoice);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error en la habitacion", err);
        }
    }
}