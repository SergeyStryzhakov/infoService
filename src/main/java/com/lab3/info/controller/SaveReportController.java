package com.lab3.info.controller;

import com.lab3.info.service.SaveReportCardService;
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

    private final SaveReportCardService saveReportCardService;
    private final ServletContext context;

    public SaveReportController(SaveReportCardService saveReportCardService,
                                ServletContext context) {
        this.saveReportCardService = saveReportCardService;
        this.context = context;
    }

    @PostMapping()
    public ResponseEntity<?> saveReport(@ModelAttribute("studentId") int studentId,
                                        @ModelAttribute("format") String format)
            throws IOException, InterruptedException, ParserConfigurationException {

        String path = context.getRealPath("");
        return ResponseEntity.ok(saveReportCardService.saveReportToFile(studentId, format, path));
    }


}
