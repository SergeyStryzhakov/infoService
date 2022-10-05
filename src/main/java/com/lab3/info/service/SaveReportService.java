package com.lab3.info.service;

import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.exception.ReportException;
import com.lab3.info.service.save.FileCreatorFactory;
import com.lab3.info.service.save.Savable;
import com.lab3.info.service.save.SaveFormats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaveReportService {
    @Value("${save.dir}")
    private String SAVE_DIR;
    private final ReportCardService reportCardService;

    public SaveReportService(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    public Map<String, String> saveReportToFile(int studentId, String format, String path)
            throws IOException, InterruptedException, ParserConfigurationException, ReportException {
        Map<String, String> responseMap = new HashMap<>();
        String realPathToSaveReport = path + SAVE_DIR;

        if (!Paths.get(realPathToSaveReport).toFile().exists()) {
            Files.createDirectories(Paths.get(realPathToSaveReport));
        }
        ReportCardDto reportCardDto = reportCardService.createDtoById(studentId);
        Savable factory = FileCreatorFactory.newInstance(SaveFormats.valueOf(format));
        String fileName = factory.save(reportCardDto, realPathToSaveReport);
        responseMap.put(SAVE_DIR, fileName);
        return responseMap;
    }


}
