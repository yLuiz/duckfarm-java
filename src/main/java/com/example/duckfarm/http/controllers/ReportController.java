package com.example.duckfarm.http.controllers;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.duckfarm.db.model.Duck;
import com.example.duckfarm.http.services.DuckService;
import com.example.duckfarm.http.services.ExcelReportService;
import com.example.duckfarm.http.services.PdfReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;

@Validated
@RestController
@RequestMapping("report")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Reports Controller", description = "Endpoints to make operations about reports.")
public class ReportController {

    @Autowired
    private ExcelReportService excelService;

    @Autowired
    private PdfReportService pdfService;

    @Autowired
    private DuckService duckService;

    @GetMapping("excel/download")
    public ResponseEntity<byte[]> downloadExcel(
        @RequestParam(value = "size", required = false) Integer size
    ) throws IOException {

        size = size == null ? 10 : size;

        Page<Duck> duckPage = duckService.findAll(0, size);

        List<Duck> ducks = duckPage.getContent();

        List<String[]> data = new ArrayList<>();
        for (Duck d : ducks) {

            String customerName = d.getCustomer() != null ? d.getCustomer().getName() : "-";
            String sellValue = d.getPurchase() != null ? ("R$   " + d.getPurchase().getPrice().toString()) : "-";
            String customerHasDiscount = d.getCustomer() != null ? (d.getCustomer().getHas_discount() == 1 ? "com Desconto" : "sem Desconto") : "-";
            String duckName = d.getMother() != null ?  "[Mãe] " + d.getMother().getName() + " - " + d.getName() : d.getName();
            String isAvailable = d.getCustomer() != null ? "Vendido" : "Disponível";

            data.add(new String[]{duckName, isAvailable, customerName, customerHasDiscount, sellValue});
        }

        ByteArrayInputStream in = excelService.generateExcel(data);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=duck-reports.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(in.readAllBytes());
    }

    // Funcionalidade nãok implementada.
    @GetMapping("pdf/download")
    @Operation(hidden=true)
    public byte[] downloadPdf() throws IOException, FileNotFoundException, JRException {
        return pdfService.export();
    }
}
