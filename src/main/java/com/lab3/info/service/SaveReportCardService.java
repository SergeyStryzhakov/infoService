package com.lab3.info.service;

import com.lab3.info.dto.ReportCardDTO;
import com.lab3.info.util.FileCreatorFactory;
import com.lab3.info.util.SaveFile;
import com.lab3.info.util.SaveFormats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaveReportCardService {
    @Value("${save.dir}")
    private String SAVE_DIR;
    private final CreateReportCardService createReportCardService;

    public SaveReportCardService(CreateReportCardService createReportCardService) {
        this.createReportCardService = createReportCardService;
    }

    public Map<String, String> saveReportToFile(int studentId, String format, String path) throws IOException, InterruptedException, ParserConfigurationException {
        String realPathToSaveReport = path + SAVE_DIR;
        if (!Paths.get(realPathToSaveReport).toFile().exists()) {
            Files.createDirectories(Paths.get(realPathToSaveReport));
        }
        ReportCardDTO reportCardDTO = createReportCardService.createDtoById(studentId);
        SaveFile factory = FileCreatorFactory.newInstance(SaveFormats.valueOf(format));
        String fileName = factory.save(reportCardDTO, realPathToSaveReport);
        Map<String, String > test = new HashMap<>();
        test.put(SAVE_DIR, fileName);
        return test;
    }


}
