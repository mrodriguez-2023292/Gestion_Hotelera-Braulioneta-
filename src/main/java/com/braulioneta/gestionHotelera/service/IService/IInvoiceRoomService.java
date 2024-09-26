package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.InvoiceRoomSaveDTO;
import com.braulioneta.gestionHotelera.model.InvoiceRoom;

public interface IInvoiceRoomService {

    // Lista todas las facturas de habitaciones disponibles
    List<InvoiceRoom> listInvoiceRooms();

    // Obtiene una factura de habitación por su ID
    InvoiceRoom getInvoiceRoom(Long id);

    // Genera una nueva factura de habitación utilizando un DTO
    InvoiceRoom generateInvoice(InvoiceRoomSaveDTO invoiceRoomSaveDTO);
}