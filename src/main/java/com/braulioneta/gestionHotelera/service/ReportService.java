package com.braulioneta.gestionHotelera.service;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braulioneta.gestionHotelera.DTO.ReportSaveDTO;
import com.braulioneta.gestionHotelera.model.Hotel;
import com.braulioneta.gestionHotelera.model.Report;
import com.braulioneta.gestionHotelera.repository.HotelRepository;
import com.braulioneta.gestionHotelera.repository.ReportRepository;
import com.braulioneta.gestionHotelera.service.IService.IReportService;

// Clase que implementa los servicios relacionados con los informes en el sistema
@Service
public class ReportService implements IReportService {

    // Repositorio para acceder y manipular los datos de los informes
    @Autowired
    ReportRepository reportRepository;

    // Servicio que maneja las operaciones relacionadas con hoteles
    @Autowired
    HotelService hotelService;

    // Repositorio para acceder y manipular los datos de hoteles
    @Autowired
    HotelRepository hotelRepository;

    // Lista todos los informes disponibles en el sistema
    @Override
    public List<Report> listReports() {
       return reportRepository.findAll();
    }

    // Obtiene un informe por su ID
    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    // Agrega un nuevo informe utilizando un DTO
    @Override
    public Report addReport(ReportSaveDTO reportDTO) {
        try {
            // Convertir de tipo String a Timestamp para la fecha del informe
            Timestamp dateReport = Timestamp.valueOf(reportDTO.getReportDate());
            // Obtener el hotel asociado al informe utilizando su ID
            Hotel hotel = hotelService.getHotel(reportDTO.getHotelId());

            // Crear una nueva instancia de Report con los datos del DTO
            Report report = new Report(
                null,
                reportDTO.getHotelName(),
                dateReport,
                reportDTO.getTotalReservations(),
                reportDTO.getTotalRooms(),
                reportDTO.getOccupiedRooms(),
                reportDTO.getOccupancyRate(),
                reportDTO.getMostRequestedHotel(),
                hotel 
            );

            return reportRepository.save(report);
        } catch (Exception err) {
            throw new IllegalArgumentException("Error al parsear las fechas", err);
        }
    }

    // Elimina un informe existente del sistema
    @Override
    public void deleteReport(Report report) {
       reportRepository.delete(report);
    }
}