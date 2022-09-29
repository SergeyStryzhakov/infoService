package com.lab3.info.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.lab3.info.dto.ReportCardDTO;
import com.lab3.info.entity.SaveFormats;
import com.lab3.info.service.ReportCardService;
import com.lab3.info.util.FileCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Controller
@RequestMapping("/save")
public class SaveReportController {
    private final ServletContext servletContext;
    private final ReportCardService reportCardService;

    public SaveReportController(ReportCardService reportCardService, ServletContext servletContext) {
        this.reportCardService = reportCardService;
        this.servletContext = servletContext;
    }


    @GetMapping(value = "/{id}")
    public String saveReport(Model model,
                             @PathVariable("id") int studentId,
                             @RequestParam String format) {
        String pathToReports = servletContext.getRealPath("data");
        try {
            ReportCardDTO reportCardDTO = reportCardService.createDtoById(studentId);
            String path = FileCreator.save(reportCardDTO, pathToReports, SaveFormats.valueOf(format));
            System.out.println(path);
            return "redirect:/show";

        } catch (IOException | InterruptedException | ParserConfigurationException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
               return "error-page";
        }
    }

}
