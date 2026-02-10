package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.entities.Menuitemstate;
import com.ort.edu.proyectofinal.entities.Menuitemtype;
import com.ort.edu.proyectofinal.repositories.MenuItemRepository;
import com.ort.edu.proyectofinal.repositories.MenuRepository;
import com.ort.edu.proyectofinal.repositories.MenuItemStateRepository;
import com.ort.edu.proyectofinal.repositories.MenuItemTypeRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class MenuImportService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemTypeRepository typeRepository;

    @Autowired
    private MenuItemStateRepository stateRepository;

    public void importExcel(MultipartFile file) throws Exception {
        importExcel(1, file);
    }

    /**
     * Estructura esperada del Excel (hoja 0):
     *  Col 0: Name
     *  Col 1: Description
     *  Col 2: BasePrice
     *  Col 3: TypeId
     *
     *  1	Entrada
     * 2	Principal
     * 3	Postre
     * 4	Bebida
     */
    public void importExcel(Integer menuId, MultipartFile file) throws Exception {

        if (file == null || file.isEmpty())
            throw new IllegalArgumentException("El archivo está vacío");

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu no encontrado: " + menuId));

        Menuitemstate state = stateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("StateId no encontrado: " + 1));

        // try-with-resources para cerrar el workbook automáticamente
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            // Empieza en 1 para saltar la fila de encabezado
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String name = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                double price = row.getCell(2).getNumericCellValue();
                int typeId = (int) row.getCell(3).getNumericCellValue();

                Menuitemtype type = typeRepository.findById(typeId)
                        .orElseThrow(() -> new RuntimeException("TypeId no encontrado: " + typeId));

                Menuitem item = new Menuitem();
                item.setName(name);
                item.setDescription(description);
                item.setBasePrice(BigDecimal.valueOf(price));
                item.setMenu(menu);
                item.setType(type);
                item.setState(state);
                item.setCreatedDate(LocalDateTime.now());
                item.setLastUpdate(LocalDateTime.now());

                menuItemRepository.save(item);
            }
        }
    }
}
