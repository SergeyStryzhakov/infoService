package com.lab3.info.controller;

import com.lab3.info.exception.ReportException;
import com.lab3.info.service.SaveReportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;


@Controller
@RequestMapping("/save")
public class SaveReportController {
    private final Logger LOGGER = LoggerFactory.getLogger(SaveReportController.class);
    private final SaveReportService saveReportService;
    private final ServletContext context;

    public SaveReportController(SaveReportService saveReportService,
                                ServletContext context) {
        this.saveReportService = saveReportService;
        this.context = context;
    }

    @PostMapping()
    public ResponseEntity<?> saveReport(@ModelAttribute("studentId") int studentId,
                                        @ModelAttribute("format") String format) throws ReportException {
        LOGGER.info("Save controller started.\n " +
                "Student id => " + studentId + " Format => " + format);
        String path = context.getRealPath("");
        LOGGER.info("Context path => " + path);
        return ResponseEntity.ok(saveReportService.saveReportToFile(studentId, format, path));
    }


}
