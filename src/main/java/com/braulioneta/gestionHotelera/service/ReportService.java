package com.braulioneta.gestionHotelera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.braulioneta.gestionHotelera.model.Report;
import com.braulioneta.gestionHotelera.repository.ReportRepository;
import com.braulioneta.gestionHotelera.service.IService.IReportService;

public class ReportService implements IReportService{

    @Autowired
    ReportRepository reportRepository;

    @Override
    public List<Report> listReports() {
       return  reportRepository.findAll();

    }

    @Override
    public Report getReport(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public Report addReport(Report report) {
        return  reportRepository.save(report);

    }

    @Override
    public void deleteReport(Report report) {
       reportRepository.delete(report);
    }


}
