package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.ReportDTO;
import com.braulioneta.gestionHotelera.model.Report;

/**
 * IReportService es una interfaz que define los métodos necesarios para gestionar
 * los informes en el sistema de gestión hotelera. Esta interfaz asegura que cualquier
 * clase que la implemente, como ReportService, proporcione la funcionalidad básica
 * para manejar informes.
 */
public interface IReportService {

    /**
     * Devuelve una lista de todos los informes disponibles.
     *
     * @return List<Report> Una lista de todos los informes.
     */
    List<Report> listReports();

    /**
     * Busca y devuelve un informe específico basado en su ID.
     *
     * @param id El ID del informe que se desea obtener.
     * @return Report El informe correspondiente o null si no se encuentra.
     */
    Report getReport(Long id);

    /**
     * Agrega un nuevo informe a la base de datos.
     *
     * @param reportDTO Un objeto ReportDTO que contiene los datos necesarios para crear un informe.
     * @return Report El informe que fue agregado a la base de datos.
     */
    Report addReport(ReportDTO reportDTO);

    /**
     * Elimina un informe existente de la base de datos.
     *
     * @param report El informe que se desea eliminar.
     */
    void deleteReport(Report report);
}
