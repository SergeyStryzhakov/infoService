package com.lab3.info.controller;


import com.lab3.info.util.SaveFormats;
import com.lab3.info.service.InfoService;
import com.lab3.info.service.CreateReportCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/show")
public class ShowReportController {

    private final InfoService infoService;
    private final CreateReportCardService reportCardService;

    public ShowReportController(InfoService infoService, CreateReportCardService createReportCardService) {
        this.infoService = infoService;
        this.reportCardService = createReportCardService;
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
