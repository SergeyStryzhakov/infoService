package com.lab3.info.controller;

import com.lab3.info.exception.ReportException;
import com.lab3.info.service.SaveReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
@RequestMapping("/save")
public class SaveReportController {

    private final SaveReportService saveReportService;
    private final ServletContext context;

    public SaveReportController(SaveReportService saveReportService,
                                ServletContext context) {
        this.saveReportService = saveReportService;
        this.context = context;
    }

    @PostMapping()
    public ResponseEntity<?> saveReport(@ModelAttribute("studentId") int studentId,
                                        @ModelAttribute("format") String format)
            throws IOException, InterruptedException, ParserConfigurationException, ReportException {

        String path = context.getRealPath("");
        return ResponseEntity.ok(saveReportService.saveReportToFile(studentId, format, path));
    }


}
