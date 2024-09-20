package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Report;

public interface ReportRepository extends JpaRepository <Report, Long>{
  
}
