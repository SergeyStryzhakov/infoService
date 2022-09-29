package com.lab3.info.controller;


import com.lab3.info.entity.SaveFormats;
import com.lab3.info.service.InfoService;
import com.lab3.info.service.ReportCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/show")
public class ShowReportController {

    private final InfoService infoService;
    private final ReportCardService reportCardService;

    public ShowReportController(InfoService infoService, ReportCardService reportCardService) {
        this.infoService = infoService;
        this.reportCardService = reportCardService;
    }

    @GetMapping()
    public String getReportPage(Model model) throws IOException, InterruptedException {
        model.addAttribute("students", infoService.getStudents());
        model.addAttribute("formats", SaveFormats.values());
        return "show";
    }

    @PostMapping()
    public ResponseEntity<?> showReport(@ModelAttribute("studentId") int id)
            throws IOException, InterruptedException {
        return ResponseEntity.ok(reportCardService.createDtoById(id));
    }


}
