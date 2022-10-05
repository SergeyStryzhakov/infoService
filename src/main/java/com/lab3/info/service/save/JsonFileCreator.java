package com.lab3.info.service.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JsonFileCreator implements Savable {

    @Override
    public String save(ReportCardDto reportCard, String saveDir) throws IOException {
        String fileName = reportCard
                .getStudentName()
                .replace(" ", "_") + "_report.json";
        String pathToSave = saveDir + FileSystems.getDefault().getSeparator() + fileName;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(pathToSave), reportCard);
        return fileName;
    }
}
