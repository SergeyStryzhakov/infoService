package com.lab3.info.service.save;

import com.lab3.info.dto.ReportCardDto;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface Savable {
    String save(ReportCardDto report, String saveDir)
            throws IOException, ParserConfigurationException;

}
