package com.example.duckfarm.http.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.duckfarm.http.services.ExcelReportService;
import com.example.duckfarm.http.services.PdfReportService;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;

@Validated
@RestController
@RequestMapping("report")
@Tag(name = "Reports Controller", description = "Endpoints to make operations about reports.")
public class ReportController {

    @Autowired
    private ExcelReportService excelService;

    @Autowired
    private PdfReportService pdfService;

    @GetMapping("excel/download")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        // Exemplo de dados
        List<String[]> data = Arrays.asList(
                new String[]{"Data1", "Data2", "Data3", "Data1", "Data2"},
                new String[]{"Data4", "Data5", "Data6", "Data4", "Data5"}
        );

        ByteArrayInputStream in = excelService.generateExcel(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=example.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

    @GetMapping("pdf/download")
    public void downloadPdf() throws IOException, FileNotFoundException, JRException {
        pdfService.export();
        return;
    }
}
