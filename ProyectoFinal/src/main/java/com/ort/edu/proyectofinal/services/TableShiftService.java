package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.entities.Tableshift;
import com.ort.edu.proyectofinal.entities.Tables;
import com.ort.edu.proyectofinal.entities.Tablestate;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TableStateRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class TableShiftService {

    @Autowired
    private TableShiftRepository tableShiftRepo;

    @Autowired
    private TablesRepository tablesRepo;

    @Autowired
    private TableStateRepository tablestateRepo;

    @Transactional
    public boolean reserveTableShift(int tableId, int shiftId) throws TableException {

        Tables table = tablesRepo.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("La mesa no existe"));

        Tablestate busyState = tablestateRepo.findById(CoreManager._BUSY_STATE_ID)
                .orElseThrow(() -> new IllegalArgumentException("El estado indicado no existe"));

        List<Tableshift> shifts = tableShiftRepo.findByTableId(tableId);

        if (!shifts.isEmpty()) {
            for (Tableshift shift : shifts) {
                if (shift.getId().getShiftId() == shiftId) {
                    if (shift.getState().getId() == CoreManager._AVAILABLE_STATE_ID) {
                        shift.setState(busyState);
                        tableShiftRepo.save(shift);
                        return true;
                    }
                    else {
                        throw new TableException("La mesa ya no está disponible para ese horario");
                    }
                }
            }
        }

        return false;
    }
}
