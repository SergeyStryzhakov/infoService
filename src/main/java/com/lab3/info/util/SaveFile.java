package com.lab3.info.util;

import com.lab3.info.dto.ReportCardDTO;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface SaveFile {
    String save(ReportCardDTO report, String saveDir) throws IOException, ParserConfigurationException;

}
