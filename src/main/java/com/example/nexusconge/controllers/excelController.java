package com.example.nexusconge.controllers;


import com.example.nexusconge.entities.Conge;
import com.example.nexusconge.entities.user;
import com.example.nexusconge.repositories.congeRepo;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


//@CrossOrigin("*")
    @RestController
    @RequestMapping("/excel")
    public class excelController {

        @Autowired
        private congeRepo congeRepo;
        @Autowired
        private com.example.nexusconge.repositories.userRepo userRepo;

    @PostMapping("/export")
    @Transactional(readOnly = true)
    public ResponseEntity<InputStreamResource> exportSprintsToExcel() {
        List<user> users = userRepo.findAll();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (user user : users) {
                List<Conge> sprints = user.getCongeList();

                Sheet sheet = workbook.createSheet(user.getUsername() + "_Sprints");
                createHeaderRow(sheet);

                int rowNumber = 1;
                for (Conge sprint : sprints) {
                    Row row = sheet.createRow(rowNumber++);
                    fillDataRow(row, sprint);
                }
            }

            // Spécifiez le chemin complet du répertoire "Téléchargements"
            String downloadsDirectory = System.getProperty("user.home") + "/Downloads/";
            String filePath = downloadsDirectory + "conges.xlsx";

            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Créer les répertoires parents si nécessaire

            try (OutputStream fileOutputStream = new FileOutputStream(file)) {
                workbook.write(fileOutputStream);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=sprints.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    private void createHeaderRow(Sheet sheet) {
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Date Début", "Date Fin", "raison",  "user"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        }

    private void fillDataRow(Row row, Conge conge) {
        Cell idCell = row.createCell(0);
        idCell.setCellValue(conge.getIdConge());

        Cell startDateCell = row.createCell(1);
        startDateCell.setCellValue(conge.getDatedebut().toString()); // Convert to String, adjust as needed for the desired format

        Cell finDateCell = row.createCell(2);
        finDateCell.setCellValue(conge.getDatefin().toString());

        Cell raisonCell = row.createCell(3);
        raisonCell.setCellValue(conge.getRaison());

        // Concatenate usernames of users associated with the conge
        String users = conge.getUsers().stream().map(user::getUsername).collect(Collectors.joining(", "));
        Cell usersCell = row.createCell(4);
        usersCell.setCellValue(users);
    }


}
