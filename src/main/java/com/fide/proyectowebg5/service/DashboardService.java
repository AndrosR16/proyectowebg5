package com.fide.proyectowebg5.service;

import org.springframework.stereotype.Service;

import com.fide.proyectowebg5.repository.DashboardRepository;

@Service
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardService(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    public Long totalUsuariosActivos() {
        return dashboardRepository.totalUsuariosActivos();
    }

    public Long totalCanchasActivas() {
        return dashboardRepository.totalCanchasActivas();
    }

    public Long totalReservas() {
        return dashboardRepository.totalReservas();
    }

    public Long totalIngresos() {
        return dashboardRepository.totalIngresos();
    }
}