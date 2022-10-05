package com.lab3.info.controller;

import com.lab3.info.exception.ReportException;
import com.lab3.info.service.save.SaveFormats;
import com.lab3.info.service.ReportCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/report")
public class ShowReportController {
    private final ReportCardService reportCardService;

    public ShowReportController(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    @GetMapping()
    public String getReportPage(Model model) {
        try {
            model.addAttribute("students", reportCardService.getStudents());
            model.addAttribute("formats", SaveFormats.values());

        } catch (ReportException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "show";
    }

    @PostMapping()
    public ResponseEntity<?> showReport(Model model, @ModelAttribute("studentId") int id) {
        try {
            return ResponseEntity.ok(reportCardService.createDtoById(id));
        } catch (ReportException e) {
            e.printStackTrace();
          return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
