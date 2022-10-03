package com.lab3.info.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lab3.info.dto.ReportCardDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JsonFileCreator implements SaveFile {

    @Override
    public String save(ReportCardDTO reportCard, String saveDir) throws IOException {
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
