package com.lab3.info.service;

import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.exception.ReportException;
import com.lab3.info.service.save.FileCreatorFactory;
import com.lab3.info.service.save.Savable;
import com.lab3.info.service.save.SaveFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaveReportService {
    @Value("${save.dir}")
    private String SAVE_DIR;
    private final Logger LOGGER = LoggerFactory.getLogger(SaveReportService.class);
    private final ReportCardService reportCardService;

    public SaveReportService(ReportCardService reportCardService) {
        this.reportCardService = reportCardService;
    }

    /**
     * Save report file
     *
     * @param studentId student id
     * @param format    format from enum
     * @param path      path to save
     * @return Map<String, String>: key - path to save, value - filename
     * for creating download link in front controller
     * @throws ReportException Common exception for front controller
     */
    public Map<String, String> saveReportToFile(int studentId, String format, String path)
            throws ReportException {
        Map<String, String> responseMap = new HashMap<>();
        String realPathToSaveReport = path + SAVE_DIR;

        if (!Paths.get(realPathToSaveReport).toFile().exists()) {
            try {
                Files.createDirectories(Paths.get(realPathToSaveReport));
                LOGGER.info(realPathToSaveReport + " create successfully");
            } catch (IOException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage(), e.getCause());
                throw new ReportException("Error create directory to save reports");
            }
        }
        ReportCardDto reportCardDto = reportCardService.createDtoById(studentId);
        Savable factory = FileCreatorFactory.newInstance(SaveFormats.valueOf(format));
        String fileName = factory.save(reportCardDto, realPathToSaveReport);
        responseMap.put(SAVE_DIR, fileName);
        return responseMap;
    }


}
