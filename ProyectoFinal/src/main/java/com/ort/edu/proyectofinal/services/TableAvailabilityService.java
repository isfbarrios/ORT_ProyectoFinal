package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.TableAvailabilityDTO;
import com.ort.edu.proyectofinal.entities.Tableavailability;
import com.ort.edu.proyectofinal.repositories.TableAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class TableAvailabilityService {

    @Autowired
    private TableAvailabilityRepository repo;

    public List<Tableavailability> getTables() {

        ZoneId zone = ZoneId.systemDefault();
        System.out.println();
        System.out.println();

        System.out.println("zone: " + zone);
        System.out.println("LocalDate.now(): " + LocalDate.now());

        System.out.println("LocalTime.MAX: " + LocalTime.MAX);
        System.out.println("LocalDate.now().atTime(LocalTime.MAX): " + LocalDate.now().atTime(LocalTime.MAX));

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(zone).toLocalDateTime();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX).atZone(zone).toLocalDateTime();

        System.out.println("startOfDay: " + startOfDay);
        System.out.println("endOfDay: " + endOfDay);


        System.out.println();
        System.out.println();

        return repo.findAllByReservedDate(startOfDay, endOfDay);
    }

    public List<TableAvailabilityDTO> getAvailableTables() {

        List<Tableavailability> availables2 = getTables();

        System.out.println("TableAvailability.getAvailableTables.availables2.size(): " + availables2.size());

        List<TableAvailabilityDTO> availables = getTables().stream()
                .filter(aux -> aux.getState().getId() == 1)
                .map(TableAvailabilityDTO::new)
                .toList();

        System.out.println("TableAvailability.getAvailableTables.availables.size(): " + availables.size());

        return availables;
    }
}
