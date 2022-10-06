package com.lab3.info.service.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDto;
import com.lab3.info.exception.ReportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JsonFileCreator implements Savable {
    private final Logger LOGGER = LoggerFactory.getLogger(JsonFileCreator.class);

    @Override
    public String save(ReportCardDto reportCard, String saveDir) throws ReportException {
        String fileName = reportCard
                .getStudentName()
                .replace(" ", "_") + "_report.json";
        String pathToSave = saveDir + FileSystems.getDefault().getSeparator() + fileName;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(pathToSave), reportCard);
            LOGGER.info("Create file " + pathToSave + " successful");
            return fileName;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex.getCause());
            throw new ReportException("Can`t write JSON value");
        }
    }
}
