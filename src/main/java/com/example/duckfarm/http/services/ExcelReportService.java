package com.example.duckfarm.http.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelReportService {

    public ByteArrayInputStream generateExcel(List<String[]> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Duck Report");

            CellStyle boldStyle = workbook.createCellStyle();
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            boldStyle.setFont(boldFont);

            // Criando o cabeçalho
            Row headerRow = sheet.createRow(0);
            List<String> columnNames = Arrays.asList(
                    "Nome",
                    "Status",
                    "Cliente",
                    "Tipo do cliente",
                    "Valor"
            );

            for (int i = 0; i < columnNames.size(); i++) {
                Cell cell = headerRow.createCell(i);

                cell.setCellValue(columnNames.get(i));
                cell.setCellStyle(boldStyle);
            }

            if (data.isEmpty()) {
                // Ajustar a largura das colunas automaticamente
                for (int i = 0; i < columnNames.size(); i++) {
                    sheet.autoSizeColumn(i);
                }

                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }

            // Preenchendo os dados
            for (int i = 0; i < data.size(); i++) {
                Row row = sheet.createRow(i + 1);
                String[] rowData = data.get(i);
                for (int j = 0; j < rowData.length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(rowData[j]);
                }
            }

            // Ajustar a largura das colunas automaticamente
            for (int i = 0; i < columnNames.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
