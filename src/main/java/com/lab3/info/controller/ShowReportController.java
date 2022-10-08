package com.lab3.info.controller;

import com.lab3.info.exception.ReportException;
import com.lab3.info.service.save.SaveFormats;
import com.lab3.info.service.ReportCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping({"/", "/report"})
public class ShowReportController {
    private final ReportCardService reportCardService;
    private final Logger LOGGER = LoggerFactory.getLogger(ShowReportController.class);

    public ShowReportController(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    @GetMapping()
    public String getReportPage(Model model) {
        try {
            model.addAttribute("students", reportCardService.getStudents());
            model.addAttribute("formats", SaveFormats.values());
            LOGGER.info("Get mapping show controller");
        } catch (ReportException ex) {
            LOGGER.error("Error in show controller Get mapping " + ex.getMessage(), ex.getCause());
            model.addAttribute("error", ex.getMessage());
        }
        return "show";
    }

    @PostMapping()
    public ResponseEntity<?> showReport(@ModelAttribute("studentId") int id) {
        try {
            return ResponseEntity.ok(reportCardService.createDtoById(id));
        } catch (ReportException ex) {
            ex.printStackTrace();
            LOGGER.error("Error in show controller POST mapping " + ex.getMessage(), ex.getCause());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }


}
