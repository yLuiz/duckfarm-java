package com.example.duckfarm.http.services;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.duckfarm.db.model.Duck;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfReportService {

    @Autowired
    private DuckService duckService;

    public void export() throws FileNotFoundException, JRException {

        String path = System.getProperty("user.dir") + "\\pdf-report.pdf";

        Page<Duck> ducks = duckService.findAll(0, 10);
        File file = ResourceUtils.getFile("classpath:templates/pdf-report.jrxml");

        JasperReport report = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ducks.getContent());
        JasperPrint print = JasperFillManager.fillReport(report, null, dataSource);

        JasperExportManager.exportReportToPdfFile(print, path);
    }
}
